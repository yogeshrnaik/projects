package com.matrimony.vo.enums;

import java.io.Serializable;

public enum Build implements Serializable {
	/*SLIM("Slim"), AVERAGE("Average"), ATHLETIC("Athletic"), FEW_EXTRA_POUNDS("Few extra pounds"), HEAVY("Heavy"), NO_ANSWER(
			"No Answer");*/
	SLIM("SLM"), AVERAGE("AVG"), ATHLETIC("ATH"), FEW_EXTRA_POUNDS("FEP"), HEAVY("HVY"), NO_ANSWER("NA");

	private String code;

	public String getCode() {
		return this.code;
	}

	private Build(String code) {
		this.code = code;
	}

	public static Build getBuild(String code) {
		for (Build build : Build.values()) {
			if (build.getCode().equalsIgnoreCase(code)) {
				return build;
			}
		}
		// default return
		return Build.NO_ANSWER;
	}

	public static Build[] toBuildArray(String[] buildCodes) {
		if (buildCodes == null || buildCodes.length == 0) {
			return new Build[0];
		}
		// if not null and not empty
		int length = buildCodes.length;
		Build[] result = new Build[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = Build.getBuild(buildCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(Build[] buildArray) {
		if (buildArray == null || buildArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = buildArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (buildArray[idx] == null) ? Build.NO_ANSWER.getCode() : buildArray[idx].getCode();
		}
		return result;
	}
}
