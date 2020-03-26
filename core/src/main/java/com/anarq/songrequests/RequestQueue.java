package com.anarq.songrequests;

import java.util.ArrayList;
import java.util.Comparator; 
import java.util.PriorityQueue;
import com.anarq.core.*;

/* 
	VotesComparator
		Contains information about comparing the votes of two songs
	
	Author(s):
		Nick
*/
class VotesComparator implements Comparator<SongRequest> { 

	/* Compare two song requests based on vote count */
    @Override
    public int compare(SongRequest song1, SongRequest song2) 
    { 
        if (song1.getVotes() < song2.getVotes()) {
            return 1; 
        }
        else if (song1.getVotes() > song2.getVotes()) {
            return -1; 
        }
        else {
            return 0; 
        }
    }
} 

/* 
	RequestQueue
		Contains methods pertaining to the organization and deployment of songrequests
	
	Author(s):
		Nick
		Patrick
*/
public class RequestQueue {
	
	// Private Varaibles
    private int maxRequests;
    private int minBPM;
    private int maxBPM;
    private boolean acceptingRequests;
    private boolean autoDJ;
    private boolean explicitFilter;
    private boolean visibility; // true = public access, false = private access
    private MusicChooser musicChooser;
	private SongRequest currentSong = null;
    private ArrayList<SongRequest> songs = new ArrayList<SongRequest>();
    private PriorityQueue<SongRequest> songQueue = new PriorityQueue<SongRequest>(new VotesComparator());
    private ArrayList<SongRequest> overrides = new ArrayList<SongRequest>();

	/* Creates a new RequestQueue Instance */
    public RequestQueue() {
        this.acceptingRequests = true;
        this.maxRequests = 50;
        this.musicChooser = new MusicChooser();
        this.autoDJ = false;
        this.explicitFilter = false;
        this.minBPM = 0;
        this.maxBPM = Integer.MAX_VALUE;
        this.visibility = true;
    }

	/* Creates a new RequestQueue Instance using the inputs */
    public RequestQueue(MusicChooser musicChooser, int maxRequests, boolean acceptingRequests, boolean explicitFilter, int minBPM, int maxBPM, boolean visibility) {
        this.maxRequests = maxRequests;
        this.musicChooser = musicChooser;
        this.acceptingRequests = acceptingRequests;
        this.autoDJ = false;
        this.explicitFilter = explicitFilter;
        if (minBPM > maxBPM || minBPM < 0 || maxBPM < 0) {
            this.minBPM = 0;
            this.maxBPM = Integer.MAX_VALUE;
        }
        else {
            this.minBPM = minBPM;
            this.maxBPM = maxBPM;
        }
        this.visibility = visibility;
    }

	/* Returns the max number of requests */
    public int getMaxRequests() {
        return this.maxRequests;
    }

    public boolean hasExplicitFilter() {
        return this.explicitFilter;
    }

    public int getMinBPM() {
        return this.minBPM;
    }

    public int getMaxBPM() {
        return maxBPM;
    }

    public boolean getVisibility() {
        return visibility;
    }

	/* Adds a song to the request queue */
    public boolean addSong(SongRequest song) {
        if (!musicChooser.isValidGenre(song.getGenre())) {
            System.out.println("Request Failed: Genre (" + song.getGenre() + ") is not accepted in the queue");
            return false;
        }
        if (isSongAlreadyInQueue(song)) {
            System.out.println("Request Failed: Queue already contains " + song.getName());
            return false;
        }

        if (!acceptingRequests) {
            System.out.println("Request Failed: Queue is no longer accepting requests");
            return false;
        }

        if (explicitFilter && song.songInfo.isExplicit) { //checks if song is explicit AND explicit filter is on
            //add song to override list and return false
            System.out.println("Request Pending: Explicit Content is not allowed ... Override request sent to host");
            overrides.add(song);
            return false;
        }

        if (song.songInfo.bpm > this.maxBPM || song.songInfo.bpm < this.minBPM) {
            System.out.println("Request Pending: BPM is not in the allowed range ... Override request sent to host");
            overrides.add(song);
            return false;
        }

        if (songQueue.size() < getMaxRequests()) {
            this.autoDJ = false;
			System.out.println("Song added to queue: " + song.getId());
            return songQueue.add(song);
        }
        else {
            System.out.println("Request Failed: Queue is full");
            return false;
        }
    }

    public void removeOverride(int i, boolean decision) {
        //if index i is out of bounds
        if (i >= overrides.size() || i < 0) {
            System.out.println("Index out of bounds!");
            return;
        }
        //if decision is true, host accepted override for song at index i, add to queue and remove from override list
        if (decision) {
            songQueue.add(overrides.remove(i));
        }
        //if decision is false, host declined override for song at index i, remove from override list, do not add to queue
        if (!decision) {
            overrides.remove(i);
        }
    }

	/* Returns a song from the queue */
    public SongRequest getSong(SongRequest song) {
        return songs.get(songs.indexOf(song));
    }

	/* Attempt to like a song */
    public boolean likeSong(String songId, String client) {
		
		SongRequest song = getSongFromQueue(songId);
		
		if(song == null) {
			return false;
		}
		
        boolean accepting = this.acceptingRequests;
        this.acceptingRequests = true;
        songQueue.remove(song);
        song.likeSong(client);
        songQueue.add(song);
		
        if (!accepting) {
            this.acceptingRequests = false;
        }
		
		return true;
		
    }

	/* Attempt to dislike a song */
    public boolean dislikeSong(String songId, String client) {
		
		SongRequest song = getSongFromQueue(songId);
		
		if(song == null) {
			return false;
		}
		
        boolean accepting = this.acceptingRequests;
        this.acceptingRequests = true;
        songQueue.remove(song);
        song.dislikeSong(client);
        songQueue.add(song);
		
        if (!accepting) {
            this.acceptingRequests = false;
        }
		
		return true;
		
    }

	/* Attempt to remove a song */
    public boolean removeSong(SongRequest song, ConnectedClient client) {
        if ((client.getId().equals(song.getClientIp()) || (client.getPermissionLevel() == Permission.DJ)) && !song.isPlaying()) {
            boolean ret = songQueue.remove(song);
            if (isEmpty()) {
                this.autoDJ = true;
            }
            return ret;
        }
        else {
            if (song.isPlaying()) {
                System.out.println("You can not remove a song that is playing");
            }
            else {
                System.out.println("You can only remove your own song");
            }
            return false;
        }
    }

	/* See which song is next on the queue */
    public SongRequest viewNextSong() {
        return songQueue.peek();
    }

	/* Move the next song to the song being played */
    public SongRequest playNextSong() {
        SongRequest ret = songQueue.poll();
        if (isEmpty()) {
            this.autoDJ = true;
        }
		currentSong = ret;
        return ret;
    }

	/* Clear the queue */
    public void clear() {
        songQueue.clear();
        this.autoDJ = true;
    }

	/* Returns true if the queue is empty */
    public boolean isEmpty() {
        return songQueue.isEmpty();
    }

	/* Returns the song currently being played */
	public SongRequest getCurrentSong() {
		return currentSong;
	}
	
	/* Returns true if that song is already in the queue */
	public boolean isSongAlreadyInQueue(SongRequest song) {
		
		SongRequest[] queue = getSongQueue();
		
		for (int i = 0; i < queue.length; i++) {
			if(queue[i].getId().equals(song.getId())) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	/* Returns true if that song is already in the queue */
	public SongRequest getSongFromQueue(String id) {
		
		SongRequest[] queue = getSongQueue();
		
		for (int i = 0; i < queue.length; i++) {
			if(queue[i].getId().equals(id)) {
				return queue[i];
			}
			
		}
		
		return null;
		
	}
	
	/* Returns the queue of songs to being played */
	public SongRequest[] getSongQueue() {
		
		SongRequest[] output = new SongRequest[songQueue.size()];
		Object[] array = songQueue.toArray();
		
		for (int i = 0; i < songQueue.size(); i++) {
			
			output[i] = (SongRequest) array[i];
			
		}
		
		return output;
	}

	/* Print a representation of the queue out into the console */
    public void printQueue() {
        System.out.println("Queue <");
        for (SongRequest song : songQueue) {
            System.out.println("    " + song.getName());
        }
        System.out.println(">");
    }
	
}