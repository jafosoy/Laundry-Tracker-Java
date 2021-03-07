package model.locations;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;

import java.util.List;

public class Closet extends LaundryLocation {
    public Closet(String name) {
        super(name + "'s Closet");

    }

    @Override
    // MODIFIES: this and wardrobe
    // EFFECTS: transfers all clothing (specific to initial location) from this laundry location to the
    //          given laundry location
    //          will only transfer clothes that are dirty
    public void transferAllClothes(LaundryLocation location) {
        Wardrobe closetWardrobe = this.getMyWardrobe();
        Wardrobe targetWardrobe = location.getMyWardrobe();
        for (String cc : closetWardrobe.getCategoryNames()) {
            ClothingCategory category = closetWardrobe.getCategory(cc);
            List<Clothing> currClothes = closetWardrobe.getClothes(category);
            for (int i = currClothes.size(); i > 0; i--) {
                Clothing currClothing = currClothes.get(i - 1);
                if (currClothing.isDirty()) {
                    closetWardrobe.removeClothing(cc, currClothing.getID());
                    targetWardrobe.addClothing(cc, currClothing, false);
                }

            }
        }
    }
}
