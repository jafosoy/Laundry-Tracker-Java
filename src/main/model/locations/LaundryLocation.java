package model.locations;



import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// A location of where a wardrobe of clothing are currently located, and the name of the location
public abstract class LaundryLocation implements Writable {
    private Wardrobe myWardrobe;
    private final String username;

    // EFFECTS: creates a new Location with its name and set of clothing (wardrobe)
    public LaundryLocation(String name) {
        this.username = name;
        myWardrobe = new Wardrobe();
    }


    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing (specific to initial location) from this laundry location to the
    //          given laundry location
    public void transferAllClothes(LaundryLocation location) {
        Wardrobe initialWardrobe = this.myWardrobe;
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                initialWardrobe.removeClothing(cc, currClothing.getID());
                targetWardrobe.addClothing(cc, currClothing, false);
            }
        }
    }

    // REQUIRES: input category name must correspond to a category in wardrobe
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers clothing of category with given name from this laundry location to the given laundry
    //          location
    public void transferClothingByType(String category, LaundryLocation location) {
        Wardrobe initialWardrobe = this.myWardrobe;
        Wardrobe targetWardrobe = location.getMyWardrobe();
        ClothingCategory targetCategory = initialWardrobe.getCategory(category);
        List<Clothing> targetClothes = initialWardrobe.getClothes(targetCategory);
        for (int i = targetClothes.size(); i > 0; i--) {
            Clothing currClothing = targetClothes.get(i - 1);
            initialWardrobe.removeClothing(targetCategory.getCategoryName(), currClothing.getID());
            targetWardrobe.addClothing(targetCategory.getCategoryName(), currClothing, false);
        }

    }


    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing of given input colour from this laundry location to the
    //          given laundry location (if no clothing is of given colour, does nothing)
    public void transferClothingByColour(LaundryLocation location, String colour) {
        Wardrobe initialWardrobe = this.myWardrobe;
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                if (currClothing.getColour().equals(colour)) {
                    initialWardrobe.removeClothing(cc, currClothing.getID());
                    targetWardrobe.addClothing(cc, currClothing, false);
                }
            }
        }
    }

    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing of given input material from this laundry location to the
    //          given laundry location (if no clothing is of given material, does nothing)
    public void transferClothingByMaterial(LaundryLocation location, String material) {
        Wardrobe initialWardrobe = this.myWardrobe;
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                if (currClothing.getMaterial().equals(material)) {
                    initialWardrobe.removeClothing(cc, currClothing.getID());
                    targetWardrobe.addClothing(cc, currClothing, false);
                }
            }
        }
    }

    // MODIFIES: this and wardrobe
    // EFFECTS: resets the location's wardrobe (i.e. removes all clothing)
    public void resetWardrobe() {
        this.myWardrobe = new Wardrobe();
    }

    // EFFECTS: returns the name of the location
    public String getUsername() {
        return this.username;
    }

    // EFFECTS: returns the location's wardrobe
    public Wardrobe getMyWardrobe() {
        return this.myWardrobe;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", username);
        json.put("wardrobe", myWardrobe.toJson());
        return json;
    }


}
