package gui.locations;

import gui.LaundryTracker;
import gui.exceptions.NoClothingException;
import gui.locations.genericmenubuttons.TransferAll;
import gui.locations.genericmenubuttons.TransferCategory;
import gui.locations.genericmenubuttons.TransferColour;
import gui.locations.genericmenubuttons.TransferMaterial;
import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class GenericLocationMenu extends AbstractAction {

    private JInternalFrame locationMenuFrame;
    private JDesktopPane mainDesktop;
    private String label;
    private LaundryTracker laundryTracker;
    private String currentLocation;
    private String targetLocation;

    private LaundryLocation mainLocation;
    private LaundryLocation transferLocation;


    public GenericLocationMenu(LaundryTracker laundryTracker, String name,
                               String current, String target) {
        super(name);
        this.laundryTracker = laundryTracker;
        this.mainDesktop = this.laundryTracker.getDesktop();
        this.label = name;
        this.currentLocation = current;
        this.targetLocation = target;
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        this.mainLocation = laundryTracker.getLocation(currentLocation);
        this.transferLocation = laundryTracker.getLocation(targetLocation);
        try {
            actionPerformed();
        } catch (NoClothingException noClothingException) {
            if (mainLocation.getUsername().contains("Basket")) {
                JOptionPane.showMessageDialog(mainDesktop,
                        "Currently no clothing in " + mainLocation.getUsername()
                        + "\nEither transfer dirty clothing from closet, or no clothing to be cleaned.",
                        "Inane warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainDesktop,
                        "Currently no clothing in " + mainLocation.getUsername()
                                + "\nYour clothes are all clean!",
                        "Inane warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void actionPerformed() throws NoClothingException {
        if (mainLocation.getMyWardrobe().isEmpty()) {
            throw new NoClothingException();
        }
        locationMenuFrame = new JInternalFrame(label, false, true, false, false);
        locationMenuFrame.setLayout(new BorderLayout());
        addLocationButtonPanel();


        locationMenuFrame.pack();
        locationMenuFrame.setVisible(true);
        locationMenuFrame.setSize(mainDesktop.getWidth() / 4, mainDesktop.getHeight() / 4);
        locationMenuFrame.setLocation((mainDesktop.getWidth() - locationMenuFrame.getWidth()) / 2,
                (mainDesktop.getHeight() - locationMenuFrame.getHeight()) / 2);
        mainDesktop.add(locationMenuFrame);
        locationMenuFrame.toFront();

    }

    // MODIFIES: this
    // EFFECTS: creates buttons that process location menu actions when pressed
    public void addLocationButtonPanel() {
        JPanel locationButtons = new JPanel();
        locationButtons.setLayout(new GridLayout(4, 1));
        locationButtons.add(new JButton(new TransferAll(mainLocation, transferLocation)));
        locationButtons.add(new JButton(new TransferCategory(mainDesktop, mainLocation, transferLocation)));
        locationButtons.add(new JButton(new TransferColour(mainDesktop, mainLocation, transferLocation)));
        locationButtons.add(new JButton(new TransferMaterial(mainDesktop, mainLocation, transferLocation)));
        locationMenuFrame.add(locationButtons, BorderLayout.CENTER);
    }



}
