package com.stecnology.hroauth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stecnology.hroauth.entities.UserDTO;

@Component
@FeignClient(name = "hr-user", path = "users")
public interface UserFeignClient {
	
	@GetMapping(value = "/search")
	ResponseEntity<UserDTO> findByEmail(@RequestParam String email);

}
