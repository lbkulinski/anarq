import java.util.Comparator; 
import java.util.PriorityQueue; 
  
class VotesComparator implements Comparator<SongRequest> { 
    @Override
    public int compare(SongRequest song1, SongRequest song2) 
    { 
        if (song1.votes < song2.votes) {
            return -1; 
        }
        else if (song1.votes > song2.votes) {
            return 1; 
        }
        else {
            return 0; 
        }
    }
} 

public class RequestQueue {
    int maxRequests;
    boolean acceptingRequests;
    MusicChooser musicChooser;
    PriorityQueue<SongRequest> songQueue = new PriorityQueue<SongRequest>(new VotesComparator());

    public RequestQueue() {
        this.acceptingRequests = true;
        this.maxRequests = 50;
        this.musicChooser = new MusicChooser();
    }

    public RequestQueue(MusicChooser musicChooser, int maxRequests, boolean acceptingRequests) {
        this.maxRequests = maxRequests;
        this.musicChooser = musicChooser;
        this.acceptingRequests = acceptingRequests;
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
            return songQueue.add(song);
        }
        else {
            System.out.println("Request Failed: Queue is full");
            return false;
        }
    }

    public boolean removeSong(SongRequest song) {
        return songQueue.remove(song);
    }

    public SongRequest viewNextSong() {
        return songQueue.peek();
    }

    public SongRequest playNextSong() {
        return songQueue.poll();
    }

    public void clear() {
        songQueue.clear();
    }

    public boolean isEmpty() {
        return songQueue.isEmpty();
    }

    public String printQueue() {
        return songQueue.toString();
    }

    public static void main(String[] args) {
        MusicChooser musicChooser = new MusicChooser();
        musicChooser.addValidGenre("Pop");
        System.out.println("Creating queue with max 5 and valid genre Pop...");
        RequestQueue queue = new RequestQueue(musicChooser, 5, true);
        queue.printQueue();

        System.out.println("Attempting to add invalid genre...");
        queue.addSong(new SongRequest(4, "album", "name", "artist", "Rock", "clientIp"));
        System.out.println();

        System.out.println("Adding 5 songs...");
        for (int i = 0; i < 5; i++) {
            queue.addSong(new SongRequest(i, "album", "name" + i, "artist", "Pop", "clientIp"));
        }
        System.out.println();
        queue.printQueue();
        System.out.println();

        System.out.println("Adding one more song...");
        queue.addSong(new SongRequest(5, "album", "name", "artist", "Pop", "clientIp"));

        System.out.println("Removing one song...");
        queue.removeSong(new SongRequest(4, "album", "name4", "artist", "Pop", "clientIp"));
        System.out.println();
        queue.printQueue();
        System.out.println();

        System.out.println("Adding duplicate song...");
        queue.addSong(new SongRequest(2, "album", "name2", "artist", "Pop", "clientIp"));
        System.out.println();
        queue.printQueue();
        System.out.println();

        queue.acceptingRequests = false;
        System.out.println("No longer accepting songs/add a song...");
        queue.addSong(new SongRequest(7, "album", "name", "artist", "Pop", "clientIp"));
        System.out.println();

        System.out.println("Play all songs...");
        while (!queue.isEmpty()) {
            System.out.println("Playing: " + queue.playNextSong().name);
        }
        System.out.println("Printing empty queue...");
        System.out.println();
    }
}