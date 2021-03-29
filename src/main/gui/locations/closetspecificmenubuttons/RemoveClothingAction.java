package gui.locations.closetspecificmenubuttons;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.LaundryLocation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class RemoveClothingAction extends AbstractAction {

    JDesktopPane desktopPane;
    private LaundryLocation userCloset;

    public RemoveClothingAction(JDesktopPane mainDesktop, LaundryLocation myCloset) {
        super("Remove Clothing");
        this.desktopPane = mainDesktop;
        this.userCloset = myCloset;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new UpdatableJFrame(desktopPane, userCloset);
    }


    private class UpdatableJFrame extends JInternalFrame implements ActionListener {


        private JComboBox categoryChooser;
        private String[] updatedCategories;
        private JLabel field;

        private JPanel categoryDropDown;

        private JDesktopPane currentDesktop;


        private String categoryChosen;
        private RemovableJFrame removableFrame;


        public UpdatableJFrame(JDesktopPane mainDesktop, LaundryLocation userCloset) {
            super("Remove Clothing", false, true, false, false);
            this.currentDesktop = mainDesktop;


            initializeDropDown(userCloset);

            this.pack();
            this.setContentPane(categoryDropDown);
            this.setSize(currentDesktop.getWidth() / 4, currentDesktop.getHeight() / 10);
            this.setLocation(currentDesktop.getWidth() - this.getWidth(),
                    0);
            this.setVisible(true);
            currentDesktop.add(this);
            this.toFront();

        }


        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            categoryChosen = (String) cb.getSelectedItem();
            removableFrame = new RemovableJFrame(currentDesktop, categoryChosen);
            removableFrame.updateButton();
        }


        // MODIFIES: this
        // EFFECTS: draws category dropdown list
        private void initializeDropDown(LaundryLocation userCloset) {
            categoryDropDown = new JPanel(new GridLayout(1, 2));

            List<String> categories = userCloset.getMyWardrobe().getCategoryNames();
            categories.add(0, "---");
            updatedCategories = categories.toArray(new String[0]);
            categoryChooser = new JComboBox(updatedCategories);
            categoryChooser.setSelectedIndex(0);
            categoryChooser.addActionListener(this);
            field = new JLabel("Category");
            field.setHorizontalAlignment(JLabel.CENTER);
            categoryDropDown.add(field);
            categoryDropDown.add(categoryChooser);
            categoryDropDown.setVisible(true);
        }

    }

    private class RemovableJFrame extends JInternalFrame implements ListSelectionListener {

        private JList removableList;
        private DefaultListModel listModel;
        private static final String removeString = "Remove";
        private JButton removeButton;

        private String categoryChosen;
        private JScrollPane listScrollPane;
        private JPanel removePanel;

        public RemovableJFrame(JDesktopPane currentDesktop, String categoryChosen) {
            super("List of removable " + categoryChosen, false, true, false, false);
            this.removePanel = new JPanel(new BorderLayout());
            this.categoryChosen = categoryChosen;
            initializeScrollGraphics();

            this.pack();
            this.setContentPane(removePanel);
            this.setSize(currentDesktop.getWidth() / 4, currentDesktop.getHeight() / 3);
            this.setLocation(currentDesktop.getWidth() - this.getWidth(),
                    currentDesktop.getHeight() / 10);
            this.setVisible(true);
            currentDesktop.add(this);
            this.toFront();
        }

        // MODIFIES: this
        // EFFECTS: prevents user from pressing button whenever category chosen has no value
        private void updateButton() {
            if (categoryChosen.equals("---") || listModel.isEmpty()) {
                removeButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: draws the list of clothing based on given clothing type into a scroll pane
        //          draws category dropdown panel, scroll pane, and remove button on to remove panel
        private void initializeScrollGraphics() {
            this.listModel = getListRemovable(categoryChosen);
            this.removableList = new JList(listModel.toArray());
            removableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            removableList.setSelectedIndex(0);
            removableList.addListSelectionListener(this);
            removableList.setVisibleRowCount(4);

            listScrollPane = new JScrollPane(removableList);

            removeButton = new JButton(removeString);
            removeButton.setActionCommand(removeString);
            removeButton.addActionListener(new RemoveListener());

            removePanel.add(listScrollPane, BorderLayout.CENTER);
            removePanel.add(removeButton, BorderLayout.PAGE_END);
            removePanel.setVisible(true);
        }

        // EFFECTS: given category, returns the list of removable clothing in closet
        private DefaultListModel getListRemovable(String category) {
            listModel = new DefaultListModel();
            if (!category.equals("---")) {
                ClothingCategory currCat = userCloset.getMyWardrobe().getCategory(category);
                for (Clothing clothing : userCloset.getMyWardrobe().getClothes(currCat)) {
                    listModel.addElement(clothing.getID() + ": " + clothing.getColour()
                            + " " + clothing.getBrand() + " " + categoryChosen);
                }
            }
            return listModel;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                if (removableList.getSelectedIndex() == -1) {
                    removeButton.setEnabled(false);

                } else {
                    removeButton.setEnabled(true);
                }
            }
        }

        private class RemoveListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = removableList.getSelectedIndex();
                    String toRemove = String.valueOf(listModel.get(index));
                    int idEnd = toRemove.indexOf(":");

                    int id = Integer.parseInt(toRemove.substring(0, idEnd));

                    String category = categoryChosen;
                    listModel.remove(index);
                    removableList.setSelectedIndex(index);
                    userCloset.getMyWardrobe().removeClothing(category, id);
                    playSound("trash.wav");
                    JOptionPane.showMessageDialog(null,
                            category + " has been removed from " + userCloset.getUsername());

                } catch (RuntimeException rne) {
                    JOptionPane.showMessageDialog(desktopPane,
                            "No clothing chosen!",
                            "Invalid removal",
                            JOptionPane.ERROR_MESSAGE);
                }


            }


            // EFFECTS: plays sound from file specific to when it is called
            public void playSound(String soundName) {
                try {
                    AudioInputStream audioInputStream;
                    audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
            }
        }
    }
}







