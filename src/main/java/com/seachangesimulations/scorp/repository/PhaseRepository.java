package com.seachangesimulations.scorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seachangesimulations.scorp.domain.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long>, JpaSpecificationExecutor<Phase> {

}
