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

    public boolean removeSong(SongRequest song, String client) {
        if (client.equals(song.clientIp) && !song.playing) {
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

    public static void main(String[] args) {
        SongRequest song0 = new SongRequest(0, "album", "name" + 0, "artist", "Pop", "clientIp");
        SongRequest song1 = new SongRequest(1, "album", "name" + 1, "artist", "Pop", "clientIp");
        SongRequest song2 = new SongRequest(2, "album", "name" + 2, "artist", "Pop", "clientIp");
        SongRequest song3 = new SongRequest(3, "album", "name" + 3, "artist", "Pop", "clientIp");
        SongRequest song4 = new SongRequest(4, "album", "name" + 4, "artist", "Pop", "clientIp");


        MusicChooser musicChooser = new MusicChooser();
        musicChooser.addValidGenre("Pop");
        System.out.println("Creating queue with max 5 and valid genre Pop...");
        RequestQueue queue = new RequestQueue(musicChooser, 5, true);

        System.out.println("Attempting to add invalid genre...");
        queue.addSong(new SongRequest(4, "album", "name", "artist", "Rock", "clientIp"));
        System.out.println();

        System.out.println("Adding 5 songs...");
        queue.addSong(song0);
        queue.addSong(song1);
        queue.addSong(song2);
        queue.addSong(song3);
        queue.addSong(song4);

        QueueUI display = new QueueUI();
        //display.populateQueue(queue.songQueue);

        System.out.println("Adding one more song...\n");
        queue.addSong(new SongRequest(5, "album", "name", "artist", "Pop", "clientIp"));

        System.out.println("\nRemoving one song...\n");
        queue.removeSong(song4, "clientIp");

        System.out.println("\nRemoving a song that is playing...\n");
        song4.playing = true;
        queue.removeSong(song4, "clientIp");

        System.out.println("\nRemoving someone else's song...\n");
        queue.removeSong(song4, "yolo");
        //display.populateQueue(queue.songQueue);

        System.out.println("Adding duplicate song...\n");
        queue.addSong(new SongRequest(2, "album", "name2", "artist", "Pop", "clientIp"));
        //display.populateQueue(queue.songQueue);

        queue.acceptingRequests = false;
        System.out.println("\nNo longer accepting songs/add a song...\n");
        queue.addSong(new SongRequest(7, "album", "name7", "artist", "Pop", "clientIp"));
        System.out.println();

        System.out.println("\nLike some songs...");
        queue.likeSong(song1, "yolo");
        queue.likeSong(song2, "yolo");
        queue.likeSong(song3, "yolo");
        queue.likeSong(song1, "yolo");
        queue.likeSong(song1, "yolo");
        queue.dislikeSong(song0, "yolo");
        System.out.println("song0: " + song0.votes);
        System.out.println("song1: " + song1.votes);
        System.out.println("song2: " + song2.votes);
        System.out.println("song3: " + song3.votes);
        System.out.println("song4: " + song4.votes);

        System.out.println("Like my own song...");
        song1.likeSong("clientIp");
        System.out.println("song0: " + song0.votes);
        System.out.println("song1: " + song1.votes);
        System.out.println("song2: " + song2.votes);
        System.out.println("song3: " + song3.votes);
        System.out.println("song4: " + song4.votes);
        display.populateQueue(queue.songQueue);

        System.out.println("Play all songs...");
        while (!queue.isEmpty()) {
            SongRequest playSong = queue.playNextSong();
            System.out.println("Playing: " + playSong.votes + " Name: " + playSong.name);
        }
        System.out.println("Printing empty queue...");
        System.out.println();
    }
}