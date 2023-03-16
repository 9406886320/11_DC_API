package com.primesoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.primesoft.binding.ChildRequest;
import com.primesoft.binding.DcSummary;
import com.primesoft.service.DcService;

@RestController
public class ChildRestController {

	@Autowired
	private DcService service;


	@PostMapping("/childrens")
	public ResponseEntity<DcSummary> saveChilds(@RequestBody ChildRequest request){
		
		Long caseNum = service.saveChildres(request);
		
		DcSummary summary = service.getSummary(caseNum);
	
	    return new ResponseEntity<>(summary, HttpStatus.OK);
	
	}

}
