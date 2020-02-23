package com.example.handlingformsubmission;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

  @GetMapping("/login")
  public String greetingForm(Model model) {
    model.addAttribute("loginInfo", new LoginInfo());
    return "login";
  }

  @PostMapping("/login")
  public String greetingSubmit(@ModelAttribute LoginInfo loginInfo) {
	  
	  if (loginInfo.getIp().equals("SERVERTEST0")) {
	  
		return "clienthome";
	  
	  }
    
	return "error";
	
  }

}