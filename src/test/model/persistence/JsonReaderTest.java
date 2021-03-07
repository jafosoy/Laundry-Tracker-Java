package model.persistence;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.LaundryLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonClothingTest {

    @Test
    void testReaderInvalidFileLocation() {
        JsonReader reader = new JsonReader("./data/invalidFileLocation.json");
        try {
            LaundryLocation ll = reader.readLocation();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyClosetLocation() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyClosetLocation.json");
        try {
            LaundryLocation testCloset = reader.readLocation();
            assertEquals("Test Closet's Closet", testCloset.getUsername());
            for (String category : testCloset.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("File either missing or invalid");
        }
    }

    @Test
    void testReaderGeneralClosetLocation() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralClosetLocation.json");
        try {
            LaundryLocation testCloset = reader.readLocation();
            assertEquals("Test Closet's Closet", testCloset.getUsername());
            assertEquals(4, testCloset.getMyWardrobe().getAllColoursAdded().size());
            assertEquals(2, testCloset.getMyWardrobe().getAllMaterialsAdded().size());
            for (String category : testCloset.getMyWardrobe().getCategoryNames()) {
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
            fail("File either missing or invalid");
        }
    }

    @Test
    void testReaderEmptyBasketLocation() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyBasketLocation.json");
        try {
            LaundryLocation testBasket = reader.readLocation();

            assertEquals("Test Basket's Laundry Basket", testBasket.getUsername());
            for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
                assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("File either missing or invalid");
        }
    }

    @Test
    void testReaderGeneralBasketLocation() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBasketLocation.json");
        try {
            LaundryLocation testBasket = reader.readLocation();
            assertEquals("Test Basket's Laundry Basket", testBasket.getUsername());
            assertEquals(4, testBasket.getMyWardrobe().getAllColoursAdded().size());
            assertEquals(2, testBasket.getMyWardrobe().getAllMaterialsAdded().size());
            for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
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
            fail("File either missing or invalid");
        }
    }

    @Test
    void testReaderEmptyLaundry() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyLaundryLocation.json");
        try {
            LaundryLocation testLaundry = reader.readLocation();
            assertEquals("Test Laundry's Laundry Room", testLaundry.getUsername());
            for (String category : testLaundry.getMyWardrobe().getCategoryNames()) {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        } catch (IOException e) {
            fail("File either missing or invalid");
        }
    }

    @Test
    void testReaderGeneralLaundry() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralLaundryLocation.json");
        try {
            LaundryLocation testLaundry = reader.readLocation();
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
            fail("File either invalid or missing");
        }
    }
}
