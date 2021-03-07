package model.persistence;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.Closet;
import model.locations.LaundryBasket;
import model.locations.LaundryLocation;
import model.locations.LaundryRoom;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// using basis of persistence package test classes in JsonSerializationDemo
public class JsonWriterTest extends JsonClothingTest {

    @Test
    void testWriterInvalidFile() {
        try {
            LaundryLocation testCloset = new Closet("Test Closet");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyClosetLocation() {
        try {
            LaundryLocation testCloset = new Closet("Test Closet");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyClosetLocation.json");
            writer.open();
            writer.write(testCloset);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyClosetLocation.json");
            testCloset = reader.readLocation();
            assertEquals("Test Closet's Closet", testCloset.getUsername());
            for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralClosetLocation() {
        try {
            LaundryLocation testCloset = new Closet("Test Closet");
            for (int i = 0; i < 5; i++) {
                testCloset.getMyWardrobe().addClothing("jeans",
                        new Clothing("NONE", "S", "blue", 4, 0, "denim"), true);
                testCloset.getMyWardrobe().addClothing("pants",
                        new Clothing("NONE", "S", "green", 3, 2, "cotton"), true);
                testCloset.getMyWardrobe().addClothing("underwear",
                        new Clothing("NONE", "S", "black", 1, 0, "cotton"), true);
                testCloset.getMyWardrobe().addClothing("shirts/tanks",
                        new Clothing("NONE", "S", "white", 2, 4, "denim"),
                        true);
            }
            testCloset.getMyWardrobe().getCategory("jeans").updateLowStock(5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralClosetLocation.json");
            writer.open();
            writer.write(testCloset);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralClosetLocation.json");
            testCloset = reader.readLocation();
            assertEquals("Test Closet's Closet", testCloset.getUsername());
            assertEquals(4, testCloset.getMyWardrobe().getAllColoursAdded().size());
            assertEquals(2, testCloset.getMyWardrobe().getAllMaterialsAdded().size());
            for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                List<Clothing> clothingSet = testCloset.getMyWardrobe().getClothes(cc);
                if (category.equals("jeans")) {
                    assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(5, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "blue", 4, 0, "denim", i, c);
                    }
                } else if (category.equals("pants")) {
                    assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "green", 3, 2, "cotton", i, c);
                    }
                } else if (category.equals("underwear")) {
                    assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "black", 1, 0, "cotton", i, c);
                    }
                } else if (category.equals("shirts/tanks")) {
                    assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "white", 2, 4, "denim", i, c);
                    }
                } else {
                    assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
                    assertEquals(1, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                }
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyBasketLocation() {
        try {
            LaundryLocation testBasket = new LaundryBasket("Test Basket");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBasketLocation.json");
            writer.open();
            writer.write(testBasket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBasketLocation.json");
            testBasket = reader.readLocation();
            assertEquals("Test Basket's Laundry Basket", testBasket.getUsername());
            for (String category: testBasket.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
                assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBasketLocation() {
        try {
            LaundryLocation testBasket = new LaundryBasket("Test Basket");
            for (int i = 0; i < 5; i++) {
                testBasket.getMyWardrobe().addClothing("jeans",
                        new Clothing("NONE", "S", "blue", 4, 0, "denim"), true);
                testBasket.getMyWardrobe().addClothing("pants",
                        new Clothing("NONE", "S", "green", 3, 2, "cotton"), true);
                testBasket.getMyWardrobe().addClothing("underwear",
                        new Clothing("NONE", "S", "black", 1, 0, "cotton"), true);
                testBasket.getMyWardrobe().addClothing("shirts/tanks",
                        new Clothing("NONE", "S", "white", 2, 4, "denim"),
                        true);
            }
            testBasket.getMyWardrobe().getCategory("jeans").updateLowStock(5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBasketLocation.json");
            writer.open();
            writer.write(testBasket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBasketLocation.json");
            testBasket = reader.readLocation();
            assertEquals("Test Basket's Laundry Basket", testBasket.getUsername());
            assertEquals(4, testBasket.getMyWardrobe().getAllColoursAdded().size());
            assertEquals(2, testBasket.getMyWardrobe().getAllMaterialsAdded().size());
            for (String category: testBasket.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
                List<Clothing> clothingSet = testBasket.getMyWardrobe().getClothes(cc);
                if (category.equals("jeans")) {
                    assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(5, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "blue", 4, 0, "denim", i, c);
                    }
                } else if (category.equals("pants")) {
                    assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "green", 3, 2, "cotton", i, c);
                    }
                } else if (category.equals("underwear")) {
                    assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "black", 1, 0, "cotton", i, c);
                    }
                } else if (category.equals("shirts/tanks")) {
                    assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "white", 2, 4, "denim", i, c);
                    }
                } else {
                    assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
                    assertEquals(1, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                }
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyLaundryLocation() {
        try {
            LaundryLocation testLaundry = new LaundryRoom("Test Laundry");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLaundryLocation.json");
            writer.open();
            writer.write(testLaundry);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLaundryLocation.json");
            testLaundry = reader.readLocation();
            assertEquals("Test Laundry's Laundry Room", testLaundry.getUsername());
            for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLaundryLocation() {
        try {
            LaundryLocation testLaundry = new LaundryRoom("Test Laundry");
            for (int i = 0; i < 5; i++) {
                testLaundry.getMyWardrobe().addClothing("jeans",
                        new Clothing("NONE", "S", "blue", 4, 0, "denim"), true);
                testLaundry.getMyWardrobe().addClothing("pants",
                        new Clothing("NONE", "S", "green", 3, 2, "cotton"), true);
                testLaundry.getMyWardrobe().addClothing("underwear",
                        new Clothing("NONE", "S", "black", 1, 0, "cotton"), true);
                testLaundry.getMyWardrobe().addClothing("shirts/tanks",
                        new Clothing("NONE", "S", "white", 2, 4, "denim"),
                        true);
            }
            testLaundry.getMyWardrobe().getCategory("jeans").updateLowStock(5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLaundryLocation.json");
            writer.open();
            writer.write(testLaundry);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLaundryLocation.json");
            testLaundry = reader.readLocation();
            assertEquals("Test Laundry's Laundry Room", testLaundry.getUsername());
            assertEquals(4, testLaundry.getMyWardrobe().getAllColoursAdded().size());
            assertEquals(2, testLaundry.getMyWardrobe().getAllMaterialsAdded().size());
            for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                List<Clothing> clothingSet = testLaundry.getMyWardrobe().getClothes(cc);
                if (category.equals("jeans")) {
                    assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(5, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "blue", 4, 0, "denim", i, c);
                    }
                } else if (category.equals("pants")) {
                    assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "green", 3, 2, "cotton", i, c);
                    }
                } else if (category.equals("underwear")) {
                    assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "black", 1, 0, "cotton", i, c);
                    }
                } else if (category.equals("shirts/tanks")) {
                    assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
                    assertEquals(6, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                    for (int i = 1; i < clothingSet.size() + 1; i++) {
                        Clothing c = clothingSet.get(i - 1);
                        checkClothes("NONE", "S", "white", 2, 4, "denim", i, c);
                    }
                } else {
                    assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
                    assertEquals(1, cc.getNextID());
                    assertEquals(0, cc.getLowStock());
                }
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
