
package com.briller.service;

import com.briller.model.Users;
import org.springframework.context.annotation.Description;

import javax.servlet.http.HttpServletRequest;


public interface MailSmsService {

    public void sendMail(String to, String subject, String text);

    public void sendSms(String to, String subject, String text);

    public void sendResetPassword(String to, Integer token);

    public void sendresetmail(String to, String token);

    public void sendNewActivationRequest(String to, String token);

    public void sendErrorEmail(Exception e, HttpServletRequest req, Users user);
}
