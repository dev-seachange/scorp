package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;

/**
 * The assignment of one actor to a phase of a Roleplay.
 * For example, actor EnglishPrimeMinister is allowed in the Munich Conference Phase of the PreWW2 RolePlay.
 */

@Entity  // Create table in DB
public class ActorPhaseAssignment extends BaseSCObject {

	// private Long id; // from BaseSCObject superclass
	private Long roleplayId;	
	private Long actorId;	
	private Long phaseId;	
	private String description;
	
	/** Zero argument constructor required by Hibernate. */
	public ActorPhaseAssignment() {}

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
	
} // end domain (pojo) class ActorPhaseAssignment
