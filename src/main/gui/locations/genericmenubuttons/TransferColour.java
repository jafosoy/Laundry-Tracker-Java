package gui.locations.genericmenubuttons;

import model.locations.LaundryLocation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransferColour extends TransferSpecific {

    public TransferColour(JDesktopPane mainDesktop, LaundryLocation current, LaundryLocation target) {
        super(mainDesktop, current, target, "Colour");
    }



}
