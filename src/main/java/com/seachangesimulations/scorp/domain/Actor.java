package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;


@Entity  // Create table in DB
public class Actor extends BaseSCObject {


	// BaseSCObject instance variables
	// private Long id;
	private Long roleplayId;
	private String actorName;
	private String description;
	private String actorCategory;
	
	/** Zero argument constructor required by Hibernate. */
	public Actor() {}


	public Long getRoleplayId() {
		return roleplayId;
	}

	public void setRoleplayId(Long roleplayId) {
		this.roleplayId = roleplayId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActorCategory() {
		return actorCategory;
	}

	public void setActorCategory(String actorCategory) {
		this.actorCategory = actorCategory;
	}
		
} // end class Actor

