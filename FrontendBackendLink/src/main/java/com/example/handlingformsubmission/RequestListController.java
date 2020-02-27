package com.example.handlingformsubmission;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;


@RestController
public class RequestListController {

	SongRequest currentSong = new SongRequest("Revolution 9", "The Beatles", "pcrowne");
	List<SongRequest> songRequests = new ArrayList<SongRequest>();
  
	public RequestListController() {
	
		songRequests.add(new SongRequest("Smells Like Teen Spirit", "Nirvana", "pcrowne"));
		songRequests.add(new SongRequest("Money Machine", "100 Gecs", "pcrowne"));
		songRequests.add(new SongRequest("My Boy", "Car Seat Headrest", "pcrowne"));
		songRequests.add(new SongRequest("You Shook Me", "Led Zeppelin", "pcrowne"));
		songRequests.add(new SongRequest("Paranoid Android", "Radiohead", "pcrowne"));
		
	}
	
	@GetMapping("/song-requests")
	public List<SongRequest> getSongRequests() {
		
		Collections.sort(songRequests, new Comparator<SongRequest>() {
			@Override
			public int compare(SongRequest a, SongRequest b) {
				if (a.getScore() < b.getScore())
					return 1;
				if (a.getScore() > b.getScore())
					return -1;
				return 0;
			}
		});
		
		return songRequests;
	}
	
	@GetMapping("/current-song")
	public SongRequest getCurrentSong() {
		return currentSong;
	}
	
	@PutMapping("/add-score/{id}")
	public SongRequest addScoreToRequest(@PathVariable long id) {
		
		for (int i = 0; i < songRequests.size(); i++) {
			
			if(songRequests.get(i).getId() == id) {
				
				songRequests.get(i).adjustScore(1);
				
				return songRequests.get(i);
				
			}
			
		}
		
		return songRequests.get(0);
		
	}
	
	@PutMapping("/sub-score/{id}")
	public SongRequest subScoreToRequest(@PathVariable long id) {
		
		for (int i = 0; i < songRequests.size(); i++) {
			
			if(songRequests.get(i).getId() == id) {
				
				songRequests.get(i).adjustScore(-1);
				
				return songRequests.get(i);
				
			}
			
		}
		
		return songRequests.get(0);
		
	}

}