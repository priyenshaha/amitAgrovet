package com.amitagrovet.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amitagrovet.entity.Party;
import com.amitagrovet.repository.PartyRepository;
import com.amitagrovet.service.PartyService;

@Service
@Transactional
public class PartyServiceImpl implements PartyService{

	@Autowired
	PartyRepository partyRepo;
	
	@Override
	public Optional<Party> findById(Integer id) {
		return partyRepo.findById(id);
	}

	@Override
	public List<Party> getAllParties() {
		return partyRepo.findAll();
	}
	
	public Party addOrEditParty(Party newParty) {
		return partyRepo.save(newParty);
	}

}
