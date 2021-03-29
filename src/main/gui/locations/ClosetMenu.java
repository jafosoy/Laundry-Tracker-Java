package gui.locations;

import gui.GenerateButtonSound;
import gui.LaundryTracker;
import gui.locations.closetspecificmenubuttons.AddDaysAction;
import gui.locations.closetspecificmenubuttons.AlertsAction;
import gui.locations.closetspecificmenubuttons.RemoveClothingAction;
import gui.locations.closetspecificmenubuttons.SetLowStockAction;
import gui.locations.closetspecificmenubuttons.AddClothingAction;
import gui.locations.genericmenubuttons.TransferAll;
import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClosetMenu extends AbstractAction {

    private LaundryTracker laundryTracker;
    private JDesktopPane mainDesktop;
    private LaundryLocation myCloset;
    private LaundryLocation myBasket;


    private JInternalFrame closetMenu;

    public ClosetMenu(LaundryTracker laundryTracker) {
        super("Closet");
        this.laundryTracker = laundryTracker;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        new GenerateButtonSound("beep-07.wav");
        this.mainDesktop = laundryTracker.getDesktop();
        this.myCloset = laundryTracker.getLocation("closet");
        this.myBasket = laundryTracker.getLocation("basket");

        closetMenu = new JInternalFrame("Closet", false, true, false, false);
        closetMenu.setLayout(new BorderLayout());
        addLocationButtonPanel();

        closetMenu.pack();
        closetMenu.setVisible(true);
        closetMenu.setSize(mainDesktop.getWidth() / 4, mainDesktop.getHeight() / 4);
        closetMenu.setLocation((mainDesktop.getWidth() - closetMenu.getWidth()) / 2,
                (mainDesktop.getHeight() - closetMenu.getHeight()) / 2);
        mainDesktop.add(closetMenu);
        closetMenu.toFront();
    }

    // MODIFIES: this
    // EFFECTS: creates buttons that process the closet menu when pressed
    private void addLocationButtonPanel() {
        JPanel locationButtons = new JPanel();
        locationButtons.setLayout(new GridLayout(6, 1));
        locationButtons.add(new JButton(new AddClothingAction(mainDesktop, myCloset)));
        locationButtons.add(new JButton(new RemoveClothingAction(mainDesktop, myCloset)));
        locationButtons.add(new JButton(new TransferAll(myCloset, myBasket)));
        locationButtons.add(new JButton(new AlertsAction(mainDesktop, myCloset)));
        locationButtons.add(new JButton(new AddDaysAction(mainDesktop, myCloset)));
        locationButtons.add(new JButton(new SetLowStockAction(mainDesktop, myCloset)));
        closetMenu.add(locationButtons);
    }


}
