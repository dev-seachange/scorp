package com.seachangesimulations.scorp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/** A role play is a scenario where parties negotiate.
 * Examples include a peace conference settlement (ie. North Korea, South Korea, USA), 
 * bargaining for the price of a piece of property, or divorice agreements. 
 * Each role play can have multiple negotiation "phases". 
 * @author Skip Cole and Mike Sheliga
 *
 */
@Entity  // Create RolePlay table in DB
public class RolePlay {

	@Id
	@GeneratedValue
	private Long id;
	
	private String roleplayName;
	
	private String description;

	/** Planned audience of this Role Play. */
	@Lob
	private String audience = "";

	/** A paragraph introducing what this role play is all about. */
	@Lob
	private String blurb = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleplayName() {
		return roleplayName;
	}

	public void setRoleplayName(String roleplayName) {
		this.roleplayName = roleplayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	
}
