package gui.locations.genericmenubuttons;

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

public class TransferSpecific extends AbstractAction implements ListSelectionListener {

    private JDesktopPane desktop;
    private LaundryLocation currentLocation;
    private LaundryLocation targetLocation;
    private JPanel transferPanel;
    private JInternalFrame transferFrame;

    private JList transferableList;
    private DefaultListModel listModel;
    private static final String transferString = "Transfer";
    private JButton transferButton;

    private String label;

    public TransferSpecific(JDesktopPane mainDesktop, LaundryLocation current, LaundryLocation target, String label) {
        super("Transfer by " + label);
        this.label = label;
        this.desktop = mainDesktop;
        this.currentLocation = current;
        this.targetLocation = target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        transferFrame = new JInternalFrame("Transfer by " + label, false, true, false, false);
        transferPanel = new JPanel(new BorderLayout());

        makeGraphics();

        transferFrame.pack();
        transferFrame.setContentPane(transferPanel);
        transferFrame.setSize(desktop.getWidth() / 4, desktop.getHeight() / 3);
        transferFrame.setLocation(desktop.getWidth() - transferFrame.getWidth(),
                0);
        transferFrame.setVisible(true);

        desktop.add(transferFrame);
        transferFrame.toFront();

    }

    // MODIFIES: this
    // EFFECTS: creates the list of transferables and puts it into a scroll pane + adds transfer button and scroll
    //          pane to transfer panel
    private void makeGraphics() {
        listModel = getListTransferable();
        transferableList = new JList(listModel);
        transferableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transferableList.setSelectedIndex(0);
        transferableList.addListSelectionListener(this);
        transferableList.setVisibleRowCount(4);

        JScrollPane listScrollPane = new JScrollPane(transferableList);

        transferButton = new JButton(transferString);
        transferButton.setActionCommand(transferString);
        transferButton.addActionListener(new TransferListener());

        transferPanel.add(listScrollPane, BorderLayout.CENTER);
        transferPanel.add(transferButton, BorderLayout.PAGE_END);
        transferPanel.setVisible(true);
    }

    // EFFECTS: adds list of transfer specific objects to list model and returns it
    private DefaultListModel getListTransferable() {
        listModel = new DefaultListModel();
        if (label.equalsIgnoreCase("category")) {
            for (String category : currentLocation.getMyWardrobe().getCategoryNames()) {
                listModel.addElement(category);
            }
        } else if (label.equalsIgnoreCase("colour")) {
            for (String colour : currentLocation.getMyWardrobe().getAllColoursAdded()) {
                listModel.addElement(colour);
            }
        } else {
            for (String material : currentLocation.getMyWardrobe().getAllMaterialsAdded()) {
                listModel.addElement(material);
            }
        }

        return listModel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (transferableList.getSelectedIndex() == -1) {
                transferButton.setEnabled(false);

            } else {
                transferButton.setEnabled(true);
            }
        }
    }

    private class TransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("transferSound.wav");
            String transferred = String.valueOf(listModel.get(transferableList.getSelectedIndex()));
            if (label.equalsIgnoreCase("category")) {
                currentLocation.transferClothingByType(transferred, targetLocation);
            } else if (label.equalsIgnoreCase("colour")) {
                currentLocation.transferClothingByColour(targetLocation, transferred);
            } else {
                currentLocation.transferClothingByMaterial(targetLocation, transferred);
            }

            if (label.equalsIgnoreCase("category")) {
                if (transferred.endsWith("s") || transferred.endsWith("wear")) {
                    JOptionPane.showMessageDialog(null,
                            "All " + transferred + " transferred to " + targetLocation.getUsername() + ".");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "All " + transferred + "s transferred to " + targetLocation.getUsername() + ".");
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "All " + transferred + " clothing transferred to " + targetLocation.getUsername() + ".");
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
