package com.matrimony.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.matrimony.constants.Constants;

public class LogoutAction extends BaseAction {

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// if all ok, add UserVO into session
		HttpSession session = request.getSession();
		if (session != null) {
			session.removeAttribute(Constants.USER_SESSION_ATTR);
			session.invalidate();
		}
		return mapping.findForward(Constants.PAGE_OK);

	}
}
