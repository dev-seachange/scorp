package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Actor {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long roleplayId;
	
	private String actorName;
	
	private String description;
	
	/** Zero argument constructor required by Hibernate. */
	public Actor() {}
	
	private String actorCategory;

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
	
	
	
	
}
