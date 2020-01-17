package com.briller.service;

import com.briller.model.Users;
import com.briller.aws.ses.AmazonEmail;
import com.briller.aws.ses.SESProcessor;
import com.briller.model.Mail;
import com.briller.model.Users;
import com.briller.service.MailSmsService;
import com.briller.service.UserService;
import com.briller.utility.MessageUtility;
import com.briller.utility.SendSmsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.accessibility.AccessibleTableModelChange;
import javax.servlet.http.HttpServletRequest;


@Description("implementation of mail service class")
@Service
public class MailSmsServiceImpl implements MailSmsService {

    private static final Logger logger = LoggerFactory.getLogger(MailSmsServiceImpl.class);

    @Value("${briller.email.from}")
    private String fromEmail;

    @Value("${briller.url}")
    private String appUrl;

    @Value("${briller.email.support}")
    private String supportEmail;

    @Value("${briller.email.service}")
    private String emailService;

    @Autowired
    private UserService userService;



    @Autowired
    private SendSmsUtility sendSmsUtility;

    @Autowired
    private MessageUtility messageUtility;

    /**
     * this method is used to send mail
     * @param to
     * @param subject
     * @param text
     */

    public void sendMail(String to, String subject, String text) {
        try {

            SESProcessor.getInstance().add(new AmazonEmail(
                    to,
                    subject,
                    text));

            logger.info("SENT EMAIL: TO=" + to + "|SUBJECT:" + subject + "|TEXT:" + text);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendSms(String to, String subject, String text) {

    }

    /**
     * this method is used to send reset otp to email or phoneno
     * @param to
     * @param token
     */
    public void sendResetPassword(String to, Integer token) {

        String url = messageUtility.getMessage("resetpasswordotp")+ token;
        String subject = messageUtility.getMessage("mailsub");
        String text = messageUtility.getMessage("mailtext")+ url;
        if (userService.isValidEmail(to)) {
            try{
            sendMail(to, subject, text);
            logger.info("the mail sent successfully in sendResetPassword in MailSmsServiceImpl"+to);
            }
            catch(Exception e)
            {
                logger.error("error in sending email in sendResetPassword method MailSmsServiceImpl"+e.getMessage());
                throw e;
            }
        } else {
            try{
            String sms = messageUtility.getMessage("smstext")+ token;
            sendSmsUtility.sendSms(to, sms);
            logger.info("sent sms successfully in sendResetPassword method in MailSmsServiceImpl"+to);
            }
            catch(Exception e)
            {
                logger.error("error in sending sms in sendResetPassword method in MailSmsServiceImpl"+e.getMessage());
            }
        }
    }

    /**
     * this method send email or msg with confirmation message of reset successFull or failure
     * @param to
     * @param token
     */

    public void sendresetmail(String to, String token) {

        String url = messageUtility.getMessage("reset");
        String subject = messageUtility.getMessage("activatesub");
        String text =url+"\n"+messageUtility.getMessage("activatetext")+"\n";
        if (userService.isValidEmail(to)) {
            try {
                sendMail(to, subject, text);
                logger.info("sent mail successfully in sendResetMail in MailSmsServiceImpl"+to);
            }
            catch(Exception e)
            {
                logger.error("error in sending mail in senfResetMail in MailSmsServiceImpl"+e.getMessage());
                throw e;
            }
        } else {
            try {

            String sms = messageUtility.getMessage("smsactivatetext")+" "+ url;
            sendSmsUtility.sendSms(to, sms);
            logger.info("sent sms successFully in sendResetMail in MailSmsServiceImpl"+to);
            }
            catch (Exception e)
            {
                logger.error("error in sending sms in sendResetMail method in mailSmsServiceImpl"+e.getMessage());
            }
        }
    }


    public void sendNewActivationRequest(String to, String token) {
        try {
            sendresetmail(to, token);
            logger.info("activation mail sent successFully in sendNewActivationRequest in MailSmsServiceImpl "+to);
        }
        catch (Exception e)
        {
            logger.error( "error in sending activation email in sendNewActivationRequest in MailSmsServiceImpl");
        }
    }

    /**
     * method if applicatiomn error occur
     * @param e }
     * @param req
     * @param user
     */
    public void sendErrorEmail(Exception e, HttpServletRequest req, Users user) {
        String subject = "Application Error: " + req.getRequestURL();
        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.getEmail();
        sendMail(supportEmail, subject, text);
    }

}
