package gui.locations.closetspecificmenubuttons;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.LaundryLocation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddDaysAction extends AbstractAction {

    JDesktopPane desktopPane;
    private LaundryLocation userCloset;

    public AddDaysAction(JDesktopPane mainDesktop, LaundryLocation myCloset) {
        super("Add days worn");
        this.desktopPane = mainDesktop;
        this.userCloset = myCloset;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ChooseCategoryFrame(desktopPane, userCloset);
    }

    private class ChooseCategoryFrame extends JInternalFrame implements ActionListener {

        private JComboBox categoryChooser;
        private String[] updatedCategories;
        private JLabel field;

        private JPanel categoryDropDown;

        private JDesktopPane currentDesktop;


        private String categoryChosen;
        private ClothingJFrame clothingFrame;

        public ChooseCategoryFrame(JDesktopPane desktopPane, LaundryLocation userCloset) {
            super("Add Days worn to Clothing", false, true, false, false);
            this.currentDesktop = desktopPane;

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
            clothingFrame = new ClothingJFrame(currentDesktop, categoryChosen);
            clothingFrame.updateButton();
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

        private class ClothingJFrame extends JInternalFrame implements ListSelectionListener {

            private JList updatableList;
            private DefaultListModel listModel;
            private static final String updateString = "Update";
            private JButton updateButton;

            private String categoryChosen;
            private JScrollPane listScrollPane;
            private JPanel updatePanel;
            private JTextField daysWorn;

            public ClothingJFrame(JDesktopPane currentDesktop, String categoryChosen) {
                super("List of clothing", false, true, false, false);
                this.updatePanel = new JPanel(new BorderLayout());
                this.categoryChosen = categoryChosen;
                initializeScrollGraphics();

                this.pack();
                this.setContentPane(updatePanel);
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
                    updateButton.setEnabled(false);
                } else {
                    updateButton.setEnabled(true);
                }
            }

            // MODIFIES: this
            // EFFECTS: draws the list of clothing based on given clothing type into a scroll pane
            //          draws category dropdown panel, scroll pane, and remove button on to remove panel
            private void initializeScrollGraphics() {
                this.listModel = getListUpdatable(categoryChosen);
                this.updatableList = new JList(listModel.toArray());
                updatableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                updatableList.setSelectedIndex(0);
                updatableList.addListSelectionListener(this);
                updatableList.setVisibleRowCount(4);

                listScrollPane = new JScrollPane(updatableList);

                updateButton = new JButton(updateString);
                updateButton.setActionCommand(updateString);
                UpdateListener updateListener = new UpdateListener(updateButton);
                updateButton.setActionCommand(updateString);
                updateButton.addActionListener(updateListener);
                updateButton.setEnabled(false);

                daysWorn = new JTextField(5);
                daysWorn.addActionListener(updateListener);
                daysWorn.getDocument().addDocumentListener(updateListener);

                JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
                bottomPanel.add(daysWorn);
                bottomPanel.add(updateButton);

                updatePanel.add(listScrollPane, BorderLayout.CENTER);
                updatePanel.add(bottomPanel, BorderLayout.PAGE_END);
                updatePanel.setVisible(true);
            }

            // EFFECTS: given category chosen, returns the list of updatable clothing in closet
            private DefaultListModel getListUpdatable(String categoryChosen) {
                listModel = new DefaultListModel();
                if (!categoryChosen.equals("---")) {
                    ClothingCategory currCat = userCloset.getMyWardrobe().getCategory(categoryChosen);
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
                    if (updatableList.getSelectedIndex() == -1) {
                        updateButton.setEnabled(false);

                    } else {
                        updateButton.setEnabled(true);
                    }
                }
            }

            private class UpdateListener implements ActionListener, DocumentListener {

                private boolean alreadyEnabled = false;
                private JButton button;

                public UpdateListener(JButton updateButton) {
                    this.button = updateButton;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int days = Integer.parseInt(daysWorn.getText());
                        int index = updatableList.getSelectedIndex();
                        String toUpdate = String.valueOf(listModel.get(index));
                        int idEnd = toUpdate.indexOf(":");

                        int id = Integer.parseInt(toUpdate.substring(0, idEnd));

                        String category = categoryChosen;
                        userCloset.getMyWardrobe().getMyClothing(category, id).addDays(days);
                        JOptionPane.showMessageDialog(null,
                                category + " has been updated in " + userCloset.getUsername());
                    } catch (RuntimeException rne) {
                        JOptionPane.showMessageDialog(desktopPane,
                                "No clothing updated!",
                                "Invalid update",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    enableButton();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    handleEmptyTextField(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (!alreadyEnabled) {
                        enableButton();
                    }
                }

                private void enableButton() {
                    if (!alreadyEnabled) {
                        button.setEnabled(true);
                    }
                }

                private void handleEmptyTextField(DocumentEvent e) {
                    if (e.getDocument().getLength() <= 0) {
                        button.setEnabled(false);
                        alreadyEnabled = false;
                    }
                }
            }
        }
    }
}
