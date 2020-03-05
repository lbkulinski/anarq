import java.time.LocalTime;

public class QueueTests {
    RequestQueue queue;
    ConnectedClient jammer = new ConnectedClient("Nick", "clientIp", Permission.JAMMER, LocalTime.now());
    SongRequest song0 = new SongRequest(0, "album", "name" + 0, "artist", "Pop", jammer.ipAddress);
    SongRequest song1 = new SongRequest(1, "album", "name" + 1, "artist", "Pop", jammer.ipAddress);
    SongRequest song2 = new SongRequest(2, "album", "name" + 2, "artist", "Pop", jammer.ipAddress);
    SongRequest song3 = new SongRequest(3, "album", "name" + 3, "artist", "Pop", jammer.ipAddress);
    SongRequest song4 = new SongRequest(4, "album", "name" + 4, "artist", "Pop", jammer.ipAddress);

    public void addSongs() {
        MusicChooser musicChooser = new MusicChooser();
        musicChooser.addValidGenre("Pop");
        System.out.println("Creating queue with max 5 and valid genre Pop...");
        queue = new RequestQueue(musicChooser, 5, true);

        System.out.println("Adding 5 songs...");
        queue.addSong(song0);
        queue.addSong(song1);
        queue.addSong(song2);
        queue.addSong(song3);
        queue.addSong(song4);
    }

    public void displayQueue() {
        QueueUI display = new QueueUI();
        display.populateQueue(queue.songQueue);
    }


    public void fillQueue() {
        addSongs();
        displayQueue();
    }

    public boolean addInvalidGenre() {
        MusicChooser musicChooser = new MusicChooser();
        musicChooser.addValidGenre("Pop");
        System.out.println("Creating queue with max 5 and valid genre Pop...");
        queue = new RequestQueue(musicChooser, 5, true);

        System.out.println("Attempting to add invalid genre...");
        return queue.addSong(new SongRequest(4, "album", "name", "artist", "Rock", "clientIp"));
    }

    public boolean addSongIntoFullQueue() {
        addSongs();
        System.out.println("Adding one more song...\n");
        queue.addSong(new SongRequest(5, "album", "name", "artist", "Pop", "clientIp"));
        return false;
    }

    public void removeOneSong() {
        addSongs();
        System.out.println("\nRemoving one song...\n");
        queue.removeSong(song4, jammer);

        displayQueue();
    }

    public boolean removeNotYourSong() {
        addSongs();
        System.out.println("\nRemoving someone else's song...\n");
        ConnectedClient otherUser = new ConnectedClient("Nick", "clientIp", Permission.JAMMER, LocalTime.now());
        return queue.removeSong(song4, otherUser);
    }

    public boolean removePlayingSong() {
        addSongs();
        System.out.println("\nRemoving a song that is playing...\n");
        song4.playing = true;
        return queue.removeSong(song4, jammer);
    }

    public boolean addDuplicateSong() {
        addSongs();
        System.out.println("Adding duplicate song...\n");
        return queue.addSong(new SongRequest(2, "album", "name2", "artist", "Pop", "clientIp"));
    }

    public boolean addSongNoLongerAccepting() {
        addSongs();
        queue.acceptingRequests = false;
        System.out.println("\nNo longer accepting songs/add a song...\n");
        return queue.addSong(new SongRequest(7, "album", "name7", "artist", "Pop", "clientIp"));
    }

    public void likeAndDislikeSongs() {
        addSongs();
        System.out.println("\nLike some songs...");
        queue.likeSong(song1, "like");
        queue.likeSong(song2, "like");
        queue.likeSong(song3, "like");
        queue.likeSong(song1, "like");
        queue.likeSong(song1, "like");
        queue.dislikeSong(song0, "like");
        displayQueue();
    }

    public void likeYourOwnSong() {
        addSongs();
        System.out.println("\nLike some songs...");
        queue.likeSong(song1, "like");
        queue.likeSong(song2, "like");
        queue.likeSong(song3, "like");
        queue.likeSong(song1, "like");
        queue.likeSong(song1, "like");
        queue.dislikeSong(song0, "like");

        System.out.println("Like my own song...");
        queue.likeSong(song1, "clientIp");
        displayQueue();
    }

    public void playSongs() {
        addSongs();
        System.out.println("Play all songs...");
        while (!queue.isEmpty()) {
            SongRequest playSong = queue.playNextSong();
            System.out.println("Playing: " + playSong.votes + " Name: " + playSong.name);
        }
        displayQueue();
    }
}