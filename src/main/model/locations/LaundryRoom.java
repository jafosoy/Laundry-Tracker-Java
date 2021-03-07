package model.locations;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;

import java.util.List;

public class LaundryRoom extends LaundryLocation {
    public LaundryRoom(String name) {
        super(name + "'s Laundry Room");
    }

    @Override
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing (specific to initial location) from this laundry location to the
    //          given laundry location, and resets the days worn for all clothing
    public void transferAllClothes(LaundryLocation location) {
        Wardrobe initialWardrobe = this.getMyWardrobe();
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                currClothing.resetDays();
                initialWardrobe.removeClothing(cc, currClothing.getID());
                targetWardrobe.addClothing(cc, currClothing, false);
            }
        }
    }

    @Override
    // REQUIRES: input category name must correspond to a category in wardrobe
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers clothing of category with given name from this laundry location to the given laundry
    //          location + resets days of clothing worn
    public void transferClothingByType(String category, LaundryLocation location) {
        Wardrobe initialWardrobe = this.getMyWardrobe();
        Wardrobe targetWardrobe = location.getMyWardrobe();
        ClothingCategory targetCategory = initialWardrobe.getCategory(category);
        List<Clothing> targetClothes = initialWardrobe.getClothes(targetCategory);
        for (int i = targetClothes.size(); i > 0; i--) {
            Clothing currClothing = targetClothes.get(i - 1);
            currClothing.resetDays();
            initialWardrobe.removeClothing(targetCategory.getCategoryName(), currClothing.getID());
            targetWardrobe.addClothing(targetCategory.getCategoryName(), currClothing, false);
        }

    }


    @Override
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing of given input colour from this laundry location to the
    //          given laundry location (if no clothing is of given colour, does nothing) + resets days of clothing worn
    public void transferClothingByColour(LaundryLocation location, String colour) {
        Wardrobe initialWardrobe = this.getMyWardrobe();
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                if (currClothing.getColour().equals(colour)) {
                    currClothing.resetDays();
                    initialWardrobe.removeClothing(cc, currClothing.getID());
                    targetWardrobe.addClothing(cc, currClothing, false);
                }
            }
        }
    }

    @Override
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing of given input material from this laundry location to the
    //          given laundry location (if no clothing is of given material, does nothing) + resets days of clothing
    //          worn
    public void transferClothingByMaterial(LaundryLocation location, String material) {
        Wardrobe initialWardrobe = this.getMyWardrobe();
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc: initialWardrobe.getCategoryNames()) {
            ClothingCategory category = initialWardrobe.getCategory(cc);
            List<Clothing> currClothes = initialWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                if (currClothing.getMaterial().equals(material)) {
                    currClothing.resetDays();
                    initialWardrobe.removeClothing(cc, currClothing.getID());
                    targetWardrobe.addClothing(cc, currClothing, false);
                }
            }
        }
    }

}
