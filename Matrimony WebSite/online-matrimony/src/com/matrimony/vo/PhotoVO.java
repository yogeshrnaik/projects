package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class PhotoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Persistent
	private String pvoProfilePhotoBlobKey;

	@Persistent
	private Date pvoLastChangedOn = null;

	public PhotoVO() {

	}

	public void copy(PhotoVO vo) {
		this.pvoProfilePhotoBlobKey = vo.pvoProfilePhotoBlobKey;
	}

	public String getProfilePhotoBlobKey() {
		return pvoProfilePhotoBlobKey;
	}

	public void setProfilePhotoBlobKey(String profilePhotoBlobKey) {
		this.pvoProfilePhotoBlobKey = profilePhotoBlobKey;
	}

	public Date getLastChangedOn() {
		return pvoLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.pvoLastChangedOn = lastChangedOn;
	}

}
