package model.clothes;

import org.json.JSONObject;
import persistence.Writable;

public class ClothingCategory implements Writable {
    private final String categoryName;
    private int lowStock;
    private int nextID;

    // EFFECTS: constructs a clothing category by it's name and number of items
    //          representing when the category is low in stock + initializes id of clothing
    public ClothingCategory(String category, int lowStock) {
        this.categoryName = category;
        this.lowStock = lowStock;
        this.nextID = 1;
    }

    // EFFECTS: returns the name of the category
    public String getCategoryName() {
        return this.categoryName;
    }

    // EFFECTS: returns the number representing when category is low in stock
    public int getLowStock() {
        return this.lowStock;
    }

    // REQUIRES: size >= 0
    // EFFECTS: updates low stock to given size
    public void updateLowStock(int size) {
        this.lowStock = size;
    }

    // MODIFIES: this
    // EFFECTS: returns the current ID of clothing in the category
    public int getNextID() {
        return this.nextID;
    }

    // MODIFIES: this
    // EFFECTS: updates next ID by adding 1
    public void updateID() {
        this.nextID++;
    }

    // MODIFIES: this
    // EFFECTS: sets the ID to the given int
    public void setNextID(int id) {
        this.nextID = id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", categoryName);
        jsonObject.put("low stock", lowStock);
        jsonObject.put("next ID", nextID);

        return jsonObject;
    }
}
