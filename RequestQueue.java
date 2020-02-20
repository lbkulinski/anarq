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
    int MAX_REQUESTS = 100;
    boolean acceptingRequests;
    PriorityQueue<SongRequest> songQueue = new PriorityQueue<SongRequest>(new VotesComparator());

    public RequestQueue() {
        acceptingRequests = true;
    }

    public boolean addSong(SongRequest song) {
        if (songQueue.contains(song)) {
            System.out.println("Request Failed: Queue already contains " + song.name);
            return false;
        }

        if (!acceptingRequests) {
            System.out.println("Request Failed: Queue is no longer accepting requests");
            return false;
        }

        if (songQueue.size() < MAX_REQUESTS) {
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

    public SongRequest getNextSong() {
        return songQueue.poll();
    }

    public void clear() {
        songQueue.clear();
    }

    public boolean isEmpty() {
        return songQueue.isEmpty();
    }
}