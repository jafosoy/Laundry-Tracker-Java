package model.locationsTests;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.locations.Closet;
import model.locations.LaundryBasket;
import model.locations.LaundryLocation;
import model.locations.LaundryRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LaundryLocationTest {
    private Closet testCloset;
    private LaundryRoom testLaundry;
    private LaundryBasket testBasket;

    @BeforeEach
    public void runBefore() {
        testCloset = new Closet("Test Closet");
        testLaundry = new LaundryRoom("Test Laundry Room");
        testBasket = new LaundryBasket("Test Laundry Basket");
        for (int i = 0; i < 5; i++) {
            testCloset.getMyWardrobe().addClothing("jeans",
                    new Clothing("NONE", "S", "black", 1, 3, "cotton"), false);
            testCloset.getMyWardrobe().addClothing("pants",
                    new Clothing("NONE", "S", "black", 5, 3, "cotton"), false);

            testBasket.getMyWardrobe().addClothing("jeans",
                    new Clothing("NONE", "S", "black", 1, 3, "denim"), false);
            testBasket.getMyWardrobe().addClothing("jeans",
                    new Clothing("NONE", "S", "white", 1, 3, "cotton"), false);
            testBasket.getMyWardrobe().addClothing("shirts/tanks",
                    new Clothing("NONE", "S", "black", 1, 4, "cotton"), false);
            testBasket.getMyWardrobe().addClothing("shirts/tanks",
                    new Clothing("NONE", "S", "blue", 1, 3, "denim"),
                    false);

            testLaundry.getMyWardrobe().addClothing("jeans",
                    new Clothing("NONE", "S", "black", 1, 3, "cotton"), false);
            testLaundry.getMyWardrobe().addClothing("jeans",
                    new Clothing("NONE", "S", "white", 1, 3, "cotton"), false);
            testLaundry.getMyWardrobe().addClothing("shirts/tanks",
                    new Clothing("NONE", "S", "white", 1, 4, "cotton"), false);
            testLaundry.getMyWardrobe().addClothing("shirts/tanks",
                    new Clothing("NONE", "S", "white", 1, 3, "polyester"),
                    false);

            //45 pieces of clothing (20 in shirts, 25 in jeans)
        }
    }

    @Test
    public void testTransferAllClothesFromLaundry() {
        testLaundry.transferAllClothes(testCloset);
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
        }
        for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
            if(category.equals("jeans")) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(15, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(10, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("pants")) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        }

        int numCleanJeans = 0;
        int numDirtyJeans = 0;
        for (Clothing jeans: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("jeans"))) {
            if (jeans.isDirty()) {
                numDirtyJeans++;
            } else {
                numCleanJeans++;
            }
        }
        assertEquals(5, numDirtyJeans);
        assertEquals(10, numCleanJeans);
        for (Clothing shirts: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("shirts/tanks"))) {
            assertFalse(shirts.isDirty());
        }
    }

    @Test
    public void testTransferAllClothesFromBasket() {
        testBasket.transferAllClothes(testLaundry);
        for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
            assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
        }
        for (String category : testLaundry.getMyWardrobe().getCategoryNames()) {
            if (category.equals("jeans")) {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                assertEquals(20, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                assertEquals(20, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else {
                ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
    }

    @Test
    public void testTransferAllClothesFromCloset() {
        testCloset.transferAllClothes(testBasket);
        for (String category : testCloset.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
            if (category.equals("pants")) {
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
            if (category.equals("jeans")) {
                assertEquals(15, testBasket.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(10, testBasket.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        }

    }

    @Test
    public void testTransferAllClothesFromLaundryByType() {
        testLaundry.transferClothingByType("jeans", testCloset);
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(10, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
            if(category.equals("jeans")) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(15, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("pants")) {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else {
                ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        }

        int numCleanJeans = 0;
        int numDirtyJeans = 0;
        for (Clothing jeans: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("jeans"))) {
            if (jeans.isDirty()) {
                numDirtyJeans++;
            } else {
                numCleanJeans++;
            }
        }
        assertEquals(5, numDirtyJeans);
        assertEquals(10, numCleanJeans);
    }

    @Test
    public void testTransferAllClothesFromBasketByType() {
        testBasket.transferClothingByType("jeans", testLaundry);
        for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(0, testBasket.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(10, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(20, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(10, testLaundry.getMyWardrobe().getClothes(cc).size());
            }else {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
    }

    @Test
    public void testTransferAllClothesFromLaundryByColour() {
        testLaundry.transferClothingByColour(testCloset, "white");
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(10, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(10, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("pants")) {
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        }

        int numCleanJeans = 0;
        int numDirtyJeans = 0;
        for (Clothing jeans: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("jeans"))) {
            if (jeans.isDirty()) {
                numDirtyJeans++;
            } else {
                numCleanJeans++;
            }
        }
        assertEquals(5, numDirtyJeans);
        assertEquals(5, numCleanJeans);
        for (Clothing shirts: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("shirts/tanks"))) {
            assertFalse(shirts.isDirty());
        }
    }

    @Test
    public void testTransferAllClothesFromBasketByColour() {
        testBasket.transferClothingByColour( testLaundry, "black");
        for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(15, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(15, testLaundry.getMyWardrobe().getClothes(cc).size());
            }else {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
    }

    @Test
    public void testTransferAllClothesFromLaundryByMaterial() {
        testLaundry.transferClothingByMaterial(testCloset, "cotton");
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(5, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testCloset.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(15, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("pants")) {
                assertEquals(5, testCloset.getMyWardrobe().getClothes(cc).size());
            } else {
                assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
            }
        }

        int numCleanJeans = 0;
        int numDirtyJeans = 0;
        for (Clothing jeans: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("jeans"))) {
            if (jeans.isDirty()) {
                numDirtyJeans++;
            } else {
                numCleanJeans++;
            }
        }
        assertEquals(5, numDirtyJeans);
        assertEquals(10, numCleanJeans);
        for (Clothing shirts: testCloset.getMyWardrobe().getClothes(testCloset.getMyWardrobe().getCategory("shirts/tanks"))) {
            assertFalse(shirts.isDirty());
        }
    }

    @Test
    public void testTransferAllClothesFromBasketByMaterial() {
        testBasket.transferClothingByMaterial( testLaundry, "denim");
        for (String category : testBasket.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testBasket.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(5, testBasket.getMyWardrobe().getClothes(cc).size());
            }
        }
        for (String category: testLaundry.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testLaundry.getMyWardrobe().getCategory(category);
            if(category.equals("jeans")) {
                assertEquals(15, testLaundry.getMyWardrobe().getClothes(cc).size());
            } else if (category.equals("shirts/tanks")) {
                assertEquals(15, testLaundry.getMyWardrobe().getClothes(cc).size());
            }else {
                assertEquals(0, testLaundry.getMyWardrobe().getClothes(cc).size());
            }
        }
    }

    @Test
    public void testResetWardrobe() {
        testCloset.resetWardrobe();
        for(String category: testCloset.getMyWardrobe().getCategoryNames()) {
            ClothingCategory cc = testCloset.getMyWardrobe().getCategory(category);
            assertEquals(0, testCloset.getMyWardrobe().getClothes(cc).size());
        }
    }

    @Test
    public void testGetName() {
        assertEquals("Test Closet", testCloset.getName());
    }


}
