package com.briller.service;

import com.briller.exception.UserAlreadyExistsException;
import com.briller.model.Users;
import com.briller.repository.UserRepository;
import com.briller.utility.MessageUtility;
import com.briller.utility.TokenUtility;
import io.micrometer.core.instrument.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Description("service for users")
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    @Qualifier(value = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OtpService otpService;

    @Value("${briller.secret}")
    private String applicationSecret;


    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private MailSmsService mailService;

    /**
     * signup function for users
     * @param user
     * @return
     * @throws Exception
     */

    public boolean registerUser(Users user) throws Exception {

        try{
        if (userExists(user.getUserName())) {
            LOGGER.info("email is already registered for username"+user.getUserName());
            throw new UserAlreadyExistsException(user.getUserName()+" email is already registered.");

                    }}
        catch(Exception e)
        {
            LOGGER.error("Error in executing userExists function"+e.getMessage());
            throw e;
        }
        if (!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (isValidEmail(user.getUserName())) {
                try{
                user.setEmail(user.getUserName());
                LOGGER.info("execution of email as username is successFull for"+user.getUserName());
                }
                catch(Exception e)
                {
                    LOGGER.error("error in setting email as username for"+user.getUserName());
                }
            } else {
                try{
                user.setPhoneNbr(user.getUserName());
                LOGGER.info("execution of phone number as username is successFull for"+user.getUserName());
                }
                catch (Exception e)
                {
                    LOGGER.error("error in setting phoneno as email for"+user.getUserName());
                }
            }

            try{
            String token = createToken(user, false);
            user.setToken("1");
            userRepository.save(user);
            LOGGER.info("creation of token and saving userName successFull for "+user.getUserName());
            }
            catch(Exception e)
            {
                LOGGER.error("error in creating and setting and saving username");
            }

            }
        return true;


    }

    /**
     * checks for duplicate users
     * @param userName
     * @return
     */
    private boolean userExists(String userName){
        try{
        if(!StringUtils.isEmpty(userName)){
            Optional<Users>  usersOptional = userRepository.findByUserName(userName);
            return usersOptional.isPresent();
        }LOGGER.info("Execution of userExists function successfully for "+userName);
        }
        catch (Exception e)
        {
            LOGGER.error("Error in executing findBYUserName"+e.getMessage());
            throw e;
        }
        return false;
    }

    /**
     * token creation function
     * @param user
     * @param save
     * @return
     */
    public String createToken(Users user, Boolean save) {
        try{
        String userName= user.getUserName();
        String token = isValidEmail(userName)?passwordEncoder.encode(applicationSecret+userName):tokenUtility.generatePhoneToken();
        if (save) {
            user.setToken(token);
            this.userRepository.save(user);
        }
            LOGGER.info("token generated successfully and saved"+userName);
        return token;

        }
        catch (Exception e)
        {
            LOGGER.error("problem in token generation"+e.getMessage());
            throw e;
        }
    }

    /**
     * checks for correct format of email
     * @param email
     * @return
     */

    public boolean isValidEmail(String email) {
        boolean matchFound;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        matchFound = m.matches();
        return matchFound;
    }

    /**
     *generates otp and send to mail or sms
     * @param userName
     * @return
     */

    public Boolean processForgotPassword(String userName) {
        Optional<Users> users = userRepository.findByUserName(userName);
        String responseMsg;

        if(!users.isPresent()) {
            responseMsg = MessageUtility.getMessage("nouserfound") + userName;
            return false;
        }
        else{
            Users user=users.get();
            String username=user.getUserName();
            int key = otpService.generateOtp(username);
            mailService.sendResetPassword(username, key);
            responseMsg = MessageUtility.getMessage("resetpasswordotp");

            return true;
        }


    }

    /**
     * validating otp
     * @param userName
     * @param otpNumber
     * @return
     */
    public Boolean validateOtp(String userName,Integer otpNumber)
    {
        Boolean s=otpService.validateOTP(userName,otpNumber);
        if(s)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * reset password with new password
     * @param user
     * @return
     */
    public Boolean resetPassword(Users user) {

        Optional<Users> users = userRepository.findByUserName(user.getUserName());
        System.out.println(users);
        if(users.isPresent()){
            Users userFound = users.get();
            System.out.println(userFound);
            userFound.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userFound);
            mailService.sendresetmail(userFound.getUserName(), userFound.getToken());
                return true;
        }
        else
        {
            return false;
        }

    }




}
