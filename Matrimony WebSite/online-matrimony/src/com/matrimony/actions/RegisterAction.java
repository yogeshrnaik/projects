package com.matrimony.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.matrimony.constants.Constants;
import com.matrimony.exceptions.AppException;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.services.impl.Validator;
import com.matrimony.services.interfaces.PersistenceService;
import com.matrimony.services.interfaces.UserService;
import com.matrimony.utils.Utils;
import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.Gender;

public class RegisterAction extends BaseAction {
	private final UserService userSrv = ServiceFactory.getUserService();
	private final Validator validator = ServiceFactory.getValidator();
	private final PersistenceService persistenceSrv = ServiceFactory.getPersistenceService();

	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			// validate the Registration form
			DynaActionForm registerForm = (DynaActionForm) form;
			validateRegisterForm(registerForm);

			// check if user with the same email address exists
			String email = registerForm.getString("registerEmailAddress");
			if (userSrv.getUserByEmail(email) != null) {
				// user exists
				throw new AppException("User with email: " + email + " is already registered with us.");
			}

			// if validation is ok, create UserVO and save
			UserVO userVO = createUserVO(registerForm);

			// save
			userSrv.persistUser(userVO);

			// put in session
			HttpSession session = request.getSession(true);
			session.setAttribute(Constants.USER_SESSION_ATTR, userVO);
			// session.setAttribute(Constants.CURRENT_HEADER_MENU, Constants.MYPROFILE_MENU);
			// session.setAttribute(Constants.CURRENT_SIDE_MENU, Constants.VIEW_MYPROFILE_MENU);

			// redirect
			return mapping.findForward(Constants.PAGE_OK);
		} catch (AppException e) {
			request.setAttribute(Constants.REGISTER_NOT_OK_MSG, e.getMessage());
			return mapping.findForward(Constants.PAGE_NOT_OK);
		}
	}

	private void validateRegisterForm(DynaActionForm registerForm) throws AppException {

		String email = registerForm.getString("registerEmailAddress");
		if (!email.equals(registerForm.getString("confirmEmailAddress"))) {
			throw new AppException("Email address and Confirm Email Address do not match.");
		}

		if (!registerForm.getString("confirmPassword").equals(registerForm.getString("password"))) {
			throw new AppException("Password and Confirm password do not match.");
		}

		// check if Age >= 18
		Date birthDate = Utils.toDate(registerForm.getString("birthDate"));
		if (!validator.isAdult(birthDate)) {
			// not over 18 years
			throw new AppException("You must be at least 18 years old to register with us.");
		}
	}

	private UserVO createUserVO(DynaActionForm registerForm) throws AppException {
		UserVO userVO = new UserVO(registerForm.getString("registerEmailAddress"));
		userVO.getMatrimonyProfile().getPersonal().setFirstName(registerForm.getString("firstName"));
		userVO.getMatrimonyProfile().getPersonal().setLastName(registerForm.getString("lastName"));
		userVO.getMatrimonyProfile().getPersonal().setGender(Gender.getGender(registerForm.getString("gender")));
		userVO.getMatrimonyProfile().getPersonal().setBirthDate(Utils.toDate(registerForm.getString("birthDate")));
		userVO.setPassword(registerForm.getString("password"));
		return userVO;
	}
}
