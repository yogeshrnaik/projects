package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Nadi implements Serializable {
	AADYA("AADYA"), ANTA("ANTA"), MADHYA("MADHYA"), NO_ANSWER("NA");

	private String code;

	private Nadi(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Nadi getNadi(String code) {
		for (Nadi nadi : Nadi.values()) {
			if (nadi.getCode().equalsIgnoreCase(code)) {
				return nadi;
			}
		}
		// default return
		return Nadi.NO_ANSWER;
	}

	public static Nadi[] toNadiArray(String[] nadiCodes) {
		if (nadiCodes == null || nadiCodes.length == 0) {
			return new Nadi[0];
		}
		// if not null and not empty
		int length = nadiCodes.length;
		Nadi[] result = new Nadi[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Nadi.getNadi(nadiCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Nadi[] nadiArray) {
		if (nadiArray == null || nadiArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = nadiArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (nadiArray[idx] == null) ? Nadi.NO_ANSWER.getCode() : nadiArray[idx].getCode();
		}
		return result;
	}

}
