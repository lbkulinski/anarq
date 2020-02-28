public class SongRequest {
    int id;
    String album;
    String name;
    String artist;
    String genre;
    String clientIp;
    boolean playing;
    int votes;

    public SongRequest(int id, String album, String name, String artist, String genre, String clientIp) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.votes = 0;
        this.clientIp = clientIp;
        this.playing = false;
    }

    public void likeSong(String clientIp) {
        if (clientIp != getClientIp() && !playing) {
            this.votes++;
        }
        //updateQueue()
    }

    public void dislikeSong(String clientIp) {
        if (clientIp != getClientIp() && !playing) {
            this.votes--;
        }
        //updateQueue()
    }
    
    public int getId() {
        return this.id;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getVotes() {
        return this.votes;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass() && this.id == ((SongRequest) o).id);
    }

    public String printSongInfo() {
        return (getName() + ": id(" + getId() + ") artist(" + getArtist() +") album(" + getAlbum() + ") genre(" + getGenre() + ") clientIp(" +
                getClientIp() + ") voteScore(" + getVotes() + ")");
    }
}