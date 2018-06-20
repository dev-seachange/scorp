package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The assignment of one actor in a phase of a Roleplay.
 * 
 */
@Entity  // Create table in DB
public class ActorPhaseAssignment {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long roleplayId;
	
	private Long actorId;
	
	private Long phaseId;
	
	private String description;
	
	/** Zero argument constructor required by Hibernate. */
	public ActorPhaseAssignment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleplayId() {
		return roleplayId;
	}

	public void setRoleplayId(Long roleplayId) {
		this.roleplayId = roleplayId;
	}

	public Long getActorId() {
		return actorId;
	}

	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
