package model.clothes;

import java.util.ArrayList;
import java.util.List;

// A clothing type with a name, material, season of clothing, whether its a favourite and lists the clothing within
// said clothing type
public class ClothingType {
    private final String typeName;             // the types of clothing a closet/laundry basket or room can have
    private final List<Clothing> myClothes;    // clothing with this type
    private int lowStock;                      // the size for when a clothing type is low in stock
    private int nextClothing;                  // tracks id of next clothing added

    // EFFECTS: type of clothing type is set to type, an empty list of clothing is set to myClothing, and lowStock to 0
    public ClothingType(String type) {
        this.typeName = type;
        this.nextClothing = 1;
        myClothes = new ArrayList<>();
        lowStock = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds a new piece clothing to the list of clothes
    public void addClothing(Clothing clothing) {
        if (clothing.getId() == 0) {
            clothing.setId(nextClothing);
            this.nextClothing++;
        }

        this.myClothes.add(clothing);

    }

    // MODIFIES: this
    // EFFECTS: removes a piece of clothing from the list of clothing if it exists, given id
    //          - return true
    //          otherwise return false
    public Boolean removeClothing(int id) {
        for (Clothing myClothing : myClothes) {
            if (myClothing.getId() == id) {
                this.myClothes.remove(myClothing);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: clothing exists in list of clothing
    // EFFECTS: returns the piece of clothing with the given id
    public Clothing getClothing(int id) {
        Clothing wantedClothing = null;
        for (Clothing myClothing : myClothes) {
            if (myClothing.getId() == id) {
                wantedClothing = myClothing;
            }
        }
        return wantedClothing;
    }

    // EFFECTS: gets the list of clothing
    public List<Clothing> getMyClothes() {
        return myClothes;
    }

    // EFFECTS: returns true, if the list of clothing contains a given clothing, otherwise false
    public Boolean containsClothing(int id) {
        for (Clothing myClothing : myClothes) {
            if (myClothing.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns size for when clothing type is low in stock of clothing
    public int getLowStock() {
        return lowStock;
    }

    // REQUIRES: size >= 0
    // MODIFIES: this
    // EFFECTS: sets the size for when clothing type is low in stock of clothing
    public void setLowStock(int size) {
        this.lowStock = size;
    }

    // EFFECTS: returns the name of the Clothing Type
    public String getTypeName() {
        return typeName;
    }
}