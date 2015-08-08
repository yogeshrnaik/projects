package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Diet implements Serializable {
	/*STRICTLY_VEG("Strictly Veg"), VEG_AND_NON_VEG("Mainly Veg + Occassionally Non Veg"), NON_VEG_AND_VEG(
			"Mainly Non Veg + Occassionally Veg"), STRICTLY_NON_VEG("Strictly Non-Veg"), NO_ANSWER("No Answer");*/
	STRICTLY_VEG("VEG"), VEG_AND_NON_VEG("VEG_NON_VEG"), NON_VEG_AND_VEG("NON_VEG_VEG"), STRICTLY_NON_VEG("NON_VEG"), NO_ANSWER(
			"NA");

	private String dietType;

	private Diet(String dietType) {
		this.dietType = dietType;
	}

	public String getDietType() {
		return this.dietType;
	}

	public static Diet getDiet(String dietType) {
		for (Diet diet : Diet.values()) {
			if (diet.getDietType().equalsIgnoreCase(dietType)) {
				return diet;
			}
		}
		// default return
		return Diet.NO_ANSWER;
	}

	public static Diet[] toDietArray(String[] dietCodes) {
		if (dietCodes == null || dietCodes.length == 0) {
			return new Diet[0];
		}
		// if not null and not empty
		int length = dietCodes.length;
		Diet[] result = new Diet[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Diet.getDiet(dietCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Diet[] dietArray) {
		if (dietArray == null || dietArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = dietArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (dietArray[idx] == null) ? Diet.NO_ANSWER.getDietType() : dietArray[idx].getDietType();
		}
		return result;
	}
}
