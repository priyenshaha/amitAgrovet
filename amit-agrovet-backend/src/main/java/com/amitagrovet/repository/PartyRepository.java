package com.amitagrovet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amitagrovet.entity.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer>{

}
