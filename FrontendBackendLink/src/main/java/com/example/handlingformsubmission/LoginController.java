package com.example.handlingformsubmission;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	public static LoginInfo globalLoginInfo = new LoginInfo();

	public static String lastHost;
	public static ConnectedUser lastClientInfo;

  @GetMapping("/")
  public String onPageLoad(Model model) {
    model.addAttribute("loginInfo", globalLoginInfo);
	
    return "login";
  }

  @PostMapping("/client")
  public String loginClient(@ModelAttribute LoginInfo loginInfo) {
	  
	  System.out.println(loginInfo.getIp());
	  
	  Session session = RequestListController.getSessionForId(loginInfo.getIp());
	  
	  if (session != null) {
	  
		ConnectedUser newCU = new ConnectedUser(loginInfo.getUsername());
	  
		session.addClient(newCU);
	  
		if (loginInfo.getIp() != null)
			lastHost = loginInfo.getIp();
			lastClientInfo = newCU;
	  
		return "clienthome";
	  
	  }
    
	return "login";
	
  }
  
  @PostMapping("/server")
  public String loginServer(@ModelAttribute LoginInfo loginInfo) {
	  
		
		Session newSession = new Session();
		RequestListController.sessions.add(newSession);
		System.out.println(newSession.getId());
	  
		lastHost = (String) (newSession.getId() + "");
	  
		return "serverhome";
	
  }


}