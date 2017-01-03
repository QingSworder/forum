package com.kaishengit.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static void sendHtmlEmail(String toAddress,String subject,String context) {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(Config.get("email_smtp"));
        htmlEmail.setSmtpPort(Integer.valueOf(Config.get("email_port")));
        htmlEmail.setAuthentication(Config.get("email_username"),Config.get("email_password"));
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setStartTLSEnabled(true);

        try {
            htmlEmail.setFrom(Config.get("email_frommail"));
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(context);
            htmlEmail.addTo(toAddress);

            htmlEmail.send();
        } catch (EmailException ex) {
            logger.error("向{}发送邮件失败",toAddress);
            throw new RuntimeException("发送邮件失败:" + toAddress);
        }

    }

}