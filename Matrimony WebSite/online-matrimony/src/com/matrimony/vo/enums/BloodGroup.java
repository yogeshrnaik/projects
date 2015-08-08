package com.matrimony.vo.enums;

import java.io.Serializable;

public enum BloodGroup implements Serializable {
	O_POS("O+"), O_NEG("O-"), A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-"), AB_POS("AB+"), AB_NEG("AB-"), NO_ANSWER(
			"NA");

	private String group;

	private BloodGroup(String group) {
		this.group = group;
	}

	public String getGroup() {
		return this.group;
	}

	public static BloodGroup getBloodGroup(String group) {
		for (BloodGroup bloodGrp : BloodGroup.values()) {
			if (bloodGrp.getGroup().equalsIgnoreCase(group)) {
				return bloodGrp;
			}
		}
		// default return
		return BloodGroup.NO_ANSWER;
	}

	public static BloodGroup[] toBloodGroupArray(String[] bloodGrpCodes) {
		if (bloodGrpCodes == null || bloodGrpCodes.length == 0) {
			return new BloodGroup[0];
		}
		// if not null and not empty
		int length = bloodGrpCodes.length;
		BloodGroup[] result = new BloodGroup[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = BloodGroup.getBloodGroup(bloodGrpCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(BloodGroup[] bloodGrpArray) {
		if (bloodGrpArray == null || bloodGrpArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = bloodGrpArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (bloodGrpArray[idx] == null) ? BloodGroup.NO_ANSWER.getGroup() : bloodGrpArray[idx]
					.getGroup();
		}
		return result;
	}
}
