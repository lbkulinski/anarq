package com.anarq.update.password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UpdatePasswordController {
    @GetMapping("/updatePassword")
    public String updateUsernameForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updatePassword";
    } //updateUsernameForm

    @PostMapping("/updatePassword")
    public String updateUsernameSubmit(@ModelAttribute UserInformation userInformation) {
        return "updatePasswordResult";
    } //updateUsernameSubmit
}
