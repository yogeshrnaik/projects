package com.matrimony.actions;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.PropertyMessageResources;

import com.matrimony.constants.Constants;
import com.matrimony.exceptions.AppException;
import com.matrimony.forms.UserForm;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.services.interfaces.UserService;
import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.UserAttributes;

public class MyProfileAction extends BaseAction {

	private static final Logger log = Logger.getLogger(MyProfileAction.class.getName());

	private final UserService userSrv = ServiceFactory.getUserService();

	// private final PersistenceService persistenceSrv = ServiceFactory.getPersistenceService();

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// check and reload if required
		checkAndDoReload(request, form);

		// set the side menu item
		String sideMenu = request.getParameter(Constants.SIDE_MENU_PARAM);
		if (sideMenu == null || sideMenu.trim().length() == 0) {
			sideMenu = Constants.VIEW_MYPROFILE_MENU;
		}

		// put menu in session
		HttpSession session = request.getSession(true);
		// put the correct menu
		session.setAttribute(Constants.CURRENT_HEADER_MENU, Constants.MYPROFILE_MENU);
		session.setAttribute(Constants.CURRENT_SIDE_MENU, sideMenu);

		// get error message from session if any. 
		// this will be case when redirected from Profile photo upload page with error
		String saveNotOk = (String) session.getAttribute(Constants.SAVE_NOT_OK_MSG);
		if (saveNotOk != null) {
			// put in request scope and remove from session
			session.removeAttribute(Constants.SAVE_NOT_OK_MSG);
			request.setAttribute(Constants.SAVE_NOT_OK_MSG, saveNotOk);
		}

		// forward to the appropriate page based on Side Menu parameter
		return mapping.findForward(sideMenu);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PropertyMessageResources props = (PropertyMessageResources) getServlet().getServletContext().getAttribute(
				Globals.MESSAGES_KEY);
		UserForm userForm = (UserForm) form;
		HttpSession session = request.getSession(true);
		try {
			/*if (UserAttributes.CHANGE_PASSWORD.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().setLastChangedOn(new Date());
			}*/
			if (UserAttributes.CONTACT_INFO.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getContact().setLastChangedOn(new Date());
			}
			if (UserAttributes.EDUCATION_CAREER.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getEducationCareer().setLastChangedOn(new Date());
			}
			if (UserAttributes.FAMILY_RELATIVES.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getFamily().setLastChangedOn(new Date());
			}
			if (UserAttributes.HOROSCOPE.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getHoroscope().setLastChangedOn(new Date());
			}
			if (UserAttributes.MY_SETTINGS.equals(userForm.getModifiedUserAttribute())) {
				// userForm.getUser().getMatrimonyProfile().get.setLastChangedOn(new Date());
			}
			if (UserAttributes.PERSONAL_INFO.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getPersonal().setLastChangedOn(new Date());
			}
			if (UserAttributes.PROFILE_PHOTO.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getPhotoVO().setLastChangedOn(new Date());

				// get Profile photo blob key stored in session in UploadFile servlet
				String profilePhotoBlobKey = (String) session.getAttribute(Constants.PROFILE_PHOTO_BLOB_KEY);
				session.removeAttribute(Constants.PROFILE_PHOTO_BLOB_KEY);
				log.info("profilePhotoBlobKey in MyProfileAction = " + profilePhotoBlobKey);

				// set the profilePhotoBlobKey into UserVO
				userForm.getUser().getMatrimonyProfile().getPhotoVO().setProfilePhotoBlobKey(profilePhotoBlobKey);
			}
			if (UserAttributes.SOUL_MATE.equals(userForm.getModifiedUserAttribute())) {
				userForm.getUser().getMatrimonyProfile().getSoulMate().setLastChangedOn(new Date());
			}

			// save the user
			UserVO userVO = userSrv.persistUser(userForm.getUser());

			// set the UserVO in UserForm
			userForm.setUser(userVO);
			session.setAttribute(Constants.USER_SESSION_ATTR, userVO);

			// set success message
			String successMsg = props.getMessage(getMessageKey(userForm.getModifiedUserAttribute(), false));
			request.setAttribute(Constants.SAVE_OK_MSG, successMsg);
		} catch (AppException e) {
			// set error message
			setErrorMessage(request, props, userForm);
		} catch (Throwable t) {
			// set error message
			setErrorMessage(request, props, userForm);
		}
		// forward to the page depending on modified user attribute
		return mapping.findForward(userForm.getModifiedUserAttribute().getName());
	}

	private void setErrorMessage(HttpServletRequest request, PropertyMessageResources props, UserForm userForm) {
		String sysAdminEmail = props.getMessage("contact_us.system.admin.email.address");
		String errMsg = props.getMessage(getMessageKey(userForm.getModifiedUserAttribute(), true));
		errMsg = String.format(errMsg, sysAdminEmail, sysAdminEmail);
		request.setAttribute(Constants.SAVE_NOT_OK_MSG, errMsg);
	}

	// public ActionForward uploadProfilePhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// UserForm userForm = (UserForm) form;
	//
	// return mapping.findForward(userForm.getModifiedUserAttribute().getName());
	// }

	private String getMessageKey(UserAttributes modifiedUserAttr, boolean error) {
		StringBuilder result = new StringBuilder();
		String userAttr = modifiedUserAttr.toString().toLowerCase();
		if (error) {
			result.append("my_profile.").append(userAttr).append(".error.message");
		} else {
			result.append("my_profile.").append(userAttr).append(".success.message");
		}
		return result.toString();
	}

	/*public ActionForward savePersonalInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PropertyMessageResources props = (PropertyMessageResources) getServlet().getServletContext().getAttribute(
				Globals.MESSAGES_KEY);
		try {
			UserForm userForm = (UserForm) form;

			// set the last changed on for Personal Info VO
			userForm.getUser().getMatrimonyProfile().getPersonal().setLastChangedOn(new Date());

			// save the user
			UserVO userVO = userSrv.persistUser(userForm.getUser());

			// set the UserVO in UserForm
			userForm.setUser(userVO);

			// set success message
			String successMsg = props.getMessage("my_profile.personal_info.success.message");
			request.setAttribute(Constants.SAVE_OK_MSG, successMsg);
		} catch (AppException e) {
			// set error message
			String sysAdminEmail = props.getMessage("contact_us.system.admin.email.address");
			String errMsg = props.getMessage("my_profile.personal_info.error.message");
			errMsg = String.format(errMsg, sysAdminEmail, sysAdminEmail);
			request.setAttribute(Constants.SAVE_NOT_OK_MSG, errMsg);
		}
		return mapping.findForward(Constants.PERSONAL_INFO_MENU);
	}

	public ActionForward saveContactDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PropertyMessageResources props = (PropertyMessageResources) getServlet().getServletContext().getAttribute(
				Globals.MESSAGES_KEY);
		try {
			UserForm userForm = (UserForm) form;

			// set the last changed on for Contact Details VO
			userForm.getUser().getMatrimonyProfile().getContact().setLastChangedOn(new Date());

			// save the user
			UserVO userVO = userSrv.persistUser(userForm.getUser());

			// set the UserVO in UserForm
			userForm.setUser(userVO);

			// set success message
			String successMsg = props.getMessage("my_profile.contact_info.success.message");
			request.setAttribute(Constants.SAVE_OK_MSG, successMsg);
		} catch (AppException e) {
			// set error message
			String sysAdminEmail = props.getMessage("contact_us.system.admin.email.address");
			String errMsg = props.getMessage("my_profile.contact_info.error.message");
			errMsg = String.format(errMsg, sysAdminEmail, sysAdminEmail);
			request.setAttribute(Constants.SAVE_NOT_OK_MSG, errMsg);
		}
		return mapping.findForward(Constants.MY_CONTACT_MENU);
	}*/

	private UserVO checkAndDoReload(HttpServletRequest request, ActionForm form) throws AppException {
		// first take the UserVO from session
		UserVO userVO = (UserVO) request.getSession(true).getAttribute(Constants.USER_SESSION_ATTR);

		UserForm userForm = (UserForm) form;

		// check the reload parameter
		boolean reload = Boolean.valueOf(request.getParameter(Constants.RELOAD_PARAM));

		if (reload) {
			// reload = true hence fetch the UserVO again
			userVO = userSrv.getUserByEmail(userVO.getEmail());
			// userVO = userSrv.getUserById(userVO.getId());

			// add the reloaded UserVO to session again
			request.getSession(true).setAttribute(Constants.USER_SESSION_ATTR, userVO);
		}

		// set the UserVO into UserForm
		userForm.setUser(userVO);
		return userVO;
	}
}
