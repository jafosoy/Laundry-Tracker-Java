package gui;

import gui.locations.genericmenubuttons.TransferAll;
import gui.locations.genericmenubuttons.TransferCategory;
import gui.locations.genericmenubuttons.TransferColour;
import gui.locations.genericmenubuttons.TransferMaterial;
import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClothingDisplayMenu extends AbstractAction {

    private LaundryTracker laundryTracker;
    private JInternalFrame displayMenuFrame;
    private JDesktopPane mainDesktop;

    public ClothingDisplayMenu(LaundryTracker laundryTracker) {
        super("Display Clothes");
        this.laundryTracker = laundryTracker;
        this.mainDesktop = this.laundryTracker.getDesktop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GenerateButtonSound("beep-07.wav");
        displayMenuFrame = new JInternalFrame("Display Clothes", false, true, false, false);
        displayMenuFrame.setLayout(new BorderLayout());
        addDisplayButtonPanel();

        displayMenuFrame.pack();
        displayMenuFrame.setVisible(true);
        displayMenuFrame.setSize(mainDesktop.getWidth() / 4, mainDesktop.getHeight() / 4);
        displayMenuFrame.setLocation((mainDesktop.getWidth() - displayMenuFrame.getWidth()) / 2,
                (mainDesktop.getHeight() - displayMenuFrame.getHeight()) / 2);
        mainDesktop.add(displayMenuFrame);
        displayMenuFrame.toFront();
    }

    // MODIFIES: this
    // EFFECTS: creates buttons that process what display of clothes the user views when pressed
    public void addDisplayButtonPanel() {
        JPanel displayButtons = new JPanel();
        displayButtons.setLayout(new GridLayout(4, 1));
        displayButtons.add(new JButton(new ViewClothesAction(laundryTracker, "All")));
        displayButtons.add(new JButton(new ViewClothesAction(laundryTracker, "Closet")));
        displayButtons.add(new JButton(new ViewClothesAction(laundryTracker, "Basket")));
        displayButtons.add(new JButton(new ViewClothesAction(laundryTracker, "Laundry")));
        displayMenuFrame.add(displayButtons, BorderLayout.CENTER);
    }

    private class ViewClothesAction extends AbstractAction {
        private String toView;
        private LaundryTracker laundryTracker;
        private JDesktopPane mainDesktop;

        public ViewClothesAction(LaundryTracker laundryTracker, String toView) {
            super("View Clothes in " + toView);
            this.laundryTracker = laundryTracker;
            this.toView = toView;
            this.mainDesktop = this.laundryTracker.getDesktop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            displayClothes(toView);
        }

        // MODIFIES: this
        // EFFECTS: draws a display of all the clothes based on given display to view
        private void displayClothes(String toView) {
            JInternalFrame displayScreen = new JInternalFrame(toView + " Clothes", false, true, false, false);
            JPanel displayPanel = makeListGraphics(toView);

            displayScreen.pack();
            displayScreen.setContentPane(displayPanel);
            displayScreen.setSize(mainDesktop.getWidth() / 4, mainDesktop.getHeight() / 3);
            displayScreen.setLocation(mainDesktop.getWidth() - displayScreen.getWidth(),
                    mainDesktop.getHeight() - displayScreen.getHeight());
            displayScreen.setVisible(true);

            mainDesktop.add(displayScreen);
            displayScreen.toFront();
        }

        // MODIFIES: this
        // EFFECTS: draws a JPanel of the display of clothing given what to view
        private JPanel makeListGraphics(String toView) {
            JPanel display = new JPanel(new BorderLayout());

            DefaultListModel listModel = getListDisplayable(toView);
            JList displayableList = new JList(listModel);
            displayableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            displayableList.setSelectedIndex(0);
            displayableList.setVisibleRowCount(4);

            JScrollPane listScrollPane = new JScrollPane(displayableList);
            display.add(listScrollPane, BorderLayout.CENTER);
            display.setVisible(true);
            return display;
        }


        // EFFECTS: produces a list of all clothes given which location to view clothes in
        private DefaultListModel getListDisplayable(String toView) {
            DefaultListModel toDisplay = new DefaultListModel();
            if (toView.equals("Closet")) {
                addToList(toDisplay, laundryTracker.getLocation("closet").getMyWardrobe());
            } else if (toView.equals("Basket")) {
                addToList(toDisplay, laundryTracker.getLocation("basket").getMyWardrobe());
            } else if (toView.equals("Laundry")) {
                addToList(toDisplay, laundryTracker.getLocation("laundry").getMyWardrobe());
            } else {
                addToList(toDisplay, laundryTracker.getLocation("closet").getMyWardrobe());
                addToList(toDisplay, laundryTracker.getLocation("basket").getMyWardrobe());
                addToList(toDisplay, laundryTracker.getLocation("laundry").getMyWardrobe());
            }
            return toDisplay;
        }

        // EFFECTS: given wardrobe, adds a string representation of all clothes to given list
        private void addToList(DefaultListModel toDisplay, Wardrobe currWardrobe) {
            for (String category : currWardrobe.getCategoryNames()) {
                ClothingCategory cc = currWardrobe.getCategory(category);
                for (Clothing c : currWardrobe.getClothes(cc)) {
                    String currClothing = "ID " + c.getID() + ": " + c.getColour()
                            + " " + c.getBrand() + " " + category;
                    toDisplay.addElement(currClothing);
                }
            }
        }


    }
}
