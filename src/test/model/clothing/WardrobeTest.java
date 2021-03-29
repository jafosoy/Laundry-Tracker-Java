package model.clothing;

import model.clothes.Clothing;
import model.clothes.ClothingCategory;
import model.clothes.Wardrobe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WardrobeTest {
    Wardrobe wardrobeTest;

    @BeforeEach
    public void runBefore() {
        wardrobeTest = new Wardrobe();
    }

    @Test
    public void addClothingIsNew() {
        ClothingCategory shirts = wardrobeTest.getCategory("shirts/tanks");
        String type = shirts.getCategoryName();
        Clothing testShirt = new Clothing("Uniqlo", "S", "green", 3, 0, "cotton");
        wardrobeTest.addClothing(type, testShirt, true);

        assertEquals(1, wardrobeTest.getClothes(shirts).size());

        assertTrue(wardrobeTest.getClothes(shirts).contains(testShirt));
        assertEquals(shirts.getNextID()-1, testShirt.getID());

        assertEquals(1, wardrobeTest.getAllColoursAdded().size());
        assertTrue(wardrobeTest.getAllColoursAdded().contains(testShirt.getColour()));

        assertEquals(1, wardrobeTest.getAllMaterialsAdded().size());
        assertTrue(wardrobeTest.getAllMaterialsAdded().contains(testShirt.getMaterial()));
    }

    @Test
    public void addClothingNotNew() {
        ClothingCategory shirts = wardrobeTest.getCategory("shirts/tanks");
        String type = shirts.getCategoryName();
        Clothing testShirt = new Clothing("Uniqlo", "S", "green", 3, 0, "cotton");
        wardrobeTest.addClothing(type, testShirt, true);

        assertEquals(1, wardrobeTest.getClothes(shirts).size());

        assertTrue(wardrobeTest.getClothes(shirts).contains(testShirt));
        assertEquals(shirts.getNextID()-1, testShirt.getID());

        assertEquals(1, wardrobeTest.getAllColoursAdded().size());
        assertTrue(wardrobeTest.getAllColoursAdded().contains(testShirt.getColour()));

        assertEquals(1, wardrobeTest.getAllMaterialsAdded().size());
        assertTrue(wardrobeTest.getAllMaterialsAdded().contains(testShirt.getMaterial()));

        wardrobeTest.removeClothing(type, testShirt.getID());
        wardrobeTest.addClothing(type, testShirt, false);

        assertEquals(1, wardrobeTest.getClothes(shirts).size());

        assertTrue(wardrobeTest.getClothes(shirts).contains(testShirt));
        assertEquals(shirts.getNextID()-1, testShirt.getID());

        assertEquals(1, wardrobeTest.getAllColoursAdded().size());
        assertTrue(wardrobeTest.getAllColoursAdded().contains(testShirt.getColour()));

        assertEquals(1, wardrobeTest.getAllMaterialsAdded().size());
        assertTrue(wardrobeTest.getAllMaterialsAdded().contains(testShirt.getMaterial()));

    }

    @Test
    public void testRemoveClothing() {
        ClothingCategory shirts = wardrobeTest.getCategory("shirts/tanks");
        String type = shirts.getCategoryName();
        Clothing testShirt = new Clothing("uniqlo", "S", "green", 3, 0, "cotton");
        wardrobeTest.addClothing(type, testShirt, true);

        assertEquals(1, wardrobeTest.getClothes(shirts).size());

        assertTrue(wardrobeTest.getClothes(shirts).contains(testShirt));
        assertEquals(shirts.getNextID()-1, testShirt.getID());

        assertEquals(1, wardrobeTest.getAllColoursAdded().size());
        assertTrue(wardrobeTest.getAllColoursAdded().contains(testShirt.getColour()));

        assertEquals(1, wardrobeTest.getAllMaterialsAdded().size());
        assertTrue(wardrobeTest.getAllMaterialsAdded().contains(testShirt.getMaterial()));

        wardrobeTest.removeClothing(type, testShirt.getID());
        assertEquals(0, wardrobeTest.getClothes(shirts).size());
    }

    @Test
    public void testGetMyClothing() {
        ClothingCategory jeans = wardrobeTest.getCategory("jeans");
        String type = jeans.getCategoryName();
        Clothing testJeans = new Clothing("calvin klein", "S", "black", 4, 0, "denim");
        wardrobeTest.addClothing(type, testJeans, true);
        assertEquals(testJeans, wardrobeTest.getMyClothing(type, testJeans.getID()));
    }

    @Test
    public void testGetClothes() {
        ClothingCategory pants = wardrobeTest.getCategory("pants");
        String type = pants.getCategoryName();
        List<Clothing> testPants = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            wardrobeTest.addClothing(type,
                    new Clothing("NONE", "S",  "green", i, 0, "cotton"), true);
            testPants.add(wardrobeTest.getMyClothing(type, i+1));
        }
        assertEquals(10, wardrobeTest.getClothes(pants).size());
        assertTrue(wardrobeTest.getClothes(pants).containsAll(testPants));
    }

    @Test
    public void testGetCategoryNames() {
        assertEquals(12, wardrobeTest.getCategoryNames().size());
    }

    @Test
    public void testSetCategoryLowStock() {
        assertEquals(0, wardrobeTest.getCategory("jeans").getLowStock());
        wardrobeTest.setCategoryLowStock("jeans", 3);
        assertEquals(3, wardrobeTest.getCategory("jeans").getLowStock());
    }

    @Test
    public void testIsLowStock() {
        assertTrue(wardrobeTest.isLow(wardrobeTest.getCategory("jeans")));
    }

    @Test
    public void testIsNotLowStock() {
        ClothingCategory shirts = wardrobeTest.getCategory("shirts/tanks");
        String type = shirts.getCategoryName();
        Clothing testShirt = new Clothing("uniqlo", "S", "green", 3, 0, "cotton");
        wardrobeTest.addClothing(type, testShirt, true);

        assertFalse(wardrobeTest.isLow(shirts));
    }

    @Test
    public void testWardrobeEmpty() {
        assertTrue(wardrobeTest.isEmpty());
    }

    @Test
    public void testWardrobeNotEmpty() {
        ClothingCategory shirts = wardrobeTest.getCategory("shirts/tanks");
        String type = shirts.getCategoryName();
        Clothing testShirt = new Clothing("uniqlo", "S", "green", 3, 0, "cotton");
        wardrobeTest.addClothing(type, testShirt, true);

        assertFalse(wardrobeTest.isEmpty());
    }
}
