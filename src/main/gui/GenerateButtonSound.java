package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GenerateButtonSound {

    public GenerateButtonSound(String soundFile) {
        playSound(soundFile);
    }

    // EFFECTS: plays sound from file specific to when it is called
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
