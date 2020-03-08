package com.anarq.update.username;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for updating a user's username.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 8, 2020
 */
@Controller
public final class UpdateUsernameController {
    /**
     * Displays the form for updating a user's username and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the username update form
     */
    @GetMapping("/updateUsername")
    public String updateUsernameForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateUsername";
    } //updatePasswordForm

    /**
     * Displays the result after attempting to update the user's username.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the username update result
     */
    @PostMapping("/updateUsername")
    public String updateUsernameSubmit(@ModelAttribute UserInformation userInformation) {
        return "updateUsernameResult";
    } //updatePasswordSubmit
}
