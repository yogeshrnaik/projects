package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Gotra implements Serializable {
	ATRI("ATRI"), KAPI ("KAPI"), KASHYAP ("KASH"), KAUDINYA("KAUD"), KAUSHIK("KAUS"), 
	GARGYA("GARG"), JAMDAGNYA("JAMD"), NITYUNDAN("NITY"), BABRAHVYA("BABR"), BHARADWAJ("BHAR"), 
	VATSA("VATS"), VASISHTYA("VASI"), VISHNUVRUDHA("VISH"), SHANDILYA("SHAN"), NO_ANSWER("NA");
	
	private String code;

	private Gotra(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static Gotra getGotra(String code) {
		for (Gotra gotra : Gotra.values()) {
			if (gotra.getCode().equalsIgnoreCase(code)) {
				return gotra;
			}
		}
		// default return
		return Gotra.NO_ANSWER;
	}
	public static Gotra[] toGotraArray(String[] gotraCodes) {
		if (gotraCodes == null || gotraCodes.length == 0) {
			return new Gotra[0];
		}
		// if not null and not empty
		int length = gotraCodes.length;
		Gotra[] result = new Gotra[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Gotra.getGotra(gotraCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Gotra[] gotraArray) {
		if (gotraArray == null || gotraArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = gotraArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (gotraArray[idx] == null) ? Gotra.NO_ANSWER.getCode() : gotraArray[idx].getCode();
		}
		return result;
	}

}
