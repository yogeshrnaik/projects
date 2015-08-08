package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class FamilyInfoVO implements Serializable {

	private static final long serialVersionUID = -742774350984785975L;

	@Persistent
	private String fiFatherDetails;

	@Persistent
	private String fiMotherDetails;

	@Persistent
	private String fiBrotherDetails;

	@Persistent
	private String fiSisterDetails;

	@Persistent
	private Date fiLastChangedOn = null;

	public FamilyInfoVO() {

	}

	public FamilyInfoVO copy(FamilyInfoVO family) {
		this.fiBrotherDetails = family.fiBrotherDetails;
		this.fiFatherDetails = family.fiFatherDetails;
		this.fiMotherDetails = family.fiMotherDetails;
		this.fiSisterDetails = family.fiSisterDetails;
		this.fiLastChangedOn = family.fiLastChangedOn;
		return this;
	}

	public String getFatherDetails() {
		return fiFatherDetails;
	}

	public void setFatherDetails(String fatherDetails) {
		this.fiFatherDetails = fatherDetails;
	}

	public String getMotherDetails() {
		return fiMotherDetails;
	}

	public void setMotherDetails(String motherDetails) {
		this.fiMotherDetails = motherDetails;
	}

	public String getBrotherDetails() {
		return fiBrotherDetails;
	}

	public void setBrotherDetails(String brotherDetails) {
		this.fiBrotherDetails = brotherDetails;
	}

	public String getSisterDetails() {
		return fiSisterDetails;
	}

	public void setSisterDetails(String sisterDetails) {
		this.fiSisterDetails = sisterDetails;
	}

	public Date getLastChangedOn() {
		return fiLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.fiLastChangedOn = lastChangedOn;
	}

}
