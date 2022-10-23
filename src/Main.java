import java.io.File;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Directory path: ");
		String directoryPath = scanner.nextLine();
		
		File directory = new File(directoryPath);
		
		if(!directory.isDirectory()) {
			System.out.println("Directory not found!");
			System.out.println("Closing program!");
			System.exit(0);
		}
		
		ListOfMusics musicPlayer = new ListOfMusics();
		for (int i = 0; i < directory.list().length; i++) {
			if(directory.list()[i].contains(".wav")) {
				musicPlayer.addMusic(directory.list()[i]);
			}
		}
		
		String option = "";
		int indexOfMusicOnList = 0;
		
		while(!option.equals("Q") && indexOfMusicOnList < musicPlayer.musicList.size()) {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(directoryPath + "/" + musicPlayer.musicList.get(indexOfMusicOnList).getName()));
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			
			clip.start();
			
			System.out.println("Song playing: " + musicPlayer.musicList.get(indexOfMusicOnList).getName());
			
			while(!option.equals("Q") && clip.getMicrosecondPosition() < clip.getMicrosecondLength()) {
				System.out.print("Play = P\n");
				System.out.print("Previous = <\t");
				System.out.print("Stop = S\t");
				System.out.print("Next = >\t");
				System.out.print("Reset = R\n");
				System.out.println("Quit = Q");
				System.out.print("Select Option: ");
			
				option = scanner.next().toUpperCase();
				
				switch(option) {
				case "P":
					clip.start();
					break;
				case "S":
					clip.stop();
					break;
				case "R":
					clip.setMicrosecondPosition(0);
					break;
				case ">":
					clip.setMicrosecondPosition(clip.getMicrosecondLength());
					indexOfMusicOnList+=1;
					break;
				case "<":
					clip.setMicrosecondPosition(clip.getMicrosecondLength());
					indexOfMusicOnList-=1;
					break;
				case "Q":
					clip.close();
					break;
				default:
					System.out.println("Option not avaiable!");
					break;
				}
			}
			clip.close();
		}
		if(musicPlayer.musicList.size() == 0) {
			System.out.println("There are no songs in the Directory!");
		}
		System.out.println("Closing program!");
		scanner.close();
	}
}
