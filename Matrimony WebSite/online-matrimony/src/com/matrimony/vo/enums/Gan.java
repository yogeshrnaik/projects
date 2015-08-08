package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Gan implements Serializable {
	DEV("DEV"), RAKSHAS("RAK"), MANUSHYA("MAN"), NO_ANSWER("NA");

	private String code;

	private Gan(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Gan getGan(String code) {
		for (Gan gan : Gan.values()) {
			if (gan.getCode().equalsIgnoreCase(code)) {
				return gan;
			}
		}
		// default return
		return Gan.NO_ANSWER;
	}

	public static Gan[] toGanArray(String[] ganCodes) {
		if (ganCodes == null || ganCodes.length == 0) {
			return new Gan[0];
		}
		// if not null and not empty
		int length = ganCodes.length;
		Gan[] result = new Gan[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Gan.getGan(ganCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Gan[] ganArray) {
		if (ganArray == null || ganArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = ganArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (ganArray[idx] == null) ? Gan.NO_ANSWER.getCode() : ganArray[idx].getCode();
		}
		return result;
	}

}
