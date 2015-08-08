package com.matrimony.actions;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.matrimony.constants.Constants;
import com.matrimony.services.factory.ServiceFactory;
import com.matrimony.services.interfaces.UserService;

public class SearchAction extends BaseAction {

	private static final Logger log = Logger.getLogger(MyProfileAction.class.getName());

	private final UserService userSrv = ServiceFactory.getUserService();

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(Constants.PAGE_OK);
	}

	public ActionForward idealMatchSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(Constants.PAGE_OK);
	}

	public ActionForward advancedSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(Constants.PAGE_OK);
	}
}
