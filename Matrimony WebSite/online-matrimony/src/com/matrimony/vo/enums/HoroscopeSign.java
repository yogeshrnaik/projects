package com.matrimony.vo.enums;

import java.io.Serializable;

public enum HoroscopeSign implements Serializable {
	ARIES("ARI"), TAURUS("TAU"), GEMINI("GEM"), CANCER("CAN"), LEO("LEO"), VIRGO("VIRG"), LIBRA("LIB"), SCORPIO("SCOP"), SAGITTARIUS(
			"SAGI"), CAPRICORN("CAPR"), AQUARIUS("AQU"), PISCES("PIS"), NO_ANSWER("NA");

	private String sign;

	private HoroscopeSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return this.sign;
	}

	public static HoroscopeSign getHoroscopeSign(String sign) {
		for (HoroscopeSign hSign : HoroscopeSign.values()) {
			if (hSign.getSign().equalsIgnoreCase(sign)) {
				return hSign;
			}
		}
		// default return
		return HoroscopeSign.NO_ANSWER;
	}

	public static HoroscopeSign[] toHoroscopeSignArray(String[] signCodes) {
		if (signCodes == null || signCodes.length == 0) {
			return new HoroscopeSign[0];
		}
		// if not null and not empty
		int length = signCodes.length;
		HoroscopeSign[] result = new HoroscopeSign[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = HoroscopeSign.getHoroscopeSign(signCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(HoroscopeSign[] signArray) {
		if (signArray == null || signArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = signArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (signArray[idx] == null) ? HoroscopeSign.NO_ANSWER.getSign() : signArray[idx].getSign();
		}
		return result;
	}

}
