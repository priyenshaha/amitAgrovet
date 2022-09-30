package com.amitagrovet.service;

import java.util.List;
import java.util.Optional;

import com.amitagrovet.entity.Party;

public interface PartyService {
	
	Optional<Party> findById(Integer id);
	
	List<Party> getAllParties();
	
	Party addOrEditParty(Party newParty);

}
