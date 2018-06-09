package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A performance is a play (with real humans) of a Roleplay.
 * 
 *
 */
public class Performance {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long roleplayId;
	
	private String performanceName;
	
	/** Zero argument constructor required by Hibernate. */
	public Performance() {}

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

	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}
	
	
}
