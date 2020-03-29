package com.anarq.core;

import org.springframework.stereotype.Controller;
import java.util.Set;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.util.HashSet;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public final class PageRedirectController {
   
	@GetMapping("/")
    public String redirectConnect() {
        return "connect";
    }
	
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