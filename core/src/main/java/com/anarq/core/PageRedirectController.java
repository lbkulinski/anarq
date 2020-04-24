package com.anarq.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A controller for page redirects of the AnarQ Application.
 *
 * @version April 23, 2020
 */
@Controller
public final class PageRedirectController {
    /**
     * Redirects the connection to the connect page.
     *
     * @return the name of the connect page
     */
	@GetMapping("/")
    public String redirectConnect() {
        return "connect";
    } //redirectConnect
	
	/*@GetMapping("/updateUsername")
    public String updateUsernameForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateUsernameForm";
    } //updatePasswordForm
	
	@GetMapping("/updatePassword")
    public String updatePasswordForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updatePasswordForm";
    } //updatePasswordForm
   
    @GetMapping("/updateBio")
    public String updateBioForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateBioForm";
    } //updateBioForm
	
	@GetMapping("/updateProfilePicture")
    public String updateProfilePictureForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateProfilePictureForm";
    } //updateProfilePictureForm*/
}