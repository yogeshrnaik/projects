package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Profession implements Serializable {
	CLERICAL("CLE"), ENGINEERING("ENGG"), MEDICAL("MED"), FINANCE("FIN"), ADVOCATE("ADV"), ARCHITECT("ARC"), AVIATION(
			"AVI"), CHEF("CHEF"), BUSINESS("BUS"), JOURNALIST("JOU"), OTHER("OTH"), NO_ANSWER("NA");

	private String code;

	private Profession(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Profession getProfession(String code) {
		for (Profession profession : Profession.values()) {
			if (profession.getCode().equalsIgnoreCase(code)) {
				return profession;
			}
		}
		// default return
		return Profession.NO_ANSWER;
	}

	public static Profession[] toProfessionArray(String[] professionCodes) {
		if (professionCodes == null || professionCodes.length == 0) {
			return new Profession[0];
		}
		// if not null and not empty
		int length = professionCodes.length;
		Profession[] result = new Profession[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Profession.getProfession(professionCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Profession[] professionArray) {
		if (professionArray == null || professionArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = professionArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (professionArray[idx] == null) ? Profession.NO_ANSWER.getCode() : professionArray[idx].getCode();
		}
		return result;
	}

}
