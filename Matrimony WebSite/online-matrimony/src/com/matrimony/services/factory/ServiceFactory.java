package com.matrimony.services.factory;

import com.matrimony.services.impl.EmailServiceImpl;
import com.matrimony.services.impl.PersistenceServiceImpl;
import com.matrimony.services.impl.ReferenceServiceImpl;
import com.matrimony.services.impl.UserServiceImpl;
import com.matrimony.services.impl.Validator;
import com.matrimony.services.interfaces.EmailService;
import com.matrimony.services.interfaces.PersistenceService;
import com.matrimony.services.interfaces.ReferenceService;
import com.matrimony.services.interfaces.UserService;

public class ServiceFactory {

	private static final EmailService emailSrv = new EmailServiceImpl();
	private static final UserService userSrv = new UserServiceImpl();
	private static final Validator validator = new Validator();
	private static final PersistenceService persistenceSrv = new PersistenceServiceImpl();
	private static final ReferenceService referenceSrv = new ReferenceServiceImpl();

	public static EmailService getEmailService() {
		return emailSrv;
	}

	public static UserService getUserService() {
		return userSrv;
	}

	public static Validator getValidator() {
		return validator;
	}

	public static PersistenceService getPersistenceService() {
		return persistenceSrv;
	}

	public static ReferenceService getReferenceService() {
		return referenceSrv;
	}
}
