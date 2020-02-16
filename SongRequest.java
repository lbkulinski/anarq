public class SongRequest {
    String id;
    String album;
    String name;
    String artist;
    String genre;
    int votes;

    public SongRequest(String id, String album, String name, String artist, String genre) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.votes = 0;
    }
}