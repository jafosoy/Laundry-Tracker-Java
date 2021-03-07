package persistence;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.Closet;
import model.locations.LaundryBasket;
import model.locations.LaundryLocation;
import model.locations.LaundryRoom;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// using basis of persistence package in JsonSerializationDemo
// represents a reader that reads a laundry tracker app from a JSON representation on file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the laundry location from file and returns it;
    //          if an error occurs when reading data on file, throws an IOException
    public LaundryLocation readLocation() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLaundryLocation(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s));

        return contentBuilder.toString();
    }

    // EFFECTS: parses laundry location from JSON object and returns it
    private LaundryLocation parseLaundryLocation(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int nameLength = name.length();
        String closet = "'s Closet";
        String basket = "'s Laundry Basket";
        String laundry = "'s Laundry Room";
        String username;
        LaundryLocation ll;
        if (name.contains(closet)) {
            username = name.substring(0, nameLength - closet.length());
            ll = new Closet(username);
        } else if (name.contains(basket)) {
            username = name.substring(0, nameLength - basket.length());
            ll = new LaundryBasket(username);
        } else {
            username = name.substring(0, nameLength - laundry.length());
            ll = new LaundryRoom(username);
        }
        addToWardrobe(ll, jsonObject);
        return ll;
    }

    // MODIFIES: ll
    // EFFECTS: parses categories from JSON object and updates ll wardrobe, and parses
    //          clothes from JSON object and adds them to wardrobe
    private void addToWardrobe(LaundryLocation ll, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("wardrobe").getJSONArray("clothing sets");
        for (Object json: jsonArray) {
            JSONObject nextClothingSet = (JSONObject) json;
            addToCategory(ll, nextClothingSet);
        }
    }

    // MODIFIES: ll
    // EFFECTS: parses category from JSON object and updates ll wardrobe, and parses
    //          clothes from JSON object and adds them to wardrobe
    private void addToCategory(LaundryLocation ll, JSONObject jsonObject) {
        JSONObject category = jsonObject.getJSONObject("category");
        String categoryName = category.getString("name");
        int lowStock = category.getInt("low stock");
        int nextID = category.getInt("next ID");
        ClothingCategory cc = ll.getMyWardrobe().getCategory(categoryName);
        cc.updateLowStock(lowStock);
        cc.setNextID(nextID);

        JSONArray jsonArray = jsonObject.getJSONArray("clothes");
        for (Object json: jsonArray) {
            JSONObject nextClothing = (JSONObject) json;
            addClothing(ll, categoryName, nextClothing);
        }
    }

    // MODIFIES: ll
    // EFFECTS: parses clothing and adds it to wardrobe in given category
    public void addClothing(LaundryLocation ll, String category, JSONObject jsonObject) {
        String brand = jsonObject.getString("brand");
        String size = jsonObject.getString("size");
        String colour = jsonObject.getString("colour");
        int frequency = jsonObject.getInt("frequency");
        int days = jsonObject.getInt("days");
        String material = jsonObject.getString("material");
        int id = jsonObject.getInt("id");
        Clothing currClothes = new Clothing(brand, size, colour, frequency, days, material);
        currClothes.setID(id);
        ll.getMyWardrobe().addClothing(category, currClothes, false);
    }
}
