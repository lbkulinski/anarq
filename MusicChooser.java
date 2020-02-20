import java.util.ArrayList;

public class MusicChooser {
    ArrayList<String> validGenres;
    
    public MusicChooser() {
        validGenres = new ArrayList<String>();
    }

    public void addValidGenre(String genre) {
        validGenres.add(genre);
    }
}