package model.clothes;

import java.awt.*;

// Represents a piece of clothing with it's brand, color and size
public class Clothing {
    private int id = 0;                   // sets id of clothing (i.e. to distinguish same clothing)
    private final String brand;
    private final String size;
    private final String colour;
    private int frequency;                // sets frequency of use before placing in laundry
    private int days;                     // sets number of days worn
    private String material;              // the type of material

    // REQUIRES: freq > 0 and days >= 0
    // EFFECTS: creates a new clothing, with it's brand, size, color, frequency of wear, days worn, and if dirty
    public Clothing(String brandName, String size, String colour, int freq, int days, String material) {
        this.brand = brandName;
        this.size = size;
        this.colour = colour;
        this.frequency = freq;
        this.days = days;
        this.material = material;

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

    // MODIFES: this
    // EFFECTS: sets the number of days worn to 0
    public void resetDays() {
        this.days = 0;
    }

    // EFFECTS: returns true if number of days worn is >= frequency of wear
    public Boolean isDirty() {
        return this.days >= this.frequency;
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

    // EFFECTS: sets the id of the piece of clothing
    public void setId(int identifier) {
        this.id = identifier;
    }

    // EFFECTS: gets the id of the piece of clothing
    public int getId() {
        return id;
    }

    // EFFECTS: gets the material of the piece of clothing
    public String getMaterial() {
        return material;
    }
}
