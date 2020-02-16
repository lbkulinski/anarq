import java.util.Comparator; 
import java.util.PriorityQueue; 
  
class VotesComparator implements Comparator<String> { 
    public int compare(SongRequest song1, SongRequest song2) 
    { 
        return song1.votes.compareTo(song2.votes); 
    } 
} 

public class RequestQueue extends SongRequest {
    int MAX_REQUESTS = 100;
    PriorityQueue<SongRequest> SongQueue = new PriorityQueue<SongRequest>(new VotesComparator); 

    public RequestQueue() {}

    int getMaxRequests() {
        return this.MAX_REQUESTS;
    }
}