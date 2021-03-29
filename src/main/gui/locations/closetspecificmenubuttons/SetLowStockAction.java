package gui.locations.closetspecificmenubuttons;

import gui.GenerateButtonSound;
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

public class SetLowStockAction extends AbstractAction {

    JDesktopPane desktopPane;
    private LaundryLocation userCloset;

    public SetLowStockAction(JDesktopPane mainDesktop, LaundryLocation myCloset) {
        super("Set low stock to clothing");
        this.desktopPane = mainDesktop;
        this.userCloset = myCloset;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GenerateButtonSound("beep-07.wav");
        new ChooseCategoryFrame(desktopPane, userCloset);
    }

    private class ChooseCategoryFrame extends JInternalFrame implements ListSelectionListener {

        private JList categoryList;
        private DefaultListModel listModel;
        private static final String setString = "Set Low Stock";
        private JButton setButton;

        private JScrollPane listScrollPane;
        private JPanel setPanel;
        private JTextField lowStock;
        private LaundryLocation myCloset;


        public ChooseCategoryFrame(JDesktopPane desktopPane, LaundryLocation userCloset) {
            super("All Categories", false, true, false, false);

            this.myCloset = userCloset;
            this.setPanel = new JPanel(new BorderLayout());
            initializeScrollGraphics();

            this.pack();
            this.setContentPane(setPanel);
            this.setSize(desktopPane.getWidth() / 4, desktopPane.getHeight() / 3);
            this.setLocation(desktopPane.getWidth() - this.getWidth(),
                    desktopPane.getHeight() / 10);
            this.setVisible(true);
            desktopPane.add(this);
            this.toFront();
        }


        // MODIFIES: this
        // EFFECTS: draws the list of categories with the set low stock text box and button
        private void initializeScrollGraphics() {
            setListCategories();
            this.categoryList = new JList(listModel.toArray());
            categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            categoryList.setSelectedIndex(0);
            categoryList.addListSelectionListener(this);
            categoryList.setVisibleRowCount(4);

            listScrollPane = new JScrollPane(categoryList);

            setButton = new JButton(setString);
            setButton.setActionCommand(setString);
            SetListener setListener = new SetListener(setButton);
            setButton.setActionCommand(setString);
            setButton.addActionListener(setListener);
            setButton.setEnabled(false);

            lowStock = new JTextField(5);
            lowStock.addActionListener(setListener);
            lowStock.getDocument().addDocumentListener(setListener);

            JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
            bottomPanel.add(lowStock);
            bottomPanel.add(setButton);

            setPanel.add(listScrollPane, BorderLayout.CENTER);
            setPanel.add(bottomPanel, BorderLayout.PAGE_END);
            setPanel.setVisible(true);
        }

        // EFFECTS: given category chosen, returns the list of updatable clothing in closet
        private void setListCategories() {
            listModel = new DefaultListModel();
            for (String cc : userCloset.getMyWardrobe().getCategoryNames()) {
                listModel.addElement(cc);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                if (categoryList.getSelectedIndex() == -1) {
                    setButton.setEnabled(false);

                } else {
                    setButton.setEnabled(true);
                }
            }
        }

        private class SetListener implements ActionListener, DocumentListener {

            private boolean alreadyEnabled = false;
            private JButton button;

            public SetListener(JButton updateButton) {
                this.button = updateButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int stock = Integer.parseInt(lowStock.getText());
                    int index = categoryList.getSelectedIndex();
                    String toUpdate = String.valueOf(listModel.get(index));

                    userCloset.getMyWardrobe().getCategory(toUpdate).updateLowStock(stock);
                    JOptionPane.showMessageDialog(null,
                            toUpdate + " category has been updated in " + userCloset.getUsername());
                } catch (RuntimeException rne) {
                    JOptionPane.showMessageDialog(desktopPane,
                            "No categories updated!",
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

