 package com.seachangesimulations.scorp.domain.oldNonExtendedClasses;

 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;

/**
 * MJS 6.20.18
 * A Page links a rolePlay with an actor and a phase. 
 * 3 Common Examples: Skip, need to fill in: XXXXX
 */
@Entity  // Save Objects in a DB Table
public class PageAssignment {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long roleplayId;
	
	private String actorPhaseAssignmentId;
	
	private String description;
	
	/** Zero argument constructor required by Hibernate. */
	public PageAssignment() {}

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

	public String getActorPhaseAssignmentId() {
		return actorPhaseAssignmentId;
	}

	public void setActorPhaseAssignmentId(String actorPhaseAssignmentId) {
		this.actorPhaseAssignmentId = actorPhaseAssignmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
