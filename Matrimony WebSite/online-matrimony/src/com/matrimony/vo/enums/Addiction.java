package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Addiction implements Serializable {
	/*STRICTLY_NO("No"), SOCIAL("Socially"), REGULAR("Regularly"), TRYING_TO_QUIT("Trying to quit"), NOT_ANYMORE(
			"Not anymore"), NO_ANSWER("No Answer");*/
	STRICTLY_NO("NO"), SOCIAL("SOC"), REGULAR("REG"), TRYING_TO_QUIT("TTQ"), NOT_ANYMORE("NAM"), NO_ANSWER("NA");

	private String code;

	private Addiction(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Addiction getAddiction(String code) {
		for (Addiction addiction : Addiction.values()) {
			if (addiction.getCode().equalsIgnoreCase(code)) {
				return addiction;
			}
		}
		// default return
		return Addiction.NO_ANSWER;
	}

	public static Addiction[] toAddictionArray(String[] addictionCodes) {
		if (addictionCodes == null || addictionCodes.length == 0) {
			return new Addiction[0];
		}
		// if not null and not empty
		int length = addictionCodes.length;
		Addiction[] result = new Addiction[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Addiction.getAddiction(addictionCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Addiction[] addicationArray) {
		if (addicationArray == null || addicationArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = addicationArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (addicationArray[idx] == null) ? Addiction.NO_ANSWER.getCode() : addicationArray[idx]
					.getCode();
		}
		return result;
	}
}
