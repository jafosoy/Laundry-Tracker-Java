package ui;

// Using basis of user interface on the Teller Application


import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;
import model.locations.Closet;
import model.locations.LaundryBasket;
import model.locations.LaundryLocation;
import model.clothes.Clothing;
import model.locations.LaundryRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class LaundryTrackerApp {
    private static final String JSON_STORE_CLOSET = "./data/myCloset.json";
    private static final String JSON_STORE_BASKET = "./data/myBasket.json";
    private static final String JSON_STORE_LAUNDRY = "./data/myLaundry.json";
    private JsonWriter jsonWriterCloset;
    private JsonReader jsonReaderCloset;
    private JsonWriter jsonWriterBasket;
    private JsonReader jsonReaderBasket;
    private JsonWriter jsonWriterLaundry;
    private JsonReader jsonReaderLaundry;

    private LaundryLocation myCloset;
    private LaundryLocation myBasket;
    private LaundryLocation myLaundry;
    private String userName = "User";      // sets default user name
    private final Scanner input;

    // EFFECTS: runs a Laundry Tracker Application
    public LaundryTrackerApp() {
        myCloset = new Closet(userName);
        myBasket = new LaundryBasket(userName);
        myLaundry = new LaundryRoom(userName);
        this.input = new Scanner(System.in);

        jsonWriterCloset = new JsonWriter(JSON_STORE_CLOSET);
        jsonReaderCloset = new JsonReader(JSON_STORE_CLOSET);
        jsonWriterBasket = new JsonWriter(JSON_STORE_BASKET);
        jsonReaderBasket = new JsonReader(JSON_STORE_BASKET);
        jsonWriterLaundry = new JsonWriter(JSON_STORE_LAUNDRY);
        jsonReaderLaundry = new JsonReader(JSON_STORE_LAUNDRY);

        processMainMenu();

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void processMainMenu() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("yes")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }
        System.out.println("\nQ: What did the first sock say to the second sock in the dryer?");
        System.out.println("\nA: I'll see you the next time around! Have a good day master.");
    }

    // MODIFIES: this
    // EFFECTS: processes myCloset menu
    private void runMenuCloset() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenuMyCloset();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("sw")) {
                keepGoing = false;
            } else {
                processCommandMyCloset(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes laundryBasket menu
    private void runMenuBasket() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenuBasket();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("sw")) {
                keepGoing = false;
            } else {
                processCommandLaundryBasket(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes clothing menu
    private void runClothingMenu() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayClothingMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("sw")) {
                keepGoing = false;
            } else {
                processCommandAllClothing(command);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: processes laundryRoom menu
    private void runMenuLaundry() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenuLaundry();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("sw")) {
                keepGoing = false;
            } else {
                processCommandLaundry(command);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command for the main menu
    private void processCommand(String command) {
        if (command.equals("mc")) {
            runMenuCloset();
        } else if (command.equals("lb")) {
            runMenuBasket();
        } else if (command.equals("lr")) {
            runMenuLaundry();
        } else if (command.equals("vc")) {
            runClothingMenu();
        } else if (command.equals("cn")) {
            changeUsername();
        } else if (command.equals("rl")) {
            resetLaundryTracker();
        } else if (command.equals("s")) {
            saveLocationsToFile();
        } else if (command.equals("l")) {
            loadLocationsFromFile();
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the closet menu
    private void processCommandMyCloset(String command) {
        if (command.equals("ac")) {
            addClothing();
        } else if (command.equals("rc")) {
            removeClothing();
        } else if (command.equals("tc")) {
            transferAllItems(myCloset);
        } else if (command.equals("ca")) {
            displayAlerts();
        } else if (command.equals("ad")) {
            addDaysToClothing();
        } else if (command.equals("al")) {
            adjustLowStock();
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the laundry basket menu
    private void processCommandLaundryBasket(String command) {
        if (command.equals("tra")) {
            transferAllItems(myBasket);
        } else if (command.equals("trt")) {
            transferByType(myBasket);
        } else if (command.equals("trc")) {
            transferByColour(myBasket);
        } else if (command.equals("trm")) {
            transferByMaterial(myBasket);
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the laundry room menu
    private void processCommandLaundry(String command) {
        if (command.equals("tra")) {
            transferAllItems(myLaundry);
        } else if (command.equals("trt")) {
            transferByType(myLaundry);
        } else if (command.equals("trc")) {
            transferByColour(myLaundry);
        } else if (command.equals("trm")) {
            transferByMaterial(myLaundry);
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the clothing display menu
    private void processCommandAllClothing(String command) {
        if (command.equals("mc")) {
            displayClosetClothes();
        } else if (command.equals("lb")) {
            displayLaundryBasketClothes();
        } else if (command.equals("lr")) {
            displayLaundryRoomClothes();
        } else if (command.equals("al")) {
            displayAllClothing();
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // EFFECTS: displays the app main menu
    private void displayMainMenu() {
        System.out.println("\n" + userName + "'s Laundry Tracker App");
        System.out.println("\nSelect From:");
        System.out.println("\tmc  -> check out your closet");
        System.out.println("\tlb  -> is your laundry basket full?");
        System.out.println("\tlr  -> transfer your clothes from your laundry room");
        System.out.println("\tvc  -> view your clothing");
        System.out.println("\tcn  -> change your user name");
        System.out.println("\trl  -> reset your laundry tracker");
        System.out.println("\ts   -> save laundry tracker to file");
        System.out.println("\tl   -> load laundry tracker from file");
        System.out.println("\tyes -> are you ready to leave?");
    }

    // EFFECTS: displays the closet menu
    private void displayMenuMyCloset() {
        System.out.println("\n" + myCloset.getUsername());
        System.out.println("\nSelect From:");
        System.out.println("\tac -> add clothing to your closet");
        System.out.println("\trc -> remove clothing from your closet");
        System.out.println("\ttc -> transfer your dirty clothes");
        System.out.println("\tca -> find your alerts");
        System.out.println("\tad -> add days worn to clothing");
        System.out.println("\tal –> adjust when clothing is low in stock");
        System.out.println("\tsw -> are you ready to leave?");
    }

    // EFFECTS: displays the clothing menu
    private void displayClothingMenu() {
        System.out.println("\nSelect From:");
        System.out.println("\tmc -> display your clothing in your closet");
        System.out.println("\tlb -> display your clothing in your laundry basket");
        System.out.println("\tlr -> display your clothing in the laundry room");
        System.out.println("\tal -> display all of your clothing");
        System.out.println("\tsw -> are you ready to leave?");
    }

    // EFFECTS: displays the laundry basket menu
    private void displayMenuBasket() {
        System.out.println("\n" + myBasket.getUsername());
        System.out.println("\nSelect From:");
        System.out.println("\ttra -> transfer all your clothes to the laundry room");
        System.out.println("\ttrt -> transfer all your clothing by type to the laundry room");
        System.out.println("\ttrc -> transfer all your clothing by colours to the laundry room");
        System.out.println("\ttrm -> transfer all your clothing to the laundry room");
        System.out.println("\tsw  -> are you ready to leave?");
    }

    // EFFECTS: displays the laundry room menu
    private void displayMenuLaundry() {
        System.out.println("\n" + myLaundry.getUsername());
        System.out.println("\nSelect From:");
        System.out.println("\ttra -> transfer all your clothes to the laundry room");
        System.out.println("\ttrt -> transfer all your clothing by type to the laundry room");
        System.out.println("\ttrc -> transfer all your clothing by colours to the laundry room");
        System.out.println("\ttrm -> transfer all your clothing to the laundry room");
        System.out.println("\tsw  -> are you ready to leave?");
    }

    // EFFECTS: displays the categories of clothing
    private void displayCategoryOptions() {
        System.out.println("\nSelect from the clothing options:");
        System.out.println("\tds -> Dress or Skirt");
        System.out.println("\tfm -> Formalwear (e.g.. Suit");
        System.out.println("\tjc -> Jacket or Coat (Outerwear)");
        System.out.println("\tjn -> Jeans");
        System.out.println("\tpn -> Pants");
        System.out.println("\tst -> Shirts or Tanks");
        System.out.println("\tsh -> Shorts");
        System.out.println("\tsl -> Sleepwear");
        System.out.println("\tso -> Socks");
        System.out.println("\tsp -> Sportswear");
        System.out.println("\tun -> Underwear");
    }

    // EFFECTS: processes user command for assigning the clothing category
    public String assignClothingCategory() {
        String category = "";
        boolean keepGoing = true;
        String command;
        List<String> acceptable = Arrays.asList("ds", "fm", "jc", "jn", "pn",
                "st", "sh", "sl", "so", "sp", "un");
        while (keepGoing) {
            displayCategoryOptions();
            command = input.next();
            command = command.toLowerCase();
            if (acceptable.contains(command)) {
                keepGoing = false;
                int position = acceptable.indexOf(command);
                category = myCloset.getMyWardrobe().getCategoryNames().get(position);
            } else {
                System.out.println("Selection unavailable or invalid...");
            }
        }
        return category;
    }

    // MODIFIES: this
    // EFFECTS: adds a new piece of clothing categorized by it's type and properties
    private void addClothing() {
        System.out.println("Describe your piece of clothing:");
        String category = assignClothingCategory();

        System.out.println("What brand is it? Say NONE for no brand.");
        input.nextLine();
        String brand = input.nextLine();
        System.out.println("What size? Please answer in XS, S, M, L, XL, etc.");
        String size = input.next().toLowerCase();
        System.out.println("What colour? (e.g. black, white, green, etc.)");
        String colour = input.next().toLowerCase();
        System.out.println("Frequency of use:");
        int frequency = parseInt(input.next());
        System.out.println("Material:");
        String material = input.next().toLowerCase();
        Clothing newAdd = new Clothing(brand, size, colour, frequency, 0, material);
        myCloset.getMyWardrobe().addClothing(category, newAdd, true);
        System.out.println("\nSuccessfully added item to your closet!");
        System.out.println("\nTaking you back to closet menu...");
    }


    // MODIFIES: this
    // EFFECTS: removes clothing based on user given type and clothing id
    private void removeClothing() {
        System.out.println("Describe your piece of clothing:");
        String category = assignClothingCategory();
        displayClothingInCategory(category, myCloset);
        if (myCloset.getMyWardrobe().getClothes(myCloset.getMyWardrobe().getCategory(category)).isEmpty()) {
            System.out.println("No " + category + " currently in closet");
            return;
        }
        System.out.println("Input id of clothing to be removed:");
        int id = Integer.parseInt(input.next());
        myCloset.getMyWardrobe().removeClothing(category, id);
        System.out.println("\nSuccessfully removed clothing from your closet.");
        System.out.println("\nTaking you back to Closet Menu...");
    }

    // MODIFIES: this and clothing
    // EFFECTS: updates the days worn of clothing by user
    private void addDaysToClothing() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Describe your piece of clothing:");
            String category = assignClothingCategory();
            displayClothingInCategory(category, myCloset);
            System.out.println("Input id of clothing to be updated");
            int id = Integer.parseInt(input.next());
            System.out.println("How many days worn do you want to add to clothing?");
            int days = Integer.parseInt(input.next());
            myCloset.getMyWardrobe().getMyClothing(category, id).addDays(days);
            System.out.println("Successfully updated clothing. "
                    + "\nDo you want to keep going? type n if not, y to keep going");
            String ans = input.next().toLowerCase();
            if (ans.equals("n")) {
                keepGoing = false;
            }
        }
    }

    // REQUIRES: input category name must correspond to a category in wardrobe
    // EFFECTS: displays all of the clothing currently in the location's wardrobe of the given category
    private void displayClothingInCategory(String category, LaundryLocation location) {
        ClothingCategory targetCategory = location.getMyWardrobe().getCategory(category);
        List<Clothing> clothesOfCategory = location.getMyWardrobe().getClothes(targetCategory);
        for (Clothing cc: clothesOfCategory) {
            System.out.println("ID " + cc.getID() + ": " + cc.getColour() + " " + cc.getBrand() + " " + category);
            System.out.println("Days worn: " + cc.getDays());
        }

    }

    // EFFECTS: prints out all clothing in all 3 locations based on colour + brand + types
    private void displayAllClothing() {
        System.out.println("All items:");
        displayClosetClothes();
        displayLaundryBasketClothes();
        displayLaundryRoomClothes();
    }

    // EFFECTS: prints out clothing in laundry room based on colour + brand + type
    private void displayLaundryRoomClothes() {
        System.out.println("Clothing at your laundry room: ");
        Wardrobe laundryWardrobe = myLaundry.getMyWardrobe();
        for (String categoryName: laundryWardrobe.getCategoryNames()) {
            displayClothingInCategory(categoryName, myLaundry);
        }
    }

    // EFFECTS: prints out clothing in laundry basket based on colour + brand + type
    private void displayLaundryBasketClothes() {
        System.out.println("Clothing at your laundry basket: ");
        Wardrobe laundryWardrobe = myBasket.getMyWardrobe();
        for (String categoryName: laundryWardrobe.getCategoryNames()) {
            displayClothingInCategory(categoryName, myBasket);
        }
    }

    // EFFECTS: prints out clothing in closet based on colour + brand + type
    private void displayClosetClothes() {
        System.out.println("Clothing in your closet: ");
        Wardrobe laundryWardrobe = myCloset.getMyWardrobe();
        for (String categoryName: laundryWardrobe.getCategoryNames()) {
            displayClothingInCategory(categoryName, myCloset);
        }
    }


    // EFFECTS: displays alerts of types of clothing low in stock in the closet and which items need to be washed
    private void displayAlerts() {
        Wardrobe closetWardrobe = myCloset.getMyWardrobe();
        for (String cc: closetWardrobe.getCategoryNames()) {
            ClothingCategory currCategory = closetWardrobe.getCategory(cc);
            if (closetWardrobe.isLow(currCategory)) {
                System.out.println("\nLOW ON" + cc + ". PLEASE WASH YOUR CLOTHES!!!");
            }
            System.out.println("\n The following need to be washed:");
            for (Clothing myClothes: closetWardrobe.getClothes(currCategory)) {
                if (myClothes.isDirty()) {
                    System.out.println("\tID " + myClothes.getID() + ": " + myClothes.getColour()
                            + " " + myClothes.getBrand() + " " + currCategory);
                }

            }
        }
    }

    // MODIFIES: this and wardrobe
    // EFFECTS: changes low stock of given category input (i.e. when a piece of clothing is low in stock)
    private void adjustLowStock() {
        System.out.println("What do you want to change?");
        boolean keepGoing = true;
        while (keepGoing) {
            String categoryToChange = assignClothingCategory();
            System.out.println("Input amount of " + categoryToChange + " for when it is low in stock:");
            int lowStock = Integer.parseInt(input.next());
            myCloset.getMyWardrobe().setCategoryLowStock(categoryToChange, lowStock);
            System.out.println("Do you want to keep changing? y or n?");
            String ans = input.next();
            if (ans.equals("n")) {
                keepGoing = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: transfers all clothing from a  given location to their appropriate location
    //          - only works for laundry basket –> laundry room -> user closet
    private void transferAllItems(LaundryLocation laundryLocation) {
        if (laundryLocation == this.myCloset) {
            laundryLocation.transferAllClothes(myBasket);
            System.out.println("\nSuccessfully transferred all dirty clothing!");
        } else if (laundryLocation == this.myBasket) {
            laundryLocation.transferAllClothes(myLaundry);
            System.out.println("\nYour laundry basket is empty!");
        } else {
            laundryLocation.transferAllClothes(myCloset);
            System.out.println("\n Your laundry room is empty!");
        }
    }

    // MODIFIES: this
    // EFFECTS: transfers all clothing from given location based on given clothing type input
    private void transferByType(LaundryLocation laundryLocation) {
        System.out.println("What do you want to transfer?");
        String category = assignClothingCategory();
        if (laundryLocation.equals(myBasket)) {
            laundryLocation.transferClothingByType(category, myLaundry);
        } else {
            laundryLocation.transferClothingByType(category, myCloset);
        }
        System.out.println("\n Successfully transferred the items!");
    }

    // MODIFIES: this
    // EFFECTS: transfers clothing from clothing types in given location based on given colour input
    private void transferByColour(LaundryLocation laundryLocation) {
        System.out.println("What colour do you want to transfer?");
        List<String> allColours = laundryLocation.getMyWardrobe().getAllColoursAdded();
        for (String colour: allColours) {
            System.out.println(colour);
        }
        boolean keepGoing = true;
        while (keepGoing) {
            String colour = input.next();
            colour = colour.toLowerCase();
            if (allColours.contains(colour)) {
                keepGoing = false;
                if (laundryLocation.equals(myBasket)) {
                    laundryLocation.transferClothingByColour(myLaundry, colour);
                } else {
                    laundryLocation.transferClothingByColour(myCloset, colour);
                }
            } else {
                System.out.println("Selection unavailable or invalid...");
            }
        }
        System.out.println("\n Successfully transferred the items!");
    }

    // MODIFIES: this
    // EFFECTS: transfers clothing from clothing types in given location based on input material
    private void transferByMaterial(LaundryLocation laundryLocation) {
        System.out.println("What material do you want to transfer?");
        List<String> allMaterials = myCloset.getMyWardrobe().getAllMaterialsAdded();
        for (String material: allMaterials) {
            System.out.println(material);
        }
        boolean keepGoing = true;
        while (keepGoing) {
            String material = input.next();
            material = material.toLowerCase();
            if (allMaterials.contains(material)) {
                if (laundryLocation.equals(myBasket)) {
                    laundryLocation.transferClothingByMaterial(myLaundry, material);
                } else {
                    laundryLocation.transferClothingByMaterial(myCloset, material);
                }
                keepGoing = false;
            } else {
                System.out.println("Selection unavailable or invalid...");
            }
        }
        System.out.println("\n Successfully transferred the items!");
    }

    // MODIFIES: this
    // EFFECTS: changes username of laundry tracker app
    public void changeUsername() {
        System.out.println("Input username to change to:");
        this.userName = input.next();
        System.out.println("Successfully changed username!");
    }

    // MODIFIES: this
    // EFFECTS: resets the laundry tracker's locations
    public void resetLaundryTracker() {
        System.out.println("Are you sure about this? y or n");
        String ans = input.next();
        if (ans.equals("y")) {
            System.out.println("Reset username to:");
            this.userName = input.next();
            myCloset.resetWardrobe();
            myLaundry.resetWardrobe();
            myBasket.resetWardrobe();
        }
    }

    // EFFECTS: saves laundry locations to file
    private void saveLocationsToFile() {
        saveClosetToFile();
        saveBasketToFile();
        saveLaundryToFile();
    }

    // EFFECTS: saves closet to file
    public void saveClosetToFile() {
        try {
            jsonWriterCloset.open();
            jsonWriterCloset.write(myCloset);
            jsonWriterCloset.close();
            System.out.println("Successfully saved " + myCloset.getUsername() + " to " + JSON_STORE_CLOSET);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_CLOSET);
        }
    }

    // EFFECTS: saves basket to file
    public void saveBasketToFile() {
        try {
            jsonWriterBasket.open();
            jsonWriterBasket.write(myBasket);
            jsonWriterBasket.close();
            System.out.println("Successfully saved " + myBasket.getUsername() + " to " + JSON_STORE_BASKET);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_BASKET);
        }
    }

    // EFFECTS: saves laundry to file
    public void saveLaundryToFile() {
        try {
            jsonWriterLaundry.open();
            jsonWriterLaundry.write(myLaundry);
            jsonWriterLaundry.close();
            System.out.println("Successfully saved " + myLaundry.getUsername() + " to " + JSON_STORE_LAUNDRY);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_LAUNDRY);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads laundry locations and user name from file
    private void loadLocationsFromFile() {
        loadClosetFromFile();
        loadBasketFromFile();
        loadLaundryFromFile();
        this.userName = this.myCloset.getUsername().substring(0, 8);
    }

    // MODIFIES: this
    // EFFECTS: loads closet from file
    public void loadClosetFromFile() {
        try {
            myCloset = jsonReaderCloset.readLocation();
            System.out.println("Successfully loaded " + myCloset.getUsername() + " from " + JSON_STORE_CLOSET);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_CLOSET);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads basket from file
    public void loadBasketFromFile() {
        try {
            myBasket = jsonReaderBasket.readLocation();
            System.out.println("Successfully loaded " + myBasket.getUsername() + " from " + JSON_STORE_CLOSET);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_BASKET);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads laundry from file
    public void loadLaundryFromFile() {
        try {
            myLaundry = jsonReaderLaundry.readLocation();
            System.out.println("Successfully loaded " + myLaundry.getUsername() + " from " + JSON_STORE_CLOSET);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_LAUNDRY);
        }
    }
}
