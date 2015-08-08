package com.matrimony.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.PropertyMessageResources;

import com.matrimony.constants.Constants;
import com.matrimony.exceptions.AppException;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.services.impl.Validator;
import com.matrimony.services.interfaces.UserService;
import com.matrimony.vo.UserVO;

public class ChangePasswordAction extends BaseAction {

	private final UserService userSrv = ServiceFactory.getUserService();
	private final Validator validator = ServiceFactory.getValidator();

	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PropertyMessageResources props = (PropertyMessageResources) getServlet().getServletContext().getAttribute(
				Globals.MESSAGES_KEY);
		try {
			// get email address from ActionForm
			DynaActionForm changePwdForm = (DynaActionForm) form;
			String oldPassword = (String) changePwdForm.get("oldPassword");
			String newPassword = (String) changePwdForm.get("newPassword");
			String confirmNewPassword = (String) changePwdForm.get("confirmNewPassword");

			// get User from session
			UserVO user = (UserVO) request.getSession(true).getAttribute(Constants.USER_SESSION_ATTR);

			// do password related validations
			validatePassword(props, user.getPassword(), oldPassword, newPassword, confirmNewPassword);

			// if all validations are ok - change the password
			user.setPassword(newPassword);
			
			// save user
			userSrv.persistUser(user);

			// set success message
			String successMsg = props.getMessage("my_profile.change_password.success.message");
			request.setAttribute(Constants.SAVE_OK_MSG, successMsg);
		} catch (AppException e) {
			request.setAttribute(Constants.SAVE_NOT_OK_MSG, e.getMessage());
		} catch (Exception e) {
			request.setAttribute(Constants.SAVE_NOT_OK_MSG, props
					.getMessage("my_profile.change_password.error.message"));
		}
		return mapping.findForward(Constants.PAGE_OK);
	}

	private void validatePassword(PropertyMessageResources props, String existingPwd, String oldPassword,
			String newPassword, String confirmNewPassword) throws AppException {
		if (!existingPwd.equals(oldPassword)) {
			// incorrect original password entered
			throw new AppException("Please enter your old password correctly.");
		}
		if (!newPassword.equals(confirmNewPassword)) {
			// new password and confirm new password are not same
			throw new AppException("New Password and Confirm New Password should be the same.");
		}
		if (existingPwd.equals(newPassword)) {
			// existing password and new are same
			throw new AppException("Your new password cannot be same as your old password.");
		}
	}
}
