package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Gender implements Serializable {
	MALE("M"), FEMALE("F");

	private String code;

	private Gender(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Gender getGender(String code) {
		for (Gender gender : Gender.values()) {
			if (gender.getCode().equalsIgnoreCase(code)) {
				return gender;
			}
		}
		// default return
		return Gender.MALE;
	}
}
