package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Education implements Serializable {
	HIGH_SCHOOL("HSC"), BACHELOR_OF_ARTS("BA"), BACHELOR_OF_COM("BCOM"), BACHELOR_OF_SCIENCE("BSC"), BACHELOR_OF_ENGG(
			"BE"), MASTER_OF_ENGG("ME"), DIPLOMA_OF_ENGG("DIPE"), OTHER_DIPLOMA("DIPO"), B_TECH("BTECH"), M_TECH(
			"MTECH"), OTHER_GRADUATE_DEGREE("OGD"), POST_GRADUATE_DEGREE("PG"), OTHER("OTH"), NO_ANSWER("NA");

	private String code;

	private Education(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getLiteral() {
		return toString();
	}

	public static Education getEducation(String code) {
		for (Education edu : Education.values()) {
			if (edu.getCode().equalsIgnoreCase(code)) {
				return edu;
			}
		}
		// default return
		return Education.NO_ANSWER;
	}

	public static Education[] toEducationArray(String[] eduCodes) {
		if (eduCodes == null || eduCodes.length == 0) {
			return new Education[0];
		}
		// if not null and not empty
		int length = eduCodes.length;
		Education[] result = new Education[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Education.getEducation(eduCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Education[] educationArray) {
		if (educationArray == null || educationArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = educationArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (educationArray[idx] == null) ? Education.NO_ANSWER.getCode() : educationArray[idx].getCode();
		}
		return result;
	}
}
