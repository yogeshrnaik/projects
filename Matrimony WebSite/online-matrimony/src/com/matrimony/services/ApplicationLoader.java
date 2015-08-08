package com.matrimony.services;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.matrimony.constants.Constants;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.services.interfaces.ReferenceService;

public class ApplicationLoader implements ServletContextListener {

	private static ServletContext contextApplication;

	private ReferenceService refSrv = ServiceFactory.getReferenceService();

	public void contextInitialized(ServletContextEvent event) {
		init(event);
	}

	private void init(ServletContextEvent event) {
		contextApplication = event.getServletContext();

		// load reference objects in application scope
		contextApplication.setAttribute(Constants.HEIGHT_LIST, refSrv.readAllHeights());
		contextApplication.setAttribute(Constants.EXPERIENCE_LIST, refSrv.readAllYearsOfExperience());
		contextApplication.setAttribute(Constants.ANNUAL_INCOME_LIST, refSrv.readAllAnnualIncome());
		contextApplication.setAttribute(Constants.AGE_LIST, refSrv.readAllAge());
		contextApplication.setAttribute(Constants.ADDICATION_LIST, refSrv.readAllAddication());
		contextApplication.setAttribute(Constants.BLOOD_GROUP_LIST, refSrv.readAllBloodGroup());
		contextApplication.setAttribute(Constants.BUILD_LIST, refSrv.readAllBuild());
		contextApplication.setAttribute(Constants.CHARAN_LIST, refSrv.readAllCharan());
		contextApplication.setAttribute(Constants.DIET_LIST, refSrv.readAllDiet());
		contextApplication.setAttribute(Constants.EDUCATION_LIST, refSrv.readAllEducation());
		contextApplication.setAttribute(Constants.GAN_LIST, refSrv.readAllGan());
		contextApplication.setAttribute(Constants.GENDER_LIST, refSrv.readAllGender());
		contextApplication.setAttribute(Constants.GOTRA_LIST, refSrv.readAllGotra());
		contextApplication.setAttribute(Constants.HOROSCOPE_SIGN_LIST, refSrv.readAllHoroscopeSign());
		contextApplication.setAttribute(Constants.LOOKS_LIST, refSrv.readAllLooks());
		contextApplication.setAttribute(Constants.MARITAL_STATUS_LIST, refSrv.readAllMaritalStatus());
		contextApplication.setAttribute(Constants.NADI_LIST, refSrv.readAllNadi());
		contextApplication.setAttribute(Constants.NAKSHTRA_LIST, refSrv.readAllNakshtra());
		contextApplication.setAttribute(Constants.OPTIONS_LIST, refSrv.readAllOptions());
		contextApplication.setAttribute(Constants.PET_PREFERENCE_LIST, refSrv.readAllPetPreferences());
		contextApplication.setAttribute(Constants.PROFESSION_LIST, refSrv.readAllProfession());
		contextApplication.setAttribute(Constants.SKIN_TONE_LIST, refSrv.readAllSkinTone());
		contextApplication.setAttribute(Constants.SPECIAL_CONDITION_LIST, refSrv.readAllSpecialCondition());
	}

	public void contextDestroyed(ServletContextEvent event) {
		// we do nothing
	}

}
