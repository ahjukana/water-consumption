package ee.water.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ee.water.model.Measurement;
import ee.water.service.MeasurementService;
import ee.water.service.UserService;
import ee.water.validation.annotation.CompareWithPreviousMeasurement;

public class MeasurementConstraintValidator implements
    ConstraintValidator<CompareWithPreviousMeasurement, MeasurementValuesValidatable> {

  @Autowired
  private MeasurementService measurementService;

  @Autowired
  private UserService userService;

  private String coldKitchenFieldName;
  private String hotKitchenFieldName;
  private String coldBathroomFieldName;
  private String hotBathroomFieldName;

  @Override
  public void initialize(CompareWithPreviousMeasurement constraintAnnotation) {
    coldKitchenFieldName = constraintAnnotation.coldKitchenFieldName();
    hotKitchenFieldName = constraintAnnotation.hotKitchenFieldName();
    coldBathroomFieldName = constraintAnnotation.coldBathroomFieldName();
    hotBathroomFieldName = constraintAnnotation.hotBathroomFieldName();
  }

  @Override
  public boolean isValid(MeasurementValuesValidatable validatable,
      ConstraintValidatorContext context) {
    Measurement lastApartmentMeasurement;
    try {
      lastApartmentMeasurement = measurementService.getLastApartmentMeasurement(
          userService.getLoggedInApartment().getNumber());
    } catch (Exception e) {
      return true;
    }
    if (lastApartmentMeasurement == null) {
      return true;
    }

    boolean isColdKitchenValid = isMeasurementValid(validatable.getColdKitchen(),
        lastApartmentMeasurement.getColdKitchen());
    boolean isHotKitchenValid = isMeasurementValid(validatable.getHotKitchen(),
        lastApartmentMeasurement.getHotKitchen());
    boolean isColdBathroomValid = isMeasurementValid(validatable.getColdBathroom(),
        lastApartmentMeasurement.getColdBathroom());
    boolean isHotBathroomValid = isMeasurementValid(validatable.getHotBathroom(),
        lastApartmentMeasurement.getHotBathroom());

    if (!isColdKitchenValid) {
      addViolation(context, coldKitchenFieldName);
    }
    if (!isHotKitchenValid) {
      addViolation(context, hotKitchenFieldName);
    }
    if (!isColdBathroomValid) {
      addViolation(context, coldBathroomFieldName);
    }
    if (!isHotBathroomValid) {
      addViolation(context, hotBathroomFieldName);
    }

    return isColdKitchenValid && isHotKitchenValid && isColdBathroomValid && isHotBathroomValid;
  }

  private void addViolation(ConstraintValidatorContext context, String fieldName) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
        .addPropertyNode(fieldName).addConstraintViolation();
  }

  private boolean isMeasurementValid(Double currentValue, Double lastValue) {
    if (currentValue == null) {
      return true; // @NotNull shows validation error
    }
    return Double.compare(currentValue, lastValue) >= 0;
  }
}