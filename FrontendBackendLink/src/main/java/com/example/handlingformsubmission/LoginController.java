package com.example.handlingformsubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
  
  @GetMapping("/clienthome")
  public String getSongList(Model model) {
	
	SongRequest[] songRequests = new SongRequest[5];
	songRequests[0] = new SongRequest("Smells Like Teen Spirit", "Nirvana");
	songRequests[1] = new SongRequest("Money Machine", "100 Gecs");
	songRequests[2] = new SongRequest("My Boy", "Car Seat Headrest");
	songRequests[3] = new SongRequest("You Shook Me", "Led Zeppelin");
	songRequests[4] = new SongRequest("Paranoid Android", "Radiohead");
	
    model.addAttribute("songRequests", songRequests);
    return "clienthome";
	
  }

}