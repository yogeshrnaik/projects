package com.matrimony.vo.enums;

import java.io.Serializable;

public enum SkinTone implements Serializable {
	/*FAIR("Fair"), MEDIUM("Medium"), BROWN("Brown"), BLACK("Black"), NO_ANSWER("No Answer");*/
	FAIR("FAR"), MEDIUM("MED"), BROWN("BRO"), BLACK("BLK"), NO_ANSWER("NA");

	private String tone;

	public String getTone() {
		return this.tone;
	}

	private SkinTone(String tone) {
		this.tone = tone;
	}

	public static SkinTone getSkinTone(String tone) {
		for (SkinTone skinTone : SkinTone.values()) {
			if (skinTone.getTone().equalsIgnoreCase(tone)) {
				return skinTone;
			}
		}
		// default return
		return SkinTone.NO_ANSWER;
	}

	public static SkinTone[] toSkinToneArray(String[] skinToneCodes) {
		if (skinToneCodes == null || skinToneCodes.length == 0) {
			return new SkinTone[0];
		}
		// if not null and not empty
		int length = skinToneCodes.length;
		SkinTone[] result = new SkinTone[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = SkinTone.getSkinTone(skinToneCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(SkinTone[] skinToneArray) {
		if (skinToneArray == null || skinToneArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = skinToneArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (skinToneArray[idx] == null) ? SkinTone.NO_ANSWER.getTone() : skinToneArray[idx].getTone();
		}
		return result;
	}

}
