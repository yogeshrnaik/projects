package com.matrimony.vo.enums;

import java.io.Serializable;

public enum PetPreference implements Serializable {
	/*I_LOVE_PETS("I love my pets"), LIKE_THEM_AT_ZOO("I like them at Zoo"), I_LIKE_PETS("I like pets"), I_DONT_LIKE_PETS(
			"I don't like pets"), NO_ANSWER("No Answer");*/

	I_LOVE_PETS("ILOVE"), LIKE_THEM_AT_ZOO("ZOO"), I_LIKE_PETS("ILIKE"), I_DONT_LIKE_PETS("IDL"), NO_ANSWER("NA");

	private String preference;

	public String getPreference() {
		return this.preference;
	}

	private PetPreference(String preference) {
		this.preference = preference;
	}

	public static PetPreference getPreference(String preference) {
		for (PetPreference petPre : PetPreference.values()) {
			if (petPre.getPreference().equalsIgnoreCase(preference)) {
				return petPre;
			}
		}
		// default return
		return PetPreference.NO_ANSWER;
	}

	public static PetPreference[] toPetPreferenceArray(String[] petCodes) {
		if (petCodes == null || petCodes.length == 0) {
			return new PetPreference[0];
		}
		// if not null and not empty
		int length = petCodes.length;
		PetPreference[] result = new PetPreference[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = PetPreference.getPreference(petCodes[idx]);
		}
		return result;
	}

	public static String[] toStringArray(PetPreference[] petArray) {
		if (petArray == null || petArray.length == 0) {
			return new String[0];
		}
		// if not null and not empty
		int length = petArray.length;
		String[] result = new String[length];
		for (int idx = 0; idx < length; idx++) {
			result[idx] = (petArray[idx] == null) ? PetPreference.NO_ANSWER.getPreference() : petArray[idx]
					.getPreference();
		}
		return result;
	}

}
