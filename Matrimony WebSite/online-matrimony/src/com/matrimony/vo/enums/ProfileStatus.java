package com.matrimony.vo.enums;

import java.io.Serializable;

public enum ProfileStatus implements Serializable {
	/*PAYMENT_PENDING("Payment Pending"), PAYMENT_DONE("Payment Done"), PUBLISHED("Published"), UNPUBLISHED(
			"Un-Published"), MEMBERSHIP_EXPIRED("Membership Expired"), UNKNOWN("Unknown");*/

	PAYMENT_PENDING("PP"), PAYMENT_DONE("PD"), PUBLISHED("P"), UNPUBLISHED("UP"), MEMBERSHIP_EXPIRED("ME"), UNKNOWN(
			"UN");

	private String code;

	private ProfileStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static ProfileStatus getProfileStatus(String statusCode) {
		for (ProfileStatus profileStatus : ProfileStatus.values()) {
			if (profileStatus.getCode().equalsIgnoreCase(statusCode)) {
				return profileStatus;
			}
		}
		// default return
		return ProfileStatus.UNKNOWN;
	}
}
