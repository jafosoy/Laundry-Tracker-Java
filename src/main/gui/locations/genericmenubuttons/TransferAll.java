package gui.locations.genericmenubuttons;

import gui.GenerateButtonSound;
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
        new GenerateButtonSound("beep-07.wav");
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(null,
                "You are about to transfer all clothes. Proceed?",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options, options[0]);
        if (n == JOptionPane.YES_OPTION) {
            new GenerateButtonSound("transferSound.wav");
            currentLocation.transferAllClothes(targetLocation);
            JOptionPane.showMessageDialog(null,
                    "All clothes transferred to " + targetLocation.getUsername() + ".");

        } else {
            JOptionPane.showMessageDialog(null, "No clothes transferred.");
        }
    }
}

