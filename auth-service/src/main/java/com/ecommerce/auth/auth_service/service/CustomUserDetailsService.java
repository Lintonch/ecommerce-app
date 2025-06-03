package com.ecommerce.auth.auth_service.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerce.auth.auth_service.model.User;
import com.ecommerce.auth.auth_service.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;

	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

	        // Convert your User to Spring Security UserDetails
	        List<SimpleGrantedAuthority> authorities = 
	            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

	        return new org.springframework.security.core.userdetails.User(
	                user.getUsername(),
	                user.getPassword(),
	                authorities // Here we pass the list of authorities
	        );
	    }

}
