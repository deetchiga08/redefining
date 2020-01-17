package com.briller.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtility {

    private static MessageSource messageSource;



    @Autowired
    public MessageUtility(MessageSource messageSource) {
        MessageUtility.messageSource = messageSource;
    }

    /**
     * get message from message properties
     * @param messageKey
     * @param args
     * @return String
     */
    public static String getMessage(String messageKey, Object... args) {

        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());

    }
}
