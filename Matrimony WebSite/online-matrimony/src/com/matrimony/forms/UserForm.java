package com.matrimony.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.UserAttributes;

public class UserForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private UserVO user;

	private UserAttributes modifiedUserAttribute;

	public UserForm() {
		user = new UserVO();
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		final ActionErrors errors = new ActionErrors();

		return errors;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserAttributes getModifiedUserAttribute() {
		return modifiedUserAttribute;
	}

	public void setModifiedUserAttribute(UserAttributes modifiedUserAttr) {
		this.modifiedUserAttribute = modifiedUserAttr;
	}

	public void setModifiedUserAttributeCode(String modifiedUserAttr) {
		this.modifiedUserAttribute = UserAttributes.getUserAttributes(modifiedUserAttr);
	}

}
