package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserVO implements Serializable {

	private static final long serialVersionUID = -2235453872689443468L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String email;

	@Persistent
	private String password;

	@Persistent
	@Embedded
	private MatrimonyProfileVO matrimonyProfile = null;

	@Persistent
	private Date lastChangedOn = null;

	public UserVO() {
		matrimonyProfile = new MatrimonyProfileVO();
	}

	public UserVO copy(UserVO user) {
		this.email = user.email;
		this.password = user.password;
		this.matrimonyProfile.copy(user.matrimonyProfile);
		this.lastChangedOn = user.lastChangedOn;
		return this;
	}

	public UserVO(String email) {
		this();
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MatrimonyProfileVO getMatrimonyProfile() {
		return matrimonyProfile;
	}

	public void setMatrimonyProfile(MatrimonyProfileVO matrimonyProfile) {
		this.matrimonyProfile = matrimonyProfile;
	}

	 public Date getLastChangedOn() {
		return lastChangedOn;
	 }
	
	 public void setLastChangedOn(Date lastChangedOn) {
	 this.lastChangedOn = lastChangedOn;
	 }

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("email = ").append(email);
		result.append(", password = ").append(password);
		result.append(", matrimonyProfile = ").append(matrimonyProfile);
		return result.toString();
	}

}
