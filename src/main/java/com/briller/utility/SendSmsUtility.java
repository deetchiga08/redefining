package com.briller.utility;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * This class is used to send mail through pilvo sms gateway
 */
@Component
public class SendSmsUtility {

    @Value("${briller.sms.authid}")
    private String authId;

    @Value("${briller.sms.authtoken}")
    private String authToken;

    @Value("${briller.sms.smsfrom}")
    private String smsFrom;

    private static final Logger logger = LoggerFactory.getLogger(SendSmsUtility.class);

    public void sendSms(String dst, String text) {

        RestAPI api = new RestAPI(authId, authToken, "v1");
        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("src", smsFrom);
        parameters.put("dst", dst);
        parameters.put("text", text);

        try {
            MessageResponse msgResponse = api.sendMessage(parameters);
            if (msgResponse.serverCode == 202) {
                // Print the Message UUID
                logger.info("Message UUID : " + msgResponse.messageUuids.get(0).toString());
            } else {
                logger.error("sendSms error" + msgResponse.error);
            }
        } catch (PlivoException e) {
            logger.error("sendSms" + e.getLocalizedMessage());
        }

    }


}
