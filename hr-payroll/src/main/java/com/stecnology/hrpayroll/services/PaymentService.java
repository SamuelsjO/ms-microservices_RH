package com.stecnology.hrpayroll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stecnology.hrpayroll.entities.Payment;
import com.stecnology.hrpayroll.entities.Worker;
import com.stecnology.hrpayroll.feignclients.WorkrFeignClient;

@Service
public class PaymentService {
	
	@Autowired
	private WorkrFeignClient workrFeignClient;
	
	public Payment getPayment(long workerId, int days) {
		
		Worker worker = workrFeignClient.findById(workerId).getBody();
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
}
