package gui.locations.closetspecificmenubuttons;

import gui.GenerateButtonSound;
import model.clothes.Clothing;
import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClothingAction extends AbstractAction {
    JDesktopPane desktopPane;
    private LaundryLocation userCloset;

    private JInternalFrame addFrame;
    private CategoryDropDown categoryPanel;
    private TextInputPanel brandPanel;
    private SizeDropDownPanel sizePanel;
    private TextInputPanel colourPanel;
    private TextInputPanel materialPanel;
    private JButton addButton;
    private TextInputPanel frequencyPanel;

    public AddClothingAction(JDesktopPane mainDesktop, LaundryLocation myCloset) {
        super("Add Clothing");
        this.userCloset = myCloset;
        this.desktopPane = mainDesktop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GenerateButtonSound("beep-07.wav");
        addFrame = new JInternalFrame("Add Clothing", false, true, false, false);

        initializePanels();

        addFrame.pack();
        addFrame.setSize(desktopPane.getWidth() / 4, desktopPane.getHeight() / 3);
        addFrame.setLocation((desktopPane.getWidth() - addFrame.getWidth()) / 2,
                0);
        addFrame.setVisible(true);

        desktopPane.add(addFrame);
        addFrame.toFront();
    }

    // MODIFIES: this
    // EFFECTS: draws the panels to be added to the Add Clothing frame
    private void initializePanels() {
        categoryPanel = new CategoryDropDown(userCloset);
        brandPanel = new TextInputPanel("Brand");
        sizePanel = new SizeDropDownPanel();
        colourPanel = new TextInputPanel("Colour");
        frequencyPanel = new TextInputPanel("Frequency of use");
        materialPanel = new TextInputPanel("Material");
        addButton = new JButton("Add");
        addButton.setActionCommand("Add");
        addButton.addActionListener(new AddClothingListener());

        addFrame.setLayout(new GridLayout(7, 1));
        addFrame.add(categoryPanel);
        addFrame.add(brandPanel);
        addFrame.add(sizePanel);
        addFrame.add(colourPanel);
        addFrame.add(frequencyPanel);
        addFrame.add(materialPanel);
        addFrame.add(addButton);
        addFrame.setVisible(true);
    }

    private class TextInputPanel extends JPanel implements ActionListener {

        private String label;
        private String inputString;
        private JTextField textField;

        public TextInputPanel(String label) {
            super(new GridLayout(1, 2));
            this.label = label;
            textField = new JTextField(20);
            textField.addActionListener(this);
            JLabel field = new JLabel(label);
            field.setHorizontalAlignment(JLabel.CENTER);
            this.add(field);
            this.add(textField);
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inputString = textField.getText();
            textField.selectAll();

        }

        // EFFECTS: getter for input string
        public String getInputString() {
            return this.inputString;
        }
    }

    private class SizeDropDownPanel extends JPanel implements ActionListener {

        private JComboBox sizeChooser;
        private String selectedSize;
        private JLabel label;

        public SizeDropDownPanel() {
            super(new GridLayout(1, 2));

            String[] sizes = new String[]{"---", "XXS", "XS", "S", "M", "L", "XL", "XXL"};
            sizeChooser = new JComboBox(sizes);
            sizeChooser.setSelectedIndex(0);
            sizeChooser.addActionListener(this);
            label = new JLabel("Size");
            label.setHorizontalAlignment(JLabel.CENTER);
            this.add(label);
            this.add(sizeChooser);
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            selectedSize = (String) cb.getSelectedItem();
        }

        // EFFECTS: getter for selected size of clothing
        public String getSelectedSize() {
            return this.selectedSize;
        }
    }

    private class AddClothingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = categoryPanel.getCategoryChosen();
            String brand = brandPanel.getInputString();
            String size = sizePanel.getSelectedSize();
            String colour = colourPanel.getInputString().toLowerCase();
            int frequency = 0;
            try {
                frequency = Integer.parseInt(frequencyPanel.getInputString());
            } catch (RuntimeException re) {
                JOptionPane.showMessageDialog(desktopPane,
                        "Non-integer value given to frequency",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
            String material = materialPanel.getInputString();
            Clothing toAdd = new Clothing(brand, size, colour, frequency, 0, material);
            userCloset.getMyWardrobe().addClothing(type, toAdd, true);
            JOptionPane.showMessageDialog(null,
                    "Clothing added to " + userCloset.getUsername() + "!");
        }
    }
}
