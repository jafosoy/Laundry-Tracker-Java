package model;

import model.clothes.Clothing;
import model.clothes.ClothingType;

import java.util.ArrayList;
import java.util.List;

public class LaundryTracker {
    private String laundryTrackerName;
    private Location myCloset;
    private Location laundryBasket;
    private Location laundryRoom;

    // EFFECTS: sets the name of the Laundry Tracker and sets up a closet, laundry basket and laundry room location
    public LaundryTracker(String name, String closetName, String basketName, String laundryRoomName) {
        this.laundryTrackerName = name + "'s Laundry Tracker";
        this.myCloset = new Location(closetName);
        this.laundryBasket = new Location(basketName);
        this.laundryRoom = new Location(laundryRoomName);
    }

    // MODIFIES: this
    // EFFECTS: resets the given location, and gives it a new name
    public void resetLocation(String location, String name) {
        if (location.equals("clst")) {
            this.myCloset = new Location(name);
        } else if (location.equals("lbkt")) {
            this.laundryBasket = new Location(name);
        } else if (location.equals("lndrm")) {
            this.laundryRoom = new Location(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the user (changes the laundry tracker's name)
    public void changeName(String name) {
        this.laundryTrackerName = name + "'s Laundry Tracker";
    }

    // MODIFIES: Location
    // EFFECTS: adds a ClothingType to the list of clothing categories for closet, basket, and laundry room
    public void addClothingType(String name) {
        myCloset.addClothingType(name);
        laundryBasket.addClothingType(name);
        laundryRoom.addClothingType(name);
    }


    // REQUIRES: clothing type is in clothing categories
    // MODIFIES: Clothing Type
    // EFFECTS: transfers clothing from clothing type of a location to the same clothing type at the appropriate
    //          location: my closet –> laundry basket –> laundry room –> my closet
    public void transferClothingType(String clothingType, Location initialLocation) {
        Location targetLocation;
        if (initialLocation == myCloset) {
            targetLocation = laundryBasket;
        } else if (initialLocation == laundryBasket) {
            targetLocation = laundryRoom;
        } else {
            targetLocation = myCloset;
        }
        for (Clothing myClothing : initialLocation.getClothingType(clothingType).getMyClothes()) {
            targetLocation.getClothingType(clothingType).addClothing(myClothing);
        }
        for (int i = initialLocation.getClothingType(clothingType).getMyClothes().size() - 1; i >= 0; i--) {
            initialLocation.getClothingType(clothingType).getMyClothes().remove(i);
        }
    }


    // REQUIRES: Clothing categories contain clothing with given colour
    // MODIFIES: Clothing Type
    // EFFECTS: transfers clothing from clothing types of a location to the same clothing type at the appropriate
    //          location, given colour: my closet –> laundry basket –> laundry room –> my closet
    public void transferClothingColour(String colour, Location initial) {
        Location targetLocation;
        if (initial == myCloset) {
            targetLocation = laundryBasket;
        } else if (initial == laundryBasket) {
            targetLocation = laundryRoom;
        } else {
            targetLocation = myCloset;
        }
        for (ClothingType categories : initial.getClothingCategories()) {
            for (Clothing myClothing : categories.getMyClothes()) {
                if (myClothing.getColour().equals(colour)) {
                    targetLocation.getClothingType(categories.getTypeName()).addClothing(myClothing);
                }
            }
        }
        for (ClothingType categories : initial.getClothingCategories()) {
            for (int i = categories.getMyClothes().size() - 1; i >= 0; i--) {
                Clothing currClothing = categories.getMyClothes().get(i);
                if (currClothing.getColour().equals(colour)) {
                    categories.removeClothing(currClothing.getId());
                }
            }
        }
    }

    // REQUIRES: clothing categories contain clothing with given material
    // MODIFIES: Clothing Type
    // EFFECTS: transfers clothing from clothing types of a location to the same clothing type at the appropriate
    //          location, given material: my closet –> laundry basket –> laundry room –> my closet
    public void transferClothingMaterial(String material, Location initial) {
        Location targetLocation;
        if (initial == myCloset) {
            targetLocation = laundryBasket;
        } else if (initial == laundryBasket) {
            targetLocation = laundryRoom;
        } else {
            targetLocation = myCloset;
        }
        for (ClothingType categories : initial.getClothingCategories()) {
            for (Clothing myClothing : categories.getMyClothes()) {
                if (myClothing.getMaterial().equals(material)) {
                    targetLocation.getClothingType(categories.getTypeName()).addClothing(myClothing);
                }
            }
        }
        for (ClothingType categories : initial.getClothingCategories()) {
            for (int i = categories.getMyClothes().size() - 1; i >= 0; i--) {
                Clothing currClothing = categories.getMyClothes().get(i);
                if (currClothing.getMaterial().equals(material)) {
                    categories.removeClothing(currClothing.getId());
                }
            }
        }
    }


    // MODIFIES: Clothing Type
    // EFFECTS: transfers all dirty clothes from my closet to laundry basket
    public void transferDirtyClothes() {
        for (ClothingType categories : myCloset.getClothingCategories()) {
            for (Clothing clothing : categories.getMyClothes()) {
                if (clothing.isDirty()) {
                    laundryBasket.getClothingType(categories.getTypeName()).addClothing(clothing);
                }
            }
        }
        for (ClothingType categories : myCloset.getClothingCategories()) {
            for (int i = categories.getMyClothes().size() - 1; i >= 0; i--) {
                Clothing currClothing = categories.getMyClothes().get(i);
                if (currClothing.isDirty()) {
                    categories.removeClothing(currClothing.getId());
                }
            }

        }
    }

    // MODIFIES: Clothing Type
    // EFFECTS: transfers all clothes from laundry basket, or laundry room to the appropriate location:
    //           laundry basket –> laundry room –> my closet
    //          - if transferring from the laundry room, reset days worn for all clothing to zero
    public void transferAllClothes(Location initialLocation) {
        for (ClothingType categories : initialLocation.getClothingCategories()) {
            for (Clothing clothing : categories.getMyClothes()) {
                if (initialLocation == this.laundryBasket) {
                    this.laundryRoom.getClothingType(categories.getTypeName()).addClothing(clothing);
                } else if (initialLocation == this.laundryRoom) {
                    clothing.resetDays();
                    this.myCloset.getClothingType(categories.getTypeName()).addClothing(clothing);
                }
            }
        }
        for (ClothingType categories : initialLocation.getClothingCategories()) {
            for (int i = categories.getMyClothes().size() - 1; i >= 0; i--) {
                categories.getMyClothes().remove(i);
            }
        }
    }

    // EFFECTS: returns the list of all clothing types in the laundry tracker
    //          - note that all three locations have the same size and types of clothing
    public List<String> getAllClothingTypes() {
        List<String> allTypes = new ArrayList<>();
        for (ClothingType categories: this.myCloset.getClothingCategories()) {
            allTypes.add(categories.getTypeName());
        }
        return allTypes;
    }


    // EFFECTS: returns the name of the Laundry Tracker
    public String getLaundryTrackerName() {
        return laundryTrackerName;
    }

    // EFFECTS: sets the name of the Laundry Tracker
    public void setLaundryTrackerName(String name) {
        this.laundryTrackerName = name;
    }

    // EFFECTS: returns the Laundry Tracker's closet
    public Location getMyCloset() {
        return myCloset;
    }

    // EFFECTS: returns the Laundry Tracker's laundry basket
    public Location getLaundryBasket() {
        return laundryBasket;
    }

    //EFFECTS: returns the Laundry Basket's laundry room
    public Location getLaundryRoom() {
        return laundryRoom;
    }

}
