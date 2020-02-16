public class SongRequest {
    String id;
    String album;
    String name;
    String artist;
    String genre;
    boolean playing;
    int votes;
    int clientIp;

    public SongRequest(String id, String album, String name, String artist, String genre, int clientIp) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.votes = 0;
        this.clientIp = clientIp;
        this.playing = false;
    }

    public void likeSong(int clientIp) {
        if (clientIp != getClientIp() && !playing) {
            votes++;
        }
        //updateQueue()
    }

    public void dislikeSong(int clientIp) {
        if (clientIp != getClientIp() && !playing) {
            votes--;
        }
        //updateQueue()
    }
    
    String getId() {
        return this.id;
    }

    String getAlbum() {
        return this.album;
    }

    String getName() {
        return this.name;
    }

    String getArtist() {
        return this.artist;
    }

    String getGenre() {
        return this.genre;
    }

    int getVotes() {
        return this.votes;
    }

    int getClientIp() {
        return this.clientIp;
    }
}