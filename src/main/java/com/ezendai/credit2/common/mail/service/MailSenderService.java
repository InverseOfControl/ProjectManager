package com.ezendai.credit2.common.mail.service;



public interface MailSenderService {

	public void sendMailToUser(String title, String content, String[] toMails,String [] ccMails);
	
	public void sendMailToUser(String content);
}
