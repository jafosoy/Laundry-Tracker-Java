package ui;

// Using basis of user interface on the Teller Application

import model.LaundryTracker;
import model.Location;
import model.clothes.Clothing;
import model.clothes.ClothingType;

import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class LaundryTrackerApp {
    private LaundryTracker myLaundryTracker;
    private final Scanner input;

    // EFFECTS: runs a Laundry Tracker Application
    public LaundryTrackerApp() {
        myLaundryTracker = new LaundryTracker("AARON", "Bedroom", "Side Door", "Main");
        this.input = new Scanner(System.in);
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
    // EFFECTS: processes editor menu
    private void runMenuEditor() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayAppEditor();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("sw")) {
                keepGoing = false;
            } else {
                processMenuEditor(command);
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
        } else if (command.equals("ea")) {
            runMenuEditor();
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
            transferDirtyClothes();
        } else if (command.equals("ca")) {
            displayAlerts();
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the laundry basket menu
    private void processCommandLaundryBasket(String command) {
        if (command.equals("tra")) {
            transferAllItems(myLaundryTracker.getLaundryBasket());
        } else if (command.equals("trt")) {
            transferByType(myLaundryTracker.getLaundryRoom());
        } else if (command.equals("trc")) {
            transferByColour(myLaundryTracker.getLaundryRoom());
        } else if (command.equals("trm")) {
            transferByMaterial(myLaundryTracker.getLaundryRoom());
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the laundry room menu
    private void processCommandLaundry(String command) {
        if (command.equals("tra")) {
            transferAllItems(myLaundryTracker.getLaundryRoom());
        } else if (command.equals("trt")) {
            transferByType(myLaundryTracker.getMyCloset());
        } else if (command.equals("trc")) {
            transferByColour(myLaundryTracker.getMyCloset());
        } else if (command.equals("trm")) {
            transferByMaterial(myLaundryTracker.getMyCloset());
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

    // MODIFIES: this
    // EFFECTS: processes user command for editing the app
    private void processMenuEditor(String command) {
        if (command.equals("en")) {
            editLaundryTrackerName();
        } else if (command.equals("cn")) {
            editMyClosetName();
        } else if (command.equals("clst")) {
            resetLocation("clst");
        } else if (command.equals("cb")) {
            editMyBasketName();
        } else if (command.equals("lbkt")) {
            resetLocation("lbkt");
        } else if (command.equals("cl")) {
            editMyLaundryName();
        } else if (command.equals("lndrm")) {
            resetLocation("lndrm");
        } else {
            System.out.println("Selection unavailable or invalid.");
        }
    }


    // EFFECTS: displays the app main menu
    private void displayMainMenu() {
        System.out.println("\nSelect From:");
        System.out.println("\tmc  -> check out your closet");
        System.out.println("\tlb  -> is your laundry basket full?");
        System.out.println("\tlr  -> transfer your clothes back to your closet");
        System.out.println("\tea  -> edit your Laundry Tracker");
        System.out.println("\tyes -> are you ready to leave?");
    }

    // EFFECTS: displays the closet menu
    private void displayMenuMyCloset() {
        System.out.println("\nSelect From:");
        System.out.println("\tac -> add clothing to your closet");
        System.out.println("\trc -> remove clothing from your closet");
        System.out.println("\ttc -> transfer your dirty clothes");
        System.out.println("\tca -> find your alerts");
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
        System.out.println("\nSelect From:");
        System.out.println("\ttra -> transfer all your clothes to the laundry room");
        System.out.println("\ttrt -> transfer all your clothing by type to the laundry room");
        System.out.println("\ttrc -> transfer all your clothing by colours to the laundry room");
        System.out.println("\ttrm -> transfer all your clothing to the laundry room");
        System.out.println("\tsw  -> are you ready to leave?");
    }

    // EFFECTS: displays the laundry room menu
    private void displayMenuLaundry() {
        System.out.println("\nSelect From:");
        System.out.println("\ttra -> transfer all your clothes to the laundry room");
        System.out.println("\ttrt -> transfer all your clothing by type to the laundry room");
        System.out.println("\ttrc -> transfer all your clothing by colours to the laundry room");
        System.out.println("\ttrm -> transfer all your clothing to the laundry room");
        System.out.println("\tsw  -> are you ready to leave?");
    }


    // EFFECTS: displays the editor view
    private void displayAppEditor() {
        System.out.println("\nSelect From:");
        System.out.println("\ten    -> switch your username");
        System.out.println("\tcn    -> change your closet name");
        System.out.println("\tclst  -> reset your closet");
        System.out.println("\tcb    -> change your laundry basket name");
        System.out.println("\tlbkt  -> reset your laundry basket");
        System.out.println("\tcl    -> change your laundry room name");
        System.out.println("\tlndrm -> reset your laundry room");
        System.out.println("\tsw    -> are you ready to leave?");
    }


    // MODIFIES: this
    // EFFECTS: adds a new piece of clothing categorized by it's type and properties
    private void addClothing() {
        System.out.println("Describe your type of clothing (e.g. shirts, pants, sweaters, [in pl form]).");
        List<String> types = showAllClothingTypes();
        String type = input.next();
        System.out.println("What brand is it? Say NONE for no brand.");
        String brand = input.next();
        System.out.println("What size? Please answer in XS, S, M, L, XL");
        String size = input.next();

        System.out.println("What colour? (e.g. lights, darks, bright colour, etc.)");
        String colour = input.next();
        System.out.println("Frequency of use?");
        int frequency = parseInt(input.next());
        System.out.println("Material?");
        String material = input.next();
        Clothing newAdd = new Clothing(brand, size, colour, frequency, 0, material);
        if (!types.contains(type)) {
            myLaundryTracker.addClothingType(type);

        }
        myLaundryTracker.getMyCloset().getClothingType(type).addClothing(newAdd);
        System.out.println("\nSuccessfully added item to your closet!");
        System.out.println("\tTaking you back to closet menu...");

    }

    // EFFECTS: returns list of clothing types
    private List<String> showAllClothingTypes() {
        return myLaundryTracker.getAllClothingTypes();
    }

    // MODIFIES: this
    // EFFECTS: removes clothing based on user given type, brand, and colour
    private void removeClothing() {
        displayClosetClothes();
        System.out.println("Describe your piece of clothing.");
        String type = input.next();
        System.out.println("What colour?");
        String colour = input.next();
        System.out.println("Brand?");
        String brand = input.next();

        Clothing clothing = null;
        for (Clothing myClothes : myLaundryTracker.getMyCloset().getClothingType(type).getMyClothes()) {
            if (myClothes.getColour().equals(colour) && myClothes.getBrand().equals(brand)) {
                System.out.println("Is this your piece of clothing? Type y or n");
                System.out.println("\t" + myClothes.getColour() + myClothes.getBrand()
                        + type + "?");
                String ans = input.next();
                if (ans.equals("y")) {
                    myLaundryTracker.getMyCloset().getClothingType(type).getMyClothes().remove(myClothes);

                }
            }
        }


    }

    // MODIFIES: this
    // EFFECTS: transfers all dirty clothing in user closet to laundry basket
    private void transferDirtyClothes() {
        myLaundryTracker.transferDirtyClothes();
        System.out.println("\n All dirty items are transferred! Better get to washing them!");
    }

    // EFFECTS: displays alerts of types of clothing low in stock in the closet and which items need to be washed
    private void displayAlerts() {
        if (this.myLaundryTracker.getMyCloset().getClothingCategories().size() == 0) {
            System.out.println("No clothing at the moment!");
        }

        for (ClothingType category : this.myLaundryTracker.getMyCloset().getClothingCategories()) {
            if (category.getMyClothes().size() <= category.getLowStock()) {
                System.out.println("\nLOW ON" + category.getTypeName() + ". PLEASE WASH YOUR CLOTHES!!!");
                System.out.println("\n The following need to be washed:");
                for (Clothing myClothes : category.getMyClothes()) {
                    if (myClothes.isDirty()) {
                        System.out.println("\n" + myClothes.getColour() + " " + myClothes.getBrand() + " "
                                + category.getTypeName());
                    }

                }
            } else {
                System.out.println("\nEverything is clean! Carry on!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: transfers all clothing from a  given location to their appropriate location
    //          - only works for laundry basket –> laundry room -> user closet
    private void transferAllItems(Location location) {
        if (location == this.myLaundryTracker.getLaundryBasket()) {
            myLaundryTracker.transferAllClothes(myLaundryTracker.getLaundryBasket());
            System.out.println("\nYour laundry basket is empty!");
        } else {
            myLaundryTracker.transferAllClothes(myLaundryTracker.getLaundryRoom());
            System.out.println("\n Your laundry room is empty!");
        }
    }

    // MODIFIES: this
    // EFFECTS: transfers all clothing from given location based on given clothing type input
    private void transferByType(Location location) {
        System.out.println("What do you want to transfer?");
        String type = input.next();
        this.myLaundryTracker.transferClothingType(type, location);
        System.out.println("\n Successfully transferred the items!");
    }

    // MODIFIES: this
    // EFFECTS: transfers clothing from clothing types in given location based on given colour input
    private void transferByColour(Location location) {
        System.out.println("What colour do you want to transfer?");
        String colour = input.next();
        this.myLaundryTracker.transferClothingColour(colour, location);
        System.out.println("\n Successfully transferred the items!");
    }

    // MODIFIES: this
    // EFFECTS: transfers clothing from clothing types in given location based on input material
    private void transferByMaterial(Location location) {
        System.out.println("What material do you want to transfer?");
        String material = input.next();
        this.myLaundryTracker.transferClothingColour(material, location);
        System.out.println("\n Successfully transferred the items!");
    }

    // EFFECTS: prints out all clothing in all 3 locations based on colour + brand + type
    private void displayAllClothing() {
        System.out.println("All items:");
        displayClosetClothes();
        displayLaundryBasketClothes();
        displayLaundryRoomClothes();
    }

    // EFFECTS: prints out clothing in laundry room based on colour + brand + type
    private void displayLaundryRoomClothes() {
        System.out.println("Clothing at your laundry room: ");
        for (ClothingType categories : myLaundryTracker.getLaundryRoom().getClothingCategories()) {
            for (Clothing myClothes : categories.getMyClothes()) {
                System.out.println("\t" + myClothes.getColour() + myClothes.getBrand() + categories.getTypeName());
            }
        }
    }

    // EFFECTS: prints out clothing in laundry basket based on colour + brand + type
    private void displayLaundryBasketClothes() {
        System.out.println("Clothing in your laundry basket: ");
        for (ClothingType categories : myLaundryTracker.getLaundryBasket().getClothingCategories()) {
            for (Clothing myClothes : categories.getMyClothes()) {
                System.out.println("\t" + myClothes.getColour() + myClothes.getBrand() + categories.getTypeName());
            }
        }
    }

    // EFFECTS: prints out clothing in closet based on colour + brand + type
    private void displayClosetClothes() {
        System.out.println("Clothing in your closet: ");
        for (ClothingType categories : myLaundryTracker.getMyCloset().getClothingCategories()) {
            for (Clothing myClothes : categories.getMyClothes()) {
                System.out.println("\t" + myClothes.getColour() + myClothes.getBrand() + categories.getTypeName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the name and empties the given location
    private void resetLocation(String location) {
        System.out.println("Enter a name:");
        String name = input.next();
        myLaundryTracker.resetLocation(location, name);
        System.out.println("\nlocations reset!");

    }

    // MODIFIES: this
    // EFFECTS: changes name of Laundry Tracker to **name's Laundry Tracker
    private void editLaundryTrackerName() {
        System.out.println("Enter a new name");
        String name = input.next();
        myLaundryTracker.setLaundryTrackerName(name);
    }

    // MODIFIES: this
    // EFFECTS: updates new name of location for user closet
    private void editMyClosetName() {
        System.out.println("Enter new name:");
        String name = input.next();
        myLaundryTracker.getMyCloset().setName(name);
    }

    // MODIFIES: this
    // EFFECTS: updates new name of location for laundry basket
    private void editMyBasketName() {
        System.out.println("Enter new name:");
        String name = input.next();
        myLaundryTracker.getLaundryBasket().setName(name);
    }

    // MODIFIES: this
    // EFFECTS: updates new name of location for laundry basket
    private void editMyLaundryName() {
        System.out.println("Enter new name:");
        String name = input.next();
        myLaundryTracker.getLaundryRoom().setName(name);
    }

}
