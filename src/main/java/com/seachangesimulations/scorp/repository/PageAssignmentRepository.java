package com.seachangesimulations.scorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.seachangesimulations.scorp.domain.PageAssignment;

/** JpaRepository - Allows generic versions of standard CRUD database ops to be used.
 * JpaSpecificationExecutor - Allows criteria searches.
 */
@Repository  // Class is a DAO (Spring Anno)
public interface PageAssignmentRepository  extends 
	JpaRepository<PageAssignment, Long>, JpaSpecificationExecutor<PageAssignment> {

}
