package com.briller.controller;

import com.briller.Response.ftdcResponse;
import com.briller.config.AuthenticationTokenService;
import com.briller.model.FtdcUserDetails;
import com.briller.model.Users;
import com.briller.repository.UserRepository;
import com.briller.service.UserService;
import com.briller.utility.MessageUtility;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api("Authentication Service for FTDC Questionaire Application")
@RestController
@RequestMapping("/auth")
public class FtdcAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService ftdcUserDetailsService;


    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;


    @Value("${briller.jwt.token.header}")
    private String tokenHeader;

    /**
     *
     * @param user
     * @return Response of signed in user's data
     * @throws Exception
     */

    @Async
    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> signupUser(@RequestBody Users user) throws Exception {
        Map<String, Object> signupResponse = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        boolean success = userService.registerUser(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    /**
     *
     * @param user ( username , password )
     * @return data of logged in user with xauth token
     */

    @PostMapping(value="/user/login")
    public ResponseEntity<?> login(@RequestBody Users user){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = ftdcUserDetailsService.loadUserByUsername(user.getUserName());
        final String token = authenticationTokenService.generateToken(userDetails);
        FtdcUserDetails loggedinUser = (FtdcUserDetails) authentication.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        if(token !=null) {
            headers.add(tokenHeader, token);
        }
        return new ResponseEntity<>( loggedinUser,headers, HttpStatus.OK);
    }

    @PostMapping(value="/user/sampleLogin")
    public ResponseEntity<?> sampleLogin(@RequestBody Users user){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = ftdcUserDetailsService.loadUserByUsername(user.getUserName());
        final String token = authenticationTokenService.generateToken(userDetails);
        FtdcUserDetails loggedinUser = (FtdcUserDetails) authentication.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        if(token !=null) {
            headers.add(tokenHeader, token);
        }
        return new ResponseEntity<>( new ftdcResponse(loggedinUser,token),headers, HttpStatus.OK);
    }

    /**
     *
     * @param user (username),
     *             it generates a otp and sends otp to mail or phone no depends on username
     * @return email sent or otp sent
     */
    @PostMapping("/user/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Users user) {
        String res="OTP code";
        String res1="No user found";
        Boolean responseMsg = userService.processForgotPassword(user.getUserName());
        if(responseMsg)
        {
        return new ResponseEntity<>(new ftdcResponse("true",res), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse("false",res1), HttpStatus.OK);
        }
    }


    /**
     *
     * @param user (new password, username ), new password gets stored
     * @return response entity of reset successfull or fail
     */

    @PostMapping("/user/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Users user) {
        boolean resetSuccess = userService.resetPassword(user);
        if (resetSuccess) {
            return new ResponseEntity<>(new ftdcResponse("true",MessageUtility.getMessage("resetSuccess")), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("false",MessageUtility.getMessage("resetFail")), HttpStatus.OK);
        }
    }


    /**
     *
     * @param userName
     * @param otpNumber (validates otp number which is given as input and otpnumber in cache)
     * @return
     */
    @PostMapping("/user/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam String userName,@RequestParam Integer otpNumber)
    {
        boolean result=userService.validateOtp(userName,otpNumber);
        if(result)
        {
            return  new ResponseEntity<>(new ftdcResponse("true","otp is valid"),HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse("false","otp is invalid"),HttpStatus.OK);
        }
    }




}
