package com.ezendai.credit2.common.mail.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.common.mail.service.MailSenderService;
import com.ezendai.credit2.common.mail.MailProperties;
import com.ezendai.credit2.master.enumerate.EnumConstants;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private MailProperties mailProperties;

	private static final Logger logger = Logger.getLogger(MailSenderServiceImpl.class);

	/**
	 * 发邮件,首先判断邮件配置MAIL_FLAG,若配置为NO,则不发邮件,配置为YES,则发邮件.
	 * 
	 * @param title:邮件主题
	 * @param content:邮件内容
	 * @param toMail:要发给的邮件地址列表
	 * @param ccMails:要抄送的邮件地址列表
	 * @see com.ezendai.credit2.common.mail.service.MailSenderService#sendMailToUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendMailToUser(String title, String content, String[] toMails,String [] ccMails) {
		SimpleMailMessage msg = new SimpleMailMessage();
		try {
			String mailFlag = mailProperties.getMailFlag();
			String from = mailProperties.getFromMail();
			if (EnumConstants.YesOrNo.YES.name().equals(mailFlag)) {
				buildMail(content, msg, title, from,toMails,ccMails);
			}
		} catch (Exception e) {
			logger.error("发送邮件异常: ", e);
			return;
		}
	}

	/**
	 * 发邮件,首先判断邮件配置MAIL_FLAG,若配置为NO,则不发邮件,配置为YES,则发邮件.
	 * @param content:邮件内容
	 * @see com.ezendai.credit2.common.mail.service.MailSenderService#sendMailToUser(java.lang.String)
	 */
	public void sendMailToUser(String content) {
		String mailFlag = mailProperties.getMailFlag();
		if (EnumConstants.YesOrNo.YES.name().equals(mailFlag)) {
			SimpleMailMessage msg = new SimpleMailMessage();
			try {
				String toEmail = mailProperties.getToMail();
				String ccMail = mailProperties.getCcMail();
				String title = mailProperties.getMailTitle();
				String from = mailProperties.getFromMail();
				// 获取邮件列表
				String[] mails = toEmail.split(";");
				String[] ccMails = ccMail.split(";");
				buildMail(content, msg, title, from,mails, ccMails);
			} catch (Exception e) {
				logger.error("发送邮件异常: ", e);
				return;
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 发送邮件
	 * </pre>
	 *
	 * @param content
	 * @param msg
	 * @param title
	 * @param mail:邮件列表
	 * @param cc:抄送邮件列表
	 */
	private void buildMail(String content, SimpleMailMessage msg, String title,String from, String[] mail,
							String[] cc) {
		msg.setFrom(from);
		if(mail != null && mail.length > 0){
			msg.setTo(mail);
		}
		if(cc != null && cc.length > 0){
			msg.setCc(cc);
		}
		msg.setSubject(title);
		msg.setText(content == null ? "" : content);
		mailSender.send(msg);
	}
}
