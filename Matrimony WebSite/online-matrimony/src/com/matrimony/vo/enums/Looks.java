package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Looks implements Serializable {
	/*BEAUTY_CONTEST_WINNER("Beauty Contest Winnder"), VERY_ATTRACTIVE("Very Attractive"), ATTRACTIVE("Attractive"), AVERAGE(
			"Average"), MIRROR_CRACKING_MATERIAL("Mirron Cracking Material"), NO_ANSWER("No Answer");*/

	BEAUTY_CONTEST_WINNER("BCW"), VERY_ATTRACTIVE("VAT"), ATTRACTIVE("ATR"), AVERAGE("AVG"), MIRROR_CRACKING_MATERIAL(
			"MCM"), NO_ANSWER("NA");

	private String lookType;

	public String getLookType() {
		return this.lookType;
	}

	private Looks(String lookType) {
		this.lookType = lookType;
	}

	public static Looks getLooks(String lookType) {
		for (Looks looks : Looks.values()) {
			if (looks.getLookType().equalsIgnoreCase(lookType)) {
				return looks;
			}
		}
		// default return
		return Looks.NO_ANSWER;
	}

	public static Looks[] toLooksArray(String[] looksCodes) {
		if (looksCodes == null || looksCodes.length == 0) {
			return new Looks[0];
		}
		// if not null and not empty
		int length = looksCodes.length;
		Looks[] result = new Looks[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Looks.getLooks(looksCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Looks[] looksArray) {
		if (looksArray == null || looksArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = looksArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (looksArray[idx] == null) ? Looks.NO_ANSWER.getLookType() : looksArray[idx].getLookType();
		}
		return result;
	}

}
