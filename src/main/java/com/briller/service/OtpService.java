package com.briller.service;

import com.briller.model.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Description("this otp service class for generating and validating otp")
@Service
public class OtpService {

    @Autowired
    OtpGenerator otpGenerator;

    @Autowired
    UserService userService;

    /**
     * generates otp and checks for the otp not to be in negative number
     * @param key (username)
     * @return
     */
    public Integer generateOtp(String key)
    {
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1)
        {
            LOGGER.error("OTP generator is not working...");
            return 0;
        }
        else{
            return otpValue;
        }
    }

    /**
     * validating otp
     * @param key (username)
     * @param otpNumber (which sent in mail or phone no)
     * @return
     */

    public Boolean validateOTP(String key, Integer otpNumber)
    {
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP.equals(otpNumber))
        {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}
