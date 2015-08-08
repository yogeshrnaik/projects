package com.matrimony.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.matrimony.exceptions.AppException;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.utils.Utils;

public class Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;

	public Validator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate email with regular expression
	 */
	public boolean validateEmail(final String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean validatePassword(final String password) {
		return (password != null && password.trim().length() > 0);
	}

	public boolean isAdult(Date birthDate) {
		return Utils.getAgeInYears(birthDate) >= 18;
	}

	public static void main(String[] args) throws AppException {
		System.out.println(ServiceFactory.getValidator().isAdult(Utils.toDate("31/04/1994")));
	}
}
