package com.matrimony.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.matrimony.constants.Constants;

public class MenuAction extends BaseAction {
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String headerMenu = request.getParameter(Constants.HEADER_MENU_PARAM);
		if (headerMenu == null) {
			headerMenu = Constants.ABOUT_US_MENU;
		}
		String sideMenu = request.getParameter(Constants.SIDE_MENU_PARAM);
		if (sideMenu == null) {
			sideMenu = headerMenu;
		}
		
		ActionForward forward = mapping.findForward(sideMenu);
		if (forward == null) {
			forward = mapping.findForward(Constants.ABOUT_US_MENU);
		}

		// set the current header menu item and side menu item (if any)
		request.getSession(true).setAttribute(Constants.CURRENT_HEADER_MENU, headerMenu);
		request.getSession(true).setAttribute(Constants.CURRENT_SIDE_MENU, sideMenu);
		return forward;
	}
}
