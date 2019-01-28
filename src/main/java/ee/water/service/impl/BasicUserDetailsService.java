package ee.water.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ee.water.model.Apartment;
import ee.water.repository.ApartmentRepository;

@Service
public class BasicUserDetailsService implements UserDetailsService {

  @Autowired
  ApartmentRepository apartmentRepository;

  @Override
  public UserDetails loadUserByUsername(String apartmentNumber) throws UsernameNotFoundException {
    Apartment apartment = apartmentRepository.findByNumber(apartmentNumber);


    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    if (apartment.hasManagerialRights()) {
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }

    return new User(apartmentNumber, apartment.getEncodedPass(), grantedAuthorities);
  }

}
