package com.stecnology.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stecnology.hroauth.entities.UserDTO;
import com.stecnology.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public UserDTO findByEmail(String email) {
		var user = userFeignClient.findByEmail(email).getBody();
		if(user == null) {
			logger.error("emil not found: " + email);
			throw new IllegalArgumentException("Email not found");
		}
		logger.info("Email found: " + email);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userFeignClient.findByEmail(username).getBody();
		if(user == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException(username);
		}
		logger.info("User found: " + username);
		return user;
	}
}
