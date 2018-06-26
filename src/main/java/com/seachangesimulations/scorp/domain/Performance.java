package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;

/**
 * A performance is a play (with real humans) of a Roleplay.
 * It might also be compared to a simulation of a conflict-scenario.
 *
 */

@Entity  // Save objects in DB table
public class Performance extends BaseSCObject {

	// private Long id; // from BaseSCObjectClass
	
	private Long roleplayId;
	private String performanceName;
	
	/** Zero argument constructor required by Hibernate. */
	public Performance() {}

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
