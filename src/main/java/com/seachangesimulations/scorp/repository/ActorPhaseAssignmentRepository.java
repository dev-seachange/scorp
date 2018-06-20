package com.seachangesimulations.scorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.seachangesimulations.scorp.domain.ActorPhaseAssignment;

@Repository
public interface ActorPhaseAssignmentRepository  extends JpaRepository<ActorPhaseAssignment, Long>, JpaSpecificationExecutor<ActorPhaseAssignment>{

}
