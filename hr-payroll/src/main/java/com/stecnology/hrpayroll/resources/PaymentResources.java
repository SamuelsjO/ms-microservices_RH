package com.stecnology.hrpayroll.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stecnology.hrpayroll.entities.Payment;
import com.stecnology.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResources {
	
	@Autowired
	private PaymentService service;
	
	@GetMapping(value = "/{workedId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long workedId, @PathVariable Integer days){
		Payment payment = service.getPayment(workedId, days);
		return ResponseEntity.ok(payment);
	}

}
