package model.clothes;

import org.json.JSONObject;
import persistence.Writable;

// Represents a piece of clothing with it's brand, color and size
public class Clothing implements Writable {
    private final String brand;
    private final String size;
    private final String colour;
    private int frequency;                // sets frequency of use before placing in laundry
    private int days;                     // sets number of days worn
    private String material;              // the type of material
    private int id;                       // sets default id of clothing

    // REQUIRES: freq > 0 and days >= 0
    // EFFECTS: creates a new clothing, with it's brand, size, color, frequency of wear, days worn, if dirty,
    //          and default ID to 0
    public Clothing(String brandName, String size, String colour, int freq, int days, String material) {
        this.brand = brandName;
        this.size = size;
        this.colour = colour;
        this.frequency = freq;
        this.days = days;
        this.material = material;
        this.id = 0;

    }

    // REQUIRES: 1 <= i
    // MODIFIES: this
    // EFFECTS: sets a new frequency to clothing
    public void setNewFrequency(int freq) {
        this.frequency = freq;
    }

    // MODIFIES: this
    // EFFECTS: adds a day to number of days clothing is worn
    public void addDays(int days) {
        this.days += days;
    }

    // MODIFIES: this
    // EFFECTS: sets the number of days worn to 0
    public void resetDays() {
        this.days = 0;
    }

    // EFFECTS: returns true if number of days worn is >= frequency of wear
    public Boolean isDirty() {
        return this.days >= this.frequency;
    }

    // REQUIRES: input number > 0
    // MODIFIES: this
    // EFFECTS: sets id number to given number
    public void setID(int idNum) {
        this.id = idNum;
    }

    // EFFECTS: gets the id of the clothing
    public int getID() {
        return id;
    }

    // EFFECTS: gets the brand of the piece of clothing
    public String getBrand() {
        return brand;
    }

    // EFFECTS: gets the size of the piece of clothing
    public String getSize() {
        return size;
    }

    // EFFECTS: gets the colour of the piece of clothing
    public String getColour() {
        return colour;
    }

    // EFFECTS: gets the frequency of wears of clothing before recommended wash
    public int getFrequency() {
        return frequency;
    }

    // EFFECTS: gets the number of days the piece of clothing has been worn
    public int getDays() {
        return days;
    }

    // EFFECTS: gets the material of the piece of clothing
    public String getMaterial() {
        return material;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("brand", brand);
        jsonObject.put("size", size);
        jsonObject.put("colour", colour);
        jsonObject.put("frequency", frequency);
        jsonObject.put("days", days);
        jsonObject.put("material", material);
        jsonObject.put("id", id);

        return jsonObject;
    }
}
