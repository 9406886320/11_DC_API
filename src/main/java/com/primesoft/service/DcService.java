package com.primesoft.service;

import java.util.Map;

import com.primesoft.binding.ChildRequest;
import com.primesoft.binding.DcSummary;
import com.primesoft.binding.Education;
import com.primesoft.binding.Income;
import com.primesoft.binding.PlanSelection;

public interface DcService {

	public Long loadCaseNum(Integer appId);
	
	public Map<Integer,String> getPlanNames();
	
	public Long savePlanSelection(PlanSelection planSelection);
	
	public Long saveIncomeData(Income income);
	
	public Long saveEducation(Education education);
	
	public Long saveChildres(ChildRequest request);
	
	public DcSummary  getSummary(Long caseNumber);
} 
