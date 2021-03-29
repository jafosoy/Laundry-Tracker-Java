package gui;

import gui.locations.BasketMenu;
import gui.locations.ClosetMenu;
import gui.locations.LaundryMenu;
import model.locations.Closet;
import model.locations.LaundryBasket;
import model.locations.LaundryLocation;
import model.locations.LaundryRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// using basis of gui structures from:
//       - AlarmSystem projects
//       - From Java component tutorials: Converter, ListDemo, TextDemo, LabelChanger
public class LaundryTracker extends JFrame {

    private static final String JSON_STORE_CLOSET = "./data/myCloset.json";
    private static final String JSON_STORE_BASKET = "./data/myBasket.json";
    private static final String JSON_STORE_LAUNDRY = "./data/myLaundry.json";

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private JsonReader jsonReaderLaundry;
    private JsonReader jsonReaderBasket;
    private JsonReader jsonReaderCloset;
    private JsonWriter jsonWriterCloset;
    private JsonWriter jsonWriterBasket;
    private JsonWriter jsonWriterLaundry;


    private LaundryLocation myCloset;
    private LaundryLocation myBasket;
    private LaundryLocation myLaundry;
    private String userName;      // sets default user name

    private JDesktopPane desktop;
    private JInternalFrame mainMenu;

    public LaundryTracker(String userName) {

        this.userName = userName;
        initializeFields();
        initializeGraphics();
        new GenerateButtonSound("turnOn.wav");

        JOptionPane.showMessageDialog(null,
                "What did the first sock say to the second sock in the dryer?"
                        + "\nI'll see you the next time around!");

        jsonReaderCloset = new JsonReader(JSON_STORE_CLOSET);
        jsonReaderBasket = new JsonReader(JSON_STORE_BASKET);
        jsonReaderLaundry = new JsonReader(JSON_STORE_LAUNDRY);
        jsonWriterCloset = new JsonWriter(JSON_STORE_CLOSET);
        jsonWriterBasket = new JsonWriter(JSON_STORE_BASKET);
        jsonWriterLaundry = new JsonWriter(JSON_STORE_LAUNDRY);

    }

    // MODIFIES: this
    // EFFECTS: instantiates fields (i.e. initializes user laundry locations and
    //          persistence classes)
    private void initializeFields() {
        myCloset = new Closet(userName);
        myBasket = new LaundryBasket(userName);
        myLaundry = new LaundryRoom(userName);

    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this LaundryTracker will operate, and sets up Main Menu
    private void initializeGraphics() {
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());

        initializeMainMenu();

        setContentPane(desktop);
        setTitle(userName + "'s Laundry Tracker");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        mainMenu.pack();
        mainMenu.setVisible(true);
        mainMenu.setSize(WIDTH / 4, HEIGHT / 3);
        desktop.add(mainMenu);

        addSettings();

        desktop.setBackground(new Color(85, 107, 47));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: draws a menu bar for editor settings
    private void addSettings() {
        JMenuBar menuBar = new JMenuBar();
        JMenu settings = new JMenu("Settings");
        addMenuItem(settings, new EditUsername(this));
        addMenuItem(settings, new ResetApp(this));
        menuBar.add(settings);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds an item (settings) with given handler (action) to the given menu
    private void addMenuItem(JMenu settings, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        settings.add(menuItem);
    }

    // MODIFIES: this
    // EFFECTS: draws an InternalFrame window where the Main Menu operates
    private void initializeMainMenu() {
        mainMenu = new JInternalFrame("Main Menu", false, false, false, false);
        mainMenu.setLayout(new BorderLayout());
        addMenuButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS:
    private void addMenuButtonPanel() {
        JPanel menuButtons = new JPanel();
        menuButtons.setLayout(new GridLayout(6, 1));
        menuButtons.add(new JButton(new ClosetMenu(this)));
        menuButtons.add(new JButton(new BasketMenu(this)));
        menuButtons.add(new JButton(new LaundryMenu(this)));
        menuButtons.add(new JButton(new ClothingDisplayMenu(this)));
        menuButtons.add(new JButton(new LoadFromFile()));
        menuButtons.add(new JButton(new SaveToFile()));
        mainMenu.add(menuButtons, BorderLayout.CENTER);
    }

    // EFFECTS: getter for desktop
    public JDesktopPane getDesktop() {
        return this.desktop;
    }

    // EFFECTS: getter for locations
    public LaundryLocation getLocation(String location) {
        if (location.equals("closet")) {
            return this.myCloset;
        } else if (location.equals("basket")) {
            return this.myBasket;
        } else {
            return this.myLaundry;
        }
    }


    // processes changing username based on user input
    private class EditUsername extends AbstractAction {
        private JTextField field;
        private LaundryTracker laundryTracker;

        public EditUsername(LaundryTracker laundryTracker) {
            super("Edit Username");
            this.laundryTracker = laundryTracker;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new EditFrame();
        }

        private class EditFrame extends AbstractAction {

            public EditFrame() {
                super("Edit Username");
                JInternalFrame editor = new JInternalFrame("Edit Username", false, true, false, false);
                editor.setLayout(new FlowLayout());
                JButton btn = new JButton("Change");
                btn.setActionCommand("myButton");
                btn.addActionListener(this);
                field = new JTextField(15);
                editor.add(field);
                editor.add(btn);

                editor.pack();
                editor.setSize(desktop.getWidth() / 4, desktop.getHeight() / 6);
                editor.setLocation((desktop.getWidth() - editor.getWidth()) / 2, 0);
                desktop.add(editor);
                editor.setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("myButton")) {
                    userName = field.getText();
                    laundryTracker.revalidate();
                }
            }
        }
    }

    // processes resetting Laundry Tracker app (empties laundry locations + changes username based on given input)
    private class ResetApp extends AbstractAction {

        LaundryTracker laundryTracker;

        public ResetApp(LaundryTracker laundryTracker) {
            super("Reset App");
            this.laundryTracker = laundryTracker;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(null,
                    "You are about to reset this application. Proceed?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options, options[0]);
            if (n == JOptionPane.YES_OPTION) {
                laundryTracker.dispose();
                new LaundryTracker("User");
            } else {
                JOptionPane.showMessageDialog(null, "Application not reset.");
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: places main application window at the centre on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    // used for switching focus when user clicks desktop
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            LaundryTracker.this.requestFocusInWindow();
        }
    }

    public class LoadFromFile extends AbstractAction {

        public LoadFromFile() {
            super("Load Laundry Tracker");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new GenerateButtonSound("beep-07.wav");
            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(null,
                    "You are about to load a previous version of your laundry tracker."
                            + "\nAny current data not saved on file will be deleted. Proceed?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options, options[0]);
            if (n == JOptionPane.YES_OPTION) {
                loadLocationsFromFile();

            } else {
                JOptionPane.showMessageDialog(null, "Data not loaded.");
            }
        }

        // MODIFIES: this
        // EFFECTS: loads laundry locations and user name from file
        private void loadLocationsFromFile() {
            loadClosetFromFile();
            loadBasketFromFile();
            loadLaundryFromFile();
            JOptionPane.showMessageDialog(null,
                    "Successfully loaded " + myCloset.getUsername() + " from " + JSON_STORE_CLOSET
                            + "\nSuccessfully loaded " + myBasket.getUsername() + " from " + JSON_STORE_BASKET
                            + "\nSuccessfully loaded " + myLaundry.getUsername() + " from " + JSON_STORE_LAUNDRY);
            int userLength = myCloset.getUsername().length();
            userLength -= 7;
            userName = myCloset.getUsername().substring(0, userLength);
        }

        // MODIFIES: this
        // EFFECTS: loads closet from file
        public void loadClosetFromFile() {
            try {
                myCloset = jsonReaderCloset.readLocation();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE_CLOSET,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // MODIFIES: this
        // EFFECTS: loads basket from file
        public void loadBasketFromFile() {
            try {
                myBasket = jsonReaderBasket.readLocation();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE_BASKET,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // MODIFIES: this
        // EFFECTS: loads laundry from file
        public void loadLaundryFromFile() {
            try {
                myLaundry = jsonReaderLaundry.readLocation();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE_LAUNDRY,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class SaveToFile extends AbstractAction {

        public SaveToFile() {
            super("Save Laundry Tracker");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new GenerateButtonSound("beep-07.wav");
            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(null,
                    "You are about to save a new version of your laundry tracker."
                            + "\nAny data from file not in this laundry tracker will be erased. Proceed?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options, options[0]);
            if (n == JOptionPane.YES_OPTION) {
                saveLocationsToFile();
            } else {
                JOptionPane.showMessageDialog(null, "Data not saved.");
            }
        }

        // EFFECTS: saves laundry locations to file
        private void saveLocationsToFile() {
            saveClosetToFile();
            saveBasketToFile();
            saveLaundryToFile();
            JOptionPane.showMessageDialog(null, "Successfully saved "
                    + myCloset.getUsername() + " to " + JSON_STORE_CLOSET
                    + "\nSuccessfully saved " + myBasket.getUsername() + " to " + JSON_STORE_BASKET
                    + "\nSuccessfully saved " + myLaundry.getUsername() + " to " + JSON_STORE_LAUNDRY);
        }

        // EFFECTS: saves closet to file
        public void saveClosetToFile() {
            try {
                jsonWriterCloset.open();
                jsonWriterCloset.write(myCloset);
                jsonWriterCloset.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE_CLOSET,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // EFFECTS: saves basket to file
        public void saveBasketToFile() {
            try {
                jsonWriterBasket.open();
                jsonWriterBasket.write(myBasket);
                jsonWriterBasket.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE_BASKET,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // EFFECTS: saves laundry to file
        public void saveLaundryToFile() {
            try {
                jsonWriterLaundry.open();
                jsonWriterLaundry.write(myLaundry);
                jsonWriterLaundry.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE_LAUNDRY,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    // starts the application
    public static void main(String[] args) {
        new LaundryTracker("User");
    }

}
