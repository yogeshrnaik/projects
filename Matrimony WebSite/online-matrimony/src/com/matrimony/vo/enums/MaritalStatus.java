package com.matrimony.vo.enums;

import java.io.Serializable;

public enum MaritalStatus implements Serializable {
	/*NEVER_MARRIED("Never Married"), DIVORCED("Divorced"), WIDOWED("Widowed"), SEPARATED("Separated");*/
	NEVER_MARRIED("NM"), DIVORCED("D"), WIDOWED("W"), SEPARATED("S"), NO_ANSWER("NA");

	private String status;

	private MaritalStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public static MaritalStatus getMaritalStatus(String status) {
		for (MaritalStatus maritalStatus : MaritalStatus.values()) {
			if (maritalStatus.getStatus().equalsIgnoreCase(status)) {
				return maritalStatus;
			}
		}
		// default return
		return MaritalStatus.NO_ANSWER;
	}

	public static MaritalStatus[] toMaritalStatusArray(String[] maritalCodes) {
		if (maritalCodes == null || maritalCodes.length == 0) {
			return new MaritalStatus[0];
		}
		// if not null and not empty
		int length = maritalCodes.length;
		MaritalStatus[] result = new MaritalStatus[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = MaritalStatus.getMaritalStatus(maritalCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(MaritalStatus[] maritalArray) {
		if (maritalArray == null || maritalArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = maritalArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (maritalArray[idx] == null) ? MaritalStatus.NO_ANSWER.getStatus() : maritalArray[idx]
					.getStatus();
		}
		return result;
	}

}
