import java.util.ArrayList;

public class ListOfMusics {
	ArrayList<Music> musicList = new ArrayList<Music>();

	public void addMusic(String musicName) {
		Music music = new Music(musicName);
		musicList.add(music);
	}
}
