package com.anarq.update.password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for updating a user's password.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 8, 2020
 */
@Controller
public class UpdatePasswordController {
    /**
     * Displays the form for updating a user's password and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the password update form
     */
    @GetMapping("/updatePassword")
    public String updatePasswordForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updatePassword";
    } //updatePasswordForm

    /**
     * Displays the result after attempting to update the user's password.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the password update result
     */
    @PostMapping("/updatePassword")
    public String updatePasswordSubmit(@ModelAttribute UserInformation userInformation) {
        return "updatePasswordResult";
    } //updatePasswordSubmit
}
