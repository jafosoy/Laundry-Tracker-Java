package gui.locations.genericmenubuttons;

import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
            currentLocation.transferAllClothes(targetLocation);
            // TODO: need to add transfer sound
            JOptionPane.showMessageDialog(null,
                    "All clothes transferred to " + targetLocation.getUsername() + ".");

        } else {
            JOptionPane.showMessageDialog(null, "No clothes transferred.");
        }
    }
}

