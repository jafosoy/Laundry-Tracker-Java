package gui.locations.genericmenubuttons;

import model.locations.LaundryLocation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

// represents the action to be taken when the user wants to transfer all clothing from the current location
public class TransferAll extends AbstractAction {

    private LaundryLocation currentLocation;
    private LaundryLocation targetLocation;


    public TransferAll(LaundryLocation current, LaundryLocation target) {
        super("Transfer All Clothing");
        this.currentLocation = current;
        this.targetLocation = target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(null,
                "You are about to transfer all clothes. Proceed?",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options, options[0]);
        if (n == JOptionPane.YES_OPTION) {
            playSound("transferSound.wav");
            currentLocation.transferAllClothes(targetLocation);
            // TODO: need to add transfer sound
            JOptionPane.showMessageDialog(null,
                    "All clothes transferred to " + targetLocation.getUsername() + ".");

        } else {
            JOptionPane.showMessageDialog(null, "No clothes transferred.");
        }
    }

    // EFFECTS: plays sound from file specific to when it is called
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}

