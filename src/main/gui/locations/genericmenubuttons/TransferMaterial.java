package gui.locations.genericmenubuttons;

import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TransferMaterial extends TransferSpecific {

    public TransferMaterial(JDesktopPane mainDesktop, LaundryLocation current, LaundryLocation target) {
        super(mainDesktop, current, target, "Material");
    }
}
