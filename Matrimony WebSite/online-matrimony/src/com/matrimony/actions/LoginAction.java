package com.matrimony.actions;

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
import com.matrimony.vo.UserVO;

public class LoginAction extends BaseAction {

	private final UserService userSrv = ServiceFactory.getUserService();
//	private final Validator validator = ServiceFactory.getValidator();
//	private final PersistenceService persistenceSrv = ServiceFactory.getPersistenceService();
	
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// forward to login page
		return mapping.findForward(Constants.LOGIN_MENU);
		
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		try {
			// get the email and password
			DynaActionForm loginForm = (DynaActionForm) form;

			String email = loginForm.getString("emailAddress");
			String password = loginForm.getString("password");

			// validate
			// validateLoginForm(email, password);

			UserVO userVO = userSrv.getUserByEmail(email);
			if (userVO == null) {
				// user with email not found
				throw new AppException("User with email: '" + email + "' is not found.");
			} else if (!userVO.getPassword().equals(password)) {
				// incorrect password
				throw new AppException("Password entered is incorrect.");
			}

			// if all ok, add UserVO into session
			session.setAttribute(Constants.USER_SESSION_ATTR, userVO);

			// put the correct menu
//			session.setAttribute(Constants.CURRENT_HEADER_MENU, Constants.MYPROFILE_MENU);
//			session.setAttribute(Constants.CURRENT_SIDE_MENU, Constants.VIEW_MYPROFILE_MENU);

			return mapping.findForward(Constants.PAGE_OK);
		} catch (AppException e) {
			request.setAttribute(Constants.LOGIN_NOT_OK_MSG, e.getMessage());

			return mapping.findForward(Constants.PAGE_NOT_OK);

			// forward based on CURRENT_MENU stored in session
			// return mapping.findForward((String) session.getAttribute(Constants.CURRENT_HEADER_MENU));
		}
	}

	/*private void validateLoginForm(String email, String password) throws AppException {

		if (!validator.validateEmail(email)) {
			throw new AppException("'" + email + "' is not valid email.");
		}
		if (!validator.validatePassword(password)) {
			throw new AppException("Please enter valid password.");
		}
	}*/
}
