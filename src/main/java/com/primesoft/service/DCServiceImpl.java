package com.primesoft.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primesoft.binding.Child;
import com.primesoft.binding.ChildRequest;
import com.primesoft.binding.DcSummary;
import com.primesoft.binding.Education;
import com.primesoft.binding.Income;
import com.primesoft.binding.PlanSelection;
import com.primesoft.entity.CitizenAppEntity;
import com.primesoft.entity.DcCaseEntity;
import com.primesoft.entity.DcChildrenEntity;
import com.primesoft.entity.DcEducationEntity;
import com.primesoft.entity.DcIncomeEntity;
import com.primesoft.entity.PlanEntity;
import com.primesoft.repo.CitizenAppRepository;
import com.primesoft.repo.DcCaseRepo;
import com.primesoft.repo.DcChildrenRepo;
import com.primesoft.repo.DcEducationRepo;
import com.primesoft.repo.DcIncomeRepo;
import com.primesoft.repo.PlanRepository;

@Service
public class DCServiceImpl implements DcService {

	@Autowired
	private DcCaseRepo dcCaseRepo;
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private DcIncomeRepo incomeRepo;
	
	@Autowired
	private DcEducationRepo educationRepo;
	
	@Autowired
	private DcChildrenRepo childRepo;
	
	@Autowired
	private CitizenAppRepository appRepo;
	
	@Override
	public Long loadCaseNum(Integer appId) {
		
		Optional<CitizenAppEntity> app= appRepo.findById(appId);
		
		if(app.isPresent()) {
			DcCaseEntity entity = new DcCaseEntity();
			entity.setAppId(appId);
			
			entity = dcCaseRepo.save(entity);
			return entity.getCaseNum();
			
		}
		return 0l;
		
		
	}

	@Override
	public Map<Integer, String> getPlanNames() {
		
		List<PlanEntity> findAll= planRepo.findAll();
		
		Map<Integer, String> plansMap = new HashMap<>();
		
		
		
		for(PlanEntity entity :findAll) {
			plansMap.put(entity.getPlanId(), entity.getPlanName());
			
		}
		
		return plansMap;
	}

	@Override
	public Long savePlanSelection(PlanSelection planSelection) {
		
		
		Optional<DcCaseEntity> findById = dcCaseRepo.findById(planSelection.getCaseNum());
		
		if(findById.isPresent()) {
			DcCaseEntity dcCaseEntity = findById.get();
			dcCaseEntity.setPlanId(planSelection.getPlanId());
			
			dcCaseRepo.save(dcCaseEntity);
			
			return planSelection.getCaseNum();
		}
		
			
		return null;
	}

	@Override
	public Long saveIncomeData(Income income) {
		DcIncomeEntity entity= new DcIncomeEntity();
		BeanUtils.copyProperties(income, entity);
		
		incomeRepo.save(entity);
		return income.getCaseNum();
	}

	@Override
	public Long saveEducation(Education education) {
	     
		DcEducationEntity entity= new DcEducationEntity();
		
		BeanUtils.copyProperties(education, entity);
		
		educationRepo.save(entity);
		return education.getCaseNum();
	}

	@Override
	public Long saveChildres(ChildRequest request) {
		
		List<Child> childs= request.getChilds();
		Long caseNum = request.getCaseNum();
		for(Child c: childs) {
			DcChildrenEntity entity= new DcChildrenEntity();
			
			BeanUtils.copyProperties(c,entity);
			entity.setCaseNum(caseNum);
			childRepo.save(entity);
		}
		
		//childRepo.saveAll(entities);
		return request.getCaseNum();
	}

	@Override
	public DcSummary getSummary(Long caseNumber) {
		
		String planName ="";
		
	DcIncomeEntity incomeEntity =	incomeRepo.findByCaseNum(caseNumber);
	DcEducationEntity educationEntity=	educationRepo.findByCaseNum(caseNumber);
	List<DcChildrenEntity> childsEntity= childRepo.findByCaseNum(caseNumber);
	
	Optional<DcCaseEntity> dcCase= dcCaseRepo.findById(caseNumber);
	if(dcCase.isPresent()) {
		Integer planId = dcCase.get().getPlanId();
		Optional<PlanEntity> plan= planRepo.findById(planId);
		
		if(plan.isPresent()) {
			planName=plan.get().getPlanName();
		}
	}
	
	//set the data to summary obj
	
	DcSummary summary = new DcSummary();
	summary.setPlanName(planName);
	
	Income income= new Income();
	BeanUtils.copyProperties(incomeEntity,income);
	summary.setIncome(income);
	
	Education edu = new Education();
	BeanUtils.copyProperties(educationEntity, edu);
	summary.setEducation(edu);
	
	List<Child> childs = new ArrayList<>();
	for(DcChildrenEntity entity : childsEntity) {
		
		Child ch = new Child();
		BeanUtils.copyProperties(entity, ch);
		childs.add(ch);
		
	}
	summary.setChild(childs);
	
	
	return summary;
	}

}
