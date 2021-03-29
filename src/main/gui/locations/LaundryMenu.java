package gui.locations;

import gui.LaundryTracker;
import model.locations.LaundryLocation;

import javax.swing.*;

public class LaundryMenu extends GenericLocationMenu {

    public LaundryMenu(LaundryTracker laundryTracker) {
        super(laundryTracker, "Laundry Room",
                "laundry",
                "closet");

    }


}
