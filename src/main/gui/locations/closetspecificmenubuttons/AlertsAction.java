package gui.locations.closetspecificmenubuttons;

import gui.GenerateButtonSound;
import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;
import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AlertsAction extends AbstractAction {

    private DefaultListModel listModel;
    private final JDesktopPane desktopPane;
    private final LaundryLocation closet;

    public AlertsAction(JDesktopPane mainDesktop, LaundryLocation myCloset) {
        super("Clothing alerts");
        this.desktopPane = mainDesktop;
        this.closet = myCloset;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GenerateButtonSound("beep-07.wav");
        Wardrobe closetWardrobe = closet.getMyWardrobe();
        for (String cc : closetWardrobe.getCategoryNames()) {
            ClothingCategory currCategory = closetWardrobe.getCategory(cc);
            if (closetWardrobe.isLow(currCategory)) {
                JOptionPane.showMessageDialog(null,
                        "LOW ON " + cc.toUpperCase() + "!");
            }
            if (hasDirtyClothing(closetWardrobe.getClothes(currCategory))) {
                new DisplayClothes(closetWardrobe.getClothes(currCategory), cc);
            }

        }
    }

    // EFFECTS: returns true if given list of clothes has dirty clothing, false otherwise
    private boolean hasDirtyClothing(List<Clothing> clothes) {
        Boolean hasDirtyClothing = false;
        for (Clothing c : clothes) {
            if (c.isDirty()) {
                hasDirtyClothing = true;
            }
        }
        return hasDirtyClothing;
    }

    private class DisplayClothes extends JInternalFrame {

        private JList dirtyClothes;
        private DefaultListModel listModel;
        private String currCategory;
        private JScrollPane scrollPanel;
        private List<Clothing> clothingList;

        public DisplayClothes(List<Clothing> clothes, String cc) {
            super(cc + " to clean", false, true, false, false);
            this.currCategory = cc;
            this.clothingList = clothes;
            initializeScrollGraphics();

            this.pack();
            this.setContentPane(scrollPanel);
            this.setSize(desktopPane.getWidth() / 4, desktopPane.getHeight() / 3);
            this.setLocation(0, desktopPane.getHeight() - this.getHeight());
            this.setVisible(true);
            desktopPane.add(this);
            this.toFront();
        }

        // MODIFIES: this
        // EFFECTS: draws the list of dirty clothing into a scroll pane
        private void initializeScrollGraphics() {
            this.listModel = getDirtyClothing();
            this.dirtyClothes = new JList(listModel.toArray());
            this.dirtyClothes.setVisibleRowCount(4);

            this.scrollPanel = new JScrollPane(dirtyClothes);
            this.scrollPanel.setVisible(true);
        }

        private DefaultListModel getDirtyClothing() {
            DefaultListModel list = new DefaultListModel();
            for (Clothing c : this.clothingList) {
                if (c.isDirty()) {
                    list.addElement(c.getID() + ": " + c.getColour()
                            + " " + c.getBrand() + " " + this.currCategory);
                }
            }
            return list;
        }
    }
}

