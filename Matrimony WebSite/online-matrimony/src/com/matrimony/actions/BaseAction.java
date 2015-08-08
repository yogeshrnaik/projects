package com.matrimony.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.matrimony.constants.Constants;
import com.matrimony.forms.UserForm;

public class BaseAction extends DispatchAction {

	/*public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward resultAction = mapping.findForward(Constants.HOME_MENU);

		// Check the user session if not on login form
		if ("login".equals(request.getParameter("action"))) {
			resultAction = super.execute(mapping, form, request, response);
		} else if (!checkIfUserLoggedIn(request)) {
			// if not logged in forward to login page
			resultAction = mapping.findForward(Constants.HOME_MENU);
		} else {
			// if logged in
			
		}
		// Forward to the mapped action
		return resultAction;
	}*/

	@Override
	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String methodName) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession(true);

		// check if user is logged in
		if (checkIfUserLoggedIn(request)) {
			// check if my profile related page
			if (form instanceof UserForm) {
				// set the Header and Side Menu items
//				session.setAttribute(Constants.CURRENT_HEADER_MENU, Constants.MYPROFILE_MENU);
//
//				String sideMenu = request.getParameter(Constants.DISPATCH_METHOD_PARAM);
//				if (sideMenu == null || sideMenu.trim().length() == 0) {
//					sideMenu = Constants.VIEW_MYPROFILE_MENU;
//				}
//				session.setAttribute(Constants.CURRENT_SIDE_MENU, sideMenu);
				
				forward = super.dispatchMethod(mapping, form, request, response, methodName);
			} else {
				// user logged in, we simply let the DispatchAction take over
				forward = super.dispatchMethod(mapping, form, request, response, methodName);
			}
		} else if (form instanceof UserForm) {
			// user is not logged in and trying to go to MyProfile related page
			request.setAttribute(Constants.LOGIN_NOT_OK_MSG, "Your session is invalid. Please login.");

			// put in session Current Header & Side menu item == login
			session.setAttribute(Constants.CURRENT_HEADER_MENU, Constants.LOGIN_MENU);
			session.setAttribute(Constants.CURRENT_SIDE_MENU, Constants.LOGIN_MENU);

			forward = mapping.findForward(Constants.LOGIN_MENU);
		} else {
			// default behaviour
			forward = super.dispatchMethod(mapping, form, request, response, methodName);
		}
		return forward;
	}

	protected boolean checkIfUserLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		boolean loggedIn = (session.getAttribute(Constants.USER_SESSION_ATTR) != null);
		return loggedIn;
	}
}
