package com.anarq.core;

import org.bson.Document;
import com.anarq.database.*;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for account operations associated with the AnarQ Application.
 * 
 * @version April 23, 2020
 */
@RestController
public class AccountController {
    /**
     * Signs up a user for an account with the specified account information.
     *
     * @param username the username to be used in the operation
     * @param email the email to be used in the operation
     * @param firstname the first name to be used in the operation
     * @param lastname the last name to be used in the operation
     * @param day the day to be used in the operation
     * @param month the month to be used in the operation
     * @param year the year to be used in the operation
     * @param password the password to be used in the operation
     * @return the result of the sign up action
     */
    @PutMapping("/sign-up")
    public String signUpForAccount(@RequestParam(value="username", defaultValue="username") String username,
                                   @RequestParam(value="email", defaultValue="no_email") String email,
                                   @RequestParam(value="firstname", defaultValue="joe") String firstname,
                                   @RequestParam(value="lastname", defaultValue="swanson") String lastname,
                                   @RequestParam(value="day", defaultValue="1") int day,
                                   @RequestParam(value="month", defaultValue="1") int month,
                                   @RequestParam(value="year", defaultValue="2000") int year,
                                   @RequestParam(value="password", defaultValue="password") String password) {
        CreateUser newUser = new CreateUser(username, password, firstname, lastname, email, day, month, year);

        return newUser.addJammer();
    } //signUpForAccount

    /**
     * Logs the user with the specified username and password into their account.
     *
     * @param username the username to be used in the operation
     * @param password the password to be used in the operation
     * @return the result of the log in action
     */
    @PutMapping("/log-in")
    public String loginToAccount(@RequestParam(value="username", defaultValue="username") String username,
                                 @RequestParam(value="password", defaultValue="password") String password) {
        FindUser userLogin = new FindUser(username);
        boolean enabled;
        String enabledFieldName = "enabled";
        boolean defaultValue = true;
        
        if (userLogin == null) {
            return "Login Failed.";
        } //end if

        Document info = userLogin.attemptLogin(password);
        
        if (info == null) {
            return "Login Failed.";
        } //end if
        
        if(info.get("user-id") == null) {
            return "Login Failed.";
        } //end if

        enabled = info.getBoolean(enabledFieldName, defaultValue);

        if (!enabled) {
            return "Account Disabled.";
        } //end if
        
        return (String) info.get("user-id");
    } //loginToAccount

    /**
     * Returns the account information for the user with the specified user ID.
     *
     * @param userKey the user ID to be used in the operation
     * @return the account information for the user with the specified user ID
     */
    @GetMapping("/get-account-info")
    public AccountInfo getAccountInfo(@RequestParam(value="userkey", defaultValue="no_user_key") String userKey) {
        FindUser fu = new FindUser("user-id", userKey);

        return fu.findAccountInfo();
    } //getAccountInfo
}