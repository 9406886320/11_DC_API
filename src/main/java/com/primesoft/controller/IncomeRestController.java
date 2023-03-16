package com.primesoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.primesoft.binding.Income;
import com.primesoft.service.DcService;

@RestController
public class IncomeRestController {

	
	@Autowired
	private DcService service;
	
	@PostMapping("/income")
	public ResponseEntity<Long> saveIncome(@RequestBody Income income ){
		Long caseNum =service.saveIncomeData(income);
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
}
