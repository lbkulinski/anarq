package com.anarq.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anarq.songrequests.*;

/* 
	LoginController
		Controls the login aspect of anarq
	
	Author(s):
		Patrick
*/
@Controller
public class LoginController {

	// Public variables
	public static LoginInfo globalLoginInfo = new LoginInfo();
	public static String lastHost;
	public static ConnectedClient lastClientInfo;

	/* GET mapping for the homepage on load */
	@GetMapping("/")
	public String onPageLoad(Model model) {
		model.addAttribute("loginInfo", globalLoginInfo);
	
		return "login";
	}

	/* POST mapping for the client */
	@PostMapping("/client")
	public String loginClient(@ModelAttribute LoginInfo loginInfo) {
	  
		System.out.println(loginInfo.getIp());
	  
		Session session = RequestListController.getSessionForId(loginInfo.getIp());
	  
		if (session != null) {
	  
			ConnectedClient newConnectedClient = new ConnectedClient(loginInfo.getUsername(), "" + (int) (Math.random() * 100000.0f), Permission.JAMMER, null);
			session.addClient(newConnectedClient);
	  
		if (loginInfo.getIp() != null)
			
			lastHost = loginInfo.getIp();
			lastClientInfo = newConnectedClient;
			return "clienthome";
	  
		}
    
		return "login";
	
	}
  
	/* POST mapping for the server */
	@PostMapping("/server")
	public String loginServer(@ModelAttribute LoginInfo loginInfo) {
	  
		Session newSession = new Session();
		RequestListController.sessions.add(newSession);
		System.out.println(newSession.getId());
		lastHost = (String) (newSession.getId() + "");
		return "serverhome";
	
	}


}