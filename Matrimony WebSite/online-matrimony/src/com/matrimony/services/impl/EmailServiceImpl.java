package com.matrimony.services.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.matrimony.exceptions.AppException;
import com.matrimony.services.interfaces.EmailService;
import com.matrimony.utils.Utils;
import com.matrimony.vo.EmailVO;

public class EmailServiceImpl implements EmailService {
	private final MailService mailService = MailServiceFactory.getMailService();

	public EmailVO createForgotPwdRespEmail(String forgotPwdTemplatePath, String sender, String password,
			String recipient) throws AppException {
		// user profile found then send email

		EmailVO emailVO = new EmailVO();
		emailVO.setSender(sender);
		emailVO.setSubject("Forgot Password Response");
		emailVO.addRecipients(recipient);

		// read the email template and set into Email body
		String htmlBody = readFile(forgotPwdTemplatePath);

		// replace password
		htmlBody = htmlBody.replaceFirst("%password%", password);

		emailVO.setHtmlBody(htmlBody);
		return emailVO;
	}

	public EmailVO createEmailWithHTMLBody(String sender, String subject, String htmlBody, String... recipients)
			throws AppException {
		// user profile found then send email

		EmailVO emailVO = new EmailVO();
		// emailVO.setSender("online-matrimony@gmail.com");
		emailVO.setSender(sender);
		emailVO.setSubject(subject);
		emailVO.addRecipients(recipients);

		// read the email template and set into Email body
		emailVO.setHtmlBody(htmlBody);
		return emailVO;
	}

	public void sendEmail(EmailVO emailVO) throws AppException {
		try {
			MailService.Message message = new MailService.Message();
			message.setSender(emailVO.getSender());
			message.setTo(emailVO.getRecipients());
			message.setSubject(emailVO.getSubject());
			message.setHtmlBody(emailVO.getHtmlBody());
			mailService.send(message);
		} catch (IOException e) {
			throw new AppException("Email could not be send to: " + Utils.toString(emailVO.getRecipients())
					+ ". Please try again.", e);
		}
	}

	public static String readFile(String filePath) throws AppException {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}

			return result.toString();
		} catch (IOException e) {
			throw new AppException("Error while reading template file: " + filePath, e);
		}
	}
}
