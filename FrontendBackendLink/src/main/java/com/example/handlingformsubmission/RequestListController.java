package com.example.handlingformsubmission;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RequestListController {

	SongRequest[] songRequests;
  
  @GetMapping("/clienthome")
  public String clienthome(Model model) {
	
	System.out.println("Logged in!");
	
	songRequests = new SongRequest[5];
	songRequests[0] = new SongRequest("Smells Like Teen Spirit", "Nirvana");
	songRequests[1] = new SongRequest("Money Machine", "100 Gecs");
	songRequests[2] = new SongRequest("My Boy", "Car Seat Headrest");
	songRequests[3] = new SongRequest("You Shook Me", "Led Zeppelin");
	songRequests[4] = new SongRequest("Paranoid Android", "Radiohead");
	
	model.addAttribute("loginInfo", new LoginInfo());
	model.addAttribute("title", "Is this thing on?");
    model.addAttribute("songRequests", songRequests);
	
    return "clienthome";
	
  }

}