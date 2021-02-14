package model;

import model.clothes.ClothingType;

import java.util.ArrayList;
import java.util.List;

// A location of where a list of clothing types are currently located, and the name of the location
public class Location {
    private final List<ClothingType> clothingCategories;
    private String name;

    // EFFECTS: creates a new Location with an empty list of clothing types
    public Location(String name) {
        this.name = name;
        clothingCategories = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if there is no clothing group with the given label in the closet's clothing categories
    //          - adds clothing type with the given name
    //          - returns true
    //          otherwise, returns false
    public Boolean addClothingType(String label) {
        for (ClothingType category : clothingCategories) {
            if (category.getTypeName().equals(label)) {
                return false;
            }
        }
        clothingCategories.add(new ClothingType(label));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if there is a clothing group with the given label in the closet's clothing categories
    //          - removes the clothing type with the given name
    //          - returns true
    //          otherwise, returns false
    public Boolean removeClothingType(String label) {
        for (ClothingType category : clothingCategories) {
            if (category.getTypeName().equals(label)) {
                this.clothingCategories.remove(category);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: Clothing Type must be in Clothing Categories
    // EFFECTS: returns the ClothingType in clothingCategories with given label
    public ClothingType getClothingType(String name) {
        ClothingType wantedClothingType = null;
        for (ClothingType category : clothingCategories) {
            if (category.getTypeName().equals(name)) {
                wantedClothingType = category;
            }
        }
        return wantedClothingType;
    }

    // EFFECTS: returns the name of the location
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the list of clothing types in location
    public List<ClothingType> getClothingCategories() {
        return clothingCategories;
    }
}
