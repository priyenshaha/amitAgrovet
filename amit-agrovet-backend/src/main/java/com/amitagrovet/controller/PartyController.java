package com.amitagrovet.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitagrovet.dto.ResponseDto;
import com.amitagrovet.entity.Party;
import com.amitagrovet.service.PartyService;

@RestController
@RequestMapping("/party")
@CrossOrigin
public class PartyController {
	
	private static Logger logger = LoggerFactory.getLogger(PartyController.class);
	
	@Autowired
	PartyService partyService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllCustomers() {
		logger.info("Request to get all customers");
		return new ResponseEntity<>(new ResponseDto<List<Party>>("success", partyService.getAllParties()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPartyById(@PathVariable("id") Integer id){
		return new ResponseEntity<>(new ResponseDto<Party>("success", partyService.findById(id).get()), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> addParty(@Valid @RequestBody Party newParty) {
		logger.info("Request to add new party");
		return new ResponseEntity<>(new ResponseDto<Party>("success", partyService.addOrEditParty(newParty)), HttpStatus.CREATED);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<?> updateParty(@Valid @RequestBody Party updateParty){
		logger.info("Updating Party with id: " + updateParty.getId());
		return new ResponseEntity<>(new ResponseDto<Party>("success", partyService.addOrEditParty(updateParty)), HttpStatus.CREATED);
	}
	
}
