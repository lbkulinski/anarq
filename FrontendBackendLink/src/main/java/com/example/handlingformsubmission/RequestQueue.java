package com.example.handlingformsubmission;

import java.util.ArrayList;
import java.util.Comparator; 
import java.util.PriorityQueue;
  
class VotesComparator implements Comparator<SongRequest> { 
    @Override
    public int compare(SongRequest song1, SongRequest song2) 
    { 
        if (song1.votes < song2.votes) {
            return 1; 
        }
        else if (song1.votes > song2.votes) {
            return -1; 
        }
        else {
            return 0; 
        }
    }
} 

public class RequestQueue {
    int maxRequests;
    boolean acceptingRequests;
    boolean autoDJ;
    MusicChooser musicChooser;
    ArrayList<SongRequest> songs = new ArrayList<SongRequest>();
    PriorityQueue<SongRequest> songQueue = new PriorityQueue<SongRequest>(new VotesComparator());

    public RequestQueue() {
        this.acceptingRequests = true;
        this.maxRequests = 50;
        this.musicChooser = new MusicChooser();
        this.autoDJ = false;
    }

    public RequestQueue(MusicChooser musicChooser, int maxRequests, boolean acceptingRequests) {
        this.maxRequests = maxRequests;
        this.musicChooser = musicChooser;
        this.acceptingRequests = acceptingRequests;
        this.autoDJ = false;
    }

    public int getMaxRequests() {
        return this.maxRequests;
    }

    public boolean addSong(SongRequest song) {
        if (!musicChooser.isValidGenre(song.genre)) {
            System.out.println("Request Failed: Genre (" + song.genre + ") is not accepted in the queue");
            return false;
        }
        if (songQueue.contains(song)) {
            System.out.println("Request Failed: Queue already contains " + song.name);
            return false;
        }

        if (!acceptingRequests) {
            System.out.println("Request Failed: Queue is no longer accepting requests");
            return false;
        }

        if (songQueue.size() < getMaxRequests()) {
            this.autoDJ = false;
            return songQueue.add(song);
        }
        else {
            System.out.println("Request Failed: Queue is full");
            return false;
        }
    }

    public SongRequest getSong(SongRequest song) {
        return songs.get(songs.indexOf(song));
    }

    public void likeSong(SongRequest song, String client) {
        boolean accepting = this.acceptingRequests;
        this.acceptingRequests = true;
        songQueue.remove(song);
        song.likeSong(client);
        songQueue.add(song);
        if (!accepting) {
            this.acceptingRequests = false;
        }
    }

    public void dislikeSong(SongRequest song, String client) {
        boolean accepting = this.acceptingRequests;
        this.acceptingRequests = true;
        songQueue.remove(song);
        song.dislikeSong(client);
        songQueue.add(song);
        if (!accepting) {
            this.acceptingRequests = false;
        }
    }

    public boolean removeSong(SongRequest song, ConnectedClient client) {
        if ((client.ipAddress.equals(song.clientIp) || (client.permissionLevel == Permission.DJ)) && !song.playing) {
            boolean ret = songQueue.remove(song);
            if (isEmpty()) {
                this.autoDJ = true;
            }
            return ret;
        }
        else {
            if (song.playing) {
                System.out.println("You can not remove a song that is playing");
            }
            else {
                System.out.println("You can only remove your own song");
            }
            return false;
        }
    }

    public SongRequest viewNextSong() {
        return songQueue.peek();
    }

    public SongRequest playNextSong() {
        SongRequest ret = songQueue.poll();
        if (isEmpty()) {
            this.autoDJ = true;
        }
        return ret;
    }

    public void clear() {
        songQueue.clear();
        this.autoDJ = true;
    }

    public boolean isEmpty() {
        return songQueue.isEmpty();
    }

    public void printQueue() {
        System.out.println("Queue <");
        for (SongRequest song : songQueue) {
            System.out.println("    " + song.name);
        }
        System.out.println(">");
    }
}