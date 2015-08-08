package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Nakshtra implements Serializable {
	ASWINI("ASWI"),
	BHARANI("BHAR"),
	KRITHIKA("KRIT"),
	ROHINI("ROHI"),
	MRIGASHIRAS("MRIG"),
	AARDHRA("AARD"),
	PUNARVASU("PUNA"),
	PUSHYAMI("PUSH"),
	ASHLESHA("ASHL"),
	MAGHA_MAKHA("MAGHA"),
	P_PHALGUNI_POORVAPHALGUNI("P_PHALGUNI"),
	U_PHALGUNI_UTHRAPHALGUNI("U_PHALGUNI"),
	HASTHA("HAST"),
	CHITRA("CHIT"),
	SWAATHI("SWAA"),
	VISHAAKHA("VISH"),
	ANURAADHA("ANUR"),
	JYESHTA("JYES"),
	MOOLA("MOOL"),
	P_SHADA_POORVASHAADA("P_SHADA"),
	U_SHADA_UTHRASHAADA("U_SHADA"),
	SHRAAVAN("SHRA"),
	DHANISHTA("DHAN"),
	SHATHABHISHA("SHAT"),
	P_BHADRA_POORVABHADRA("P_BHADRA"),
	U_BHADRA_UTHRABHADRA("U_BHADRA"),
	REVATHI("REVA"), 
	NO_ANSWER("NA");

	private String code;

	private Nakshtra(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Nakshtra getNakshtra(String code) {
		for (Nakshtra nakshtra : Nakshtra.values()) {
			if (nakshtra.getCode().equalsIgnoreCase(code)) {
				return nakshtra;
			}
		}
		// default return
		return Nakshtra.NO_ANSWER;
	}
	
	public static Nakshtra[] toNakshtraArray(String[] nakshtraCodes) {
		if (nakshtraCodes == null || nakshtraCodes.length == 0) {
			return new Nakshtra[0];
		}
		// if not null and not empty
		int length = nakshtraCodes.length;
		Nakshtra[] result = new Nakshtra[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Nakshtra.getNakshtra(nakshtraCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Nakshtra[] nakshtraArray) {
		if (nakshtraArray == null || nakshtraArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = nakshtraArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (nakshtraArray[idx] == null) ? Nakshtra.NO_ANSWER.getCode() : nakshtraArray[idx].getCode();
		}
		return result;
	}
}
