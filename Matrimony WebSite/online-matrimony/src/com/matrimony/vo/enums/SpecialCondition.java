package com.matrimony.vo.enums;

import java.io.Serializable;

public enum SpecialCondition implements Serializable {

	PHYSICALLY_CHALLENGED_BY_BIRTH("PCB"), PHYSICALLY_CHALLENGED_ACCIDENT("PCA"), PHYSICALLY_ABNORMAL_LOOKS("PAL"), NONE(
			"NON"), NO_ANSWER("NA");

	private String code;

	private SpecialCondition(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static SpecialCondition getSpecialCondition(String code) {
		for (SpecialCondition specCond : SpecialCondition.values()) {
			if (specCond.getCode().equalsIgnoreCase(code)) {
				return specCond;
			}
		}
		// default return
		return SpecialCondition.NO_ANSWER;
	}

	public static SpecialCondition[] toSpecialConditionArray(String[] specCondCodes) {
		if (specCondCodes == null || specCondCodes.length == 0) {
			return new SpecialCondition[0];
		}
		// if not null and not empty
		int length = specCondCodes.length;
		SpecialCondition[] result = new SpecialCondition[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = SpecialCondition.getSpecialCondition(specCondCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(SpecialCondition[] specCondArray) {
		if (specCondArray == null || specCondArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = specCondArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (specCondArray[idx] == null) ? SpecialCondition.NO_ANSWER.getCode() : specCondArray[idx].getCode();
		}
		return result;
	}

}
