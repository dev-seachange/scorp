package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phase {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long roleplayId;
	
	private String phaseName;
	
	private String description;
	
	/** Zero argument constructor required by Hibernate. */
	public Phase() {}

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

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
