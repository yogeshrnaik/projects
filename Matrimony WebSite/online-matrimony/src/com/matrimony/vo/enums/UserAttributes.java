package com.matrimony.vo.enums;

public enum UserAttributes {
	PERSONAL_INFO("personalInfo"), SOUL_MATE("mySoulMate"), FAMILY_RELATIVES("familyRelatives"), EDUCATION_CAREER(
			"educationCareer"), CONTACT_INFO("myContact"), HOROSCOPE("horoscope"), PROFILE_PHOTO("profilePhoto"), MY_SETTINGS(
			"mySettings"), CHANGE_PASSWORD("changePassword");

	private String name;

	private UserAttributes(String attribute) {
		this.name = attribute;
	}

	public String getName() {
		return this.name;
	}

	public static UserAttributes getUserAttributes(String attribute) {
		for (UserAttributes userAttr : UserAttributes.values()) {
			if (userAttr.getName().equalsIgnoreCase(attribute)) {
				return userAttr;
			}
		}
		// default return
		return UserAttributes.PERSONAL_INFO;
	}
}
