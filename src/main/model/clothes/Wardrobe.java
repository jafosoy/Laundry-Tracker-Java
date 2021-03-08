package model.clothes;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Wardrobe implements Writable {
    private final Map<ClothingCategory, List<Clothing>> allClothes;
    private final int defaultLowStock = 0;
    private final ClothingCategory dressOrSkirt = new ClothingCategory("dress/skirt", defaultLowStock);
    private final ClothingCategory formalWear = new ClothingCategory("formalwear", defaultLowStock);
    private final ClothingCategory jacketCoat = new ClothingCategory("jacket/coat", defaultLowStock);
    private final ClothingCategory jeans = new ClothingCategory("jeans", defaultLowStock);
    private final ClothingCategory pants = new ClothingCategory("pants", defaultLowStock);
    private final ClothingCategory shirtsTanks = new ClothingCategory("shirts/tanks", defaultLowStock);
    private final ClothingCategory shorts = new ClothingCategory("shorts", defaultLowStock);
    private final ClothingCategory sleepWear = new ClothingCategory("sleepwear", defaultLowStock);
    private final ClothingCategory socks = new ClothingCategory("socks", defaultLowStock);
    private final ClothingCategory sportswear = new ClothingCategory("sportswear", defaultLowStock);
    private final ClothingCategory sweater = new ClothingCategory("sweater", defaultLowStock);
    private final ClothingCategory underwear = new ClothingCategory("underwear", defaultLowStock);
    private final List<String> allColoursAdded;
    private final List<String> allMaterialsAdded;

    //EFFECTS: constructs a user wardrobe, and adds an empty list of clothing to wardrobe for each clothing category
    //         specified above
    public Wardrobe() {
        allClothes = new HashMap<>();
        allClothes.put(dressOrSkirt, new ArrayList<>());
        allClothes.put(formalWear, new ArrayList<>());
        allClothes.put(jacketCoat, new ArrayList<>());
        allClothes.put(jeans, new ArrayList<>());
        allClothes.put(pants, new ArrayList<>());
        allClothes.put(shirtsTanks, new ArrayList<>());
        allClothes.put(shorts, new ArrayList<>());
        allClothes.put(sleepWear, new ArrayList<>());
        allClothes.put(socks, new ArrayList<>());
        allClothes.put(sportswear, new ArrayList<>());
        allClothes.put(sweater, new ArrayList<>());
        allClothes.put(underwear, new ArrayList<>());
        allColoursAdded = new ArrayList<>();
        allMaterialsAdded = new ArrayList<>();
    }

    // REQUIRES: input clothing type must correspond to a category name in wardrobe
    // MODIFIES: this
    // EFFECTS: adds clothing to given clothing category
    //          if clothing is new, updates clothing id to next id in category, else, doesn't update clothing id
    public void addClothing(String type, Clothing clothes, Boolean isNew) {
        ClothingCategory category = getCategory(type);
        if (isNew) {
            clothes.setID(category.getNextID());
            category.updateID();
        }
        this.allClothes.get(category).add(clothes);
        if (!allColoursAdded.contains(clothes.getColour())) {
            allColoursAdded.add(clothes.getColour());
        }
        if (!allMaterialsAdded.contains(clothes.getMaterial())) {
            allMaterialsAdded.add(clothes.getMaterial());
        }
    }

    // REQUIRES: input clothing type must correspond to a category name and list of clothing
    //           must contain a clothing with given id
    // MODIFIES: this
    // EFFECTS: removes clothing with given id from the list of clothing corresponding to given type
    public void removeClothing(String type, int id) {
        ClothingCategory category = getCategory(type);
        Clothing currClothing = getMyClothing(type, id);
        allClothes.get(category).remove(currClothing);
    }

    // REQUIRES: input clothing type must correspond to a category name and list of clothing must contain a clothing
    //           with given id`
    // EFFECTS: returns clothing with given id from the list of clothing corresponding to given type
    public Clothing getMyClothing(String type, int id) {
        ClothingCategory category = getCategory(type);
        Clothing currClothing = null;
        List<Clothing> sortedClothes = allClothes.get(category);
        for (Clothing clothing: sortedClothes) {
            if (clothing.getID() == id) {
                currClothing = clothing;
            }
        }
        return currClothing;
    }

    // EFFECTS: returns the list of clothing with the category name
    public List<Clothing> getClothes(ClothingCategory type) {
        return allClothes.get(type);
    }

    // REQUIRES: input string must correspond to a category name in wardrobe
    // EFFECTS: returns the clothing category according to the string input
    public ClothingCategory getCategory(String name) {
        ClothingCategory category = null;
        for (ClothingCategory cc : allClothes.keySet()) {
            if (cc.getCategoryName().equals(name)) {
                category = cc;
            }
        }
        return category;
    }

    // EFFECTS: returns a list of all the clothing category names
    public List<String> getCategoryNames() {
        List<String> categories = new LinkedList<>();
        categories.add(dressOrSkirt.getCategoryName());
        categories.add(formalWear.getCategoryName());
        categories.add(jacketCoat.getCategoryName());
        categories.add(jeans.getCategoryName());
        categories.add(pants.getCategoryName());
        categories.add(shirtsTanks.getCategoryName());
        categories.add(shorts.getCategoryName());
        categories.add(sleepWear.getCategoryName());
        categories.add(socks.getCategoryName());
        categories.add(sportswear.getCategoryName());
        categories.add(sweater.getCategoryName());
        categories.add(underwear.getCategoryName());
        return categories;
    }

    // REQUIRES: size >= 0
    // MODIFIES: this and ClothingCategory
    // EFFECTS: sets the size for when clothing type is low in stock of clothing of category with given name
    public void setCategoryLowStock(String type, int size) {
        getCategory(type).updateLowStock(size);
    }

    // EFFECTS: returns the list of colours of clothing in wardrobe
    public List<String> getAllColoursAdded() {
        return this.allColoursAdded;
    }

    // EFFECTS: returns the list of materials of clothing in wardrobe
    public List<String> getAllMaterialsAdded() {
        return this.allMaterialsAdded;
    }

    // EFFECTS: returns true if the category is low on stock of clothing
    public Boolean isLow(ClothingCategory category) {
        return category.getLowStock() >= allClothes.get(category).size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("clothing sets", clothingSetsToJson());
        return json;
    }

    // EFFECTS: returns clothing sets in this wardrobe as a JSON array
    public JSONArray clothingSetsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ClothingCategory cc: this.allClothes.keySet()) {
            jsonArray.put(categoryToJson(cc));
        }
        return jsonArray;
    }

    // EFFECTS: returns a clothing set in this wardrobe as a JSON object
    private JSONObject categoryToJson(ClothingCategory cc) {
        JSONObject json = new JSONObject();
        json.put("category", cc.toJson());
        json.put("clothes", clothesToJson(cc));
        return json;
    }

    // EFFECTS: returns clothes in given clothing set as a JSON array
    private JSONArray clothesToJson(ClothingCategory cc) {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c: getClothes(cc)) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
