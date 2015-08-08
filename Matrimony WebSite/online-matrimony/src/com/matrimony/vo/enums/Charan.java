package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Charan implements Serializable {
	PRATHAM("P"), DWITIYA("D"), TRITIYA("T"), CHATURTHA("C"), NO_ANSWER("N");

	private String code;

	private Charan(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Charan getCharan(String code) {
		for (Charan charan : Charan.values()) {
			if (charan.getCode().equalsIgnoreCase(code)) {
				return charan;
			}
		}
		// default return
		return Charan.NO_ANSWER;
	}

	public static Charan[] toCharanArray(String[] charanCodes) {
		if (charanCodes == null || charanCodes.length == 0) {
			return new Charan[0];
		}
		// if not null and not empty
		int length = charanCodes.length;
		Charan[] result = new Charan[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Charan.getCharan(charanCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Charan[] charanArray) {
		if (charanArray == null || charanArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = charanArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (charanArray[idx] == null) ? Charan.NO_ANSWER.getCode() : charanArray[idx].getCode();
		}
		return result;
	}
}
