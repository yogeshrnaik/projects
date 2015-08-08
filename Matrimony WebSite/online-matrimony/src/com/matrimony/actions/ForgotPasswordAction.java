package com.matrimony.actions;

import java.util.ResourceBundle;

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
import com.matrimony.services.impl.EmailServiceImpl;
import com.matrimony.services.impl.Validator;
import com.matrimony.services.interfaces.EmailService;
import com.matrimony.services.interfaces.PersistenceService;
import com.matrimony.services.interfaces.UserService;
import com.matrimony.vo.EmailVO;
import com.matrimony.vo.UserVO;

public class ForgotPasswordAction extends BaseAction {

	private final EmailService emailSrv = ServiceFactory.getEmailService();
	private final UserService userSrv = ServiceFactory.getUserService();
	private final Validator validator = ServiceFactory.getValidator();
	private final PersistenceService persistenceSrv = ServiceFactory.getPersistenceService();

	public ActionForward forgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// get email address from ActionForm
			String forgotPwdEmailAddress = (String) ((DynaActionForm) form).get("forgotPwdEmailAddress");
			// String forgotPwdEmailAddress = request.getParameter("forgotPwdEmailAddress");

			if (!validator.validateEmail(forgotPwdEmailAddress)) {
				// email address is invalid
				// set error in request attribute and return
				request.setAttribute(Constants.FORGOT_PASSWORD_NOT_OK_MSG, "'" + forgotPwdEmailAddress
						+ "' is not valid email. Please correct and try again.");
				return mapping.findForward(Constants.PAGE_NOT_OK);
			}
			// search for the User Profile based on Email
			UserVO userVO = userSrv.getUserByEmail(forgotPwdEmailAddress);
			if (userVO == null) {
				// // TODO: REMOVE THIS START
				// userVO = new UserVO(forgotPwdEmailAddress);
				// userVO.setPassword("TestPwd");
				// userVO.setFirstName("First Test Name");
				// userVO.setLastName("Last Test Name");
				// persistenceSrv.persist(userVO);
				// // TODO: REMOVE THIS END

				// throw exception
				throw new AppException("No user found for email: " + forgotPwdEmailAddress
						+ ". Please enter correct email address and try again.");
			}

			// read the sender address from properties file

			PropertyMessageResources props = (PropertyMessageResources) getServlet().getServletContext().getAttribute(
					Globals.MESSAGES_KEY);

			String forgotPwdTemplatePath = getServlet().getServletContext().getRealPath(
					"/templates/forgot-password-template.html");

			// String htmlBody = EmailServiceImpl.readFile(filePath);
			// htmlBody = htmlBody.replaceAll("%password%", userVO.getPassword());

			String sender = props.getMessage("contact_us.email.address");

			EmailVO emailVO = emailSrv.createForgotPwdRespEmail(forgotPwdTemplatePath, sender, userVO.getPassword(),
					forgotPwdEmailAddress);

			// EmailVO emailVO = emailSrv.createEmailWithHTMLBody(props.getMessage("contact_us.email.address"),
			// "Forgot Password Response", htmlBody, forgotPwdEmailAddress);

			/*// user profile found then send email
			EmailVO emailVO = new EmailVO();
			// emailVO.setSender("online-matrimony@gmail.com");
			emailVO.setSender("yogeshrnaik@gmail.com");
			emailVO.setSubject("Forgot Password Response");
			emailVO.addRecipients(forgotPwdEmailAddress);*/

			// send email
			emailSrv.sendEmail(emailVO);

			// set success message
			String successMsg = props.getMessage("forgot_password.success.message");
			request.setAttribute(Constants.FORGOT_PASSWORD_OK_MSG, String.format(successMsg, forgotPwdEmailAddress));
		} catch (AppException e) {
			request.setAttribute(Constants.FORGOT_PASSWORD_NOT_OK_MSG, e.getMessage());
		}
		return mapping.findForward(Constants.PAGE_OK);
	}
}
