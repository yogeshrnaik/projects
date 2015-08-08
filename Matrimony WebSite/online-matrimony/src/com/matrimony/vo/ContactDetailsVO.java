package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class ContactDetailsVO implements Serializable {

	private static final long serialVersionUID = -812075828555590756L;

	@Persistent
	private String cdSecondaryEmail;

	@Persistent
	private String cdHomePhone;

	@Persistent
	private String cdWorkPhone;

	@Persistent
	private String cdMobilePhone;

	@Persistent
	private String cdCountry;

	@Persistent
	private String cdState;

	@Persistent
	private String cdCity;

	@Persistent
	private String cdPostalCode;

	@Persistent
	private String cdAddress;

	@Persistent
	private String cdHomeTown;

	@Persistent
	private String cdWebsite;

	@Persistent
	private Date cdLastChangedOn = null;

	// @Persistent
	// private String streetName;
	//
	// @Persistent
	// private String buildingName;
	//
	// @Persistent
	// private String flatNo;

	public ContactDetailsVO() {

	}

	public ContactDetailsVO copy(ContactDetailsVO contact) {
		this.cdAddress = contact.cdAddress;
		this.cdCity = contact.cdCity;
		this.cdCountry = contact.cdCountry;
		this.cdHomePhone = contact.cdHomePhone;
		this.cdMobilePhone = contact.cdMobilePhone;
		this.cdPostalCode = contact.cdPostalCode;
		this.cdSecondaryEmail = contact.cdSecondaryEmail;
		this.cdState = contact.cdState;
		this.cdWebsite = contact.cdWebsite;
		this.cdWorkPhone = contact.cdWorkPhone;
		this.cdHomeTown = contact.cdHomeTown;
		this.cdLastChangedOn = contact.cdLastChangedOn;
		return this;
	}

	public String getSecondaryEmail() {
		return cdSecondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.cdSecondaryEmail = secondaryEmail;
	}

	public String getHomePhone() {
		return cdHomePhone;
	}

	public void setHomePhone(String homePhone) {
		this.cdHomePhone = homePhone;
	}

	public String getWorkPhone() {
		return cdWorkPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.cdWorkPhone = workPhone;
	}

	public String getMobilePhone() {
		return cdMobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.cdMobilePhone = mobilePhone;
	}

	public String getCountry() {
		return cdCountry;
	}

	public void setCountry(String country) {
		this.cdCountry = country;
	}

	public String getState() {
		return cdState;
	}

	public void setState(String state) {
		this.cdState = state;
	}

	public String getCity() {
		return cdCity;
	}

	public void setCity(String city) {
		this.cdCity = city;
	}

	public String getPostalCode() {
		return cdPostalCode;
	}

	public void setPostalCode(String postalCode) {
		this.cdPostalCode = postalCode;
	}

	public String getAddress() {
		return cdAddress;
	}

	public void setAddress(String address) {
		this.cdAddress = address;
	}

	public String getHomeTown() {
		return cdHomeTown;
	}

	public void setHomeTown(String homeTown) {
		this.cdHomeTown = homeTown;
	}

	public String getWebsite() {
		return cdWebsite;
	}

	public void setWebsite(String website) {
		this.cdWebsite = website;
	}

	public Date getLastChangedOn() {
		return cdLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.cdLastChangedOn = lastChangedOn;
	}

}
