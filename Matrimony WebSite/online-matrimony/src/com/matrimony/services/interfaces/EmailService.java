package com.matrimony.services.interfaces;

import com.matrimony.exceptions.AppException;
import com.matrimony.vo.EmailVO;

public interface EmailService {

	public EmailVO createEmailWithHTMLBody(String sender, String subject, String htmlBody, String... recipients)
			throws AppException;

	public EmailVO createForgotPwdRespEmail(String forgotPwdTemplatePath, String sender, String password, String recipient) throws AppException;

	public void sendEmail(EmailVO emailVO) throws AppException;

}
