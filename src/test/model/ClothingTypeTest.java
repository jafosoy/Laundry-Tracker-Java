package model;

import model.clothes.Clothing;
import model.clothes.ClothingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

public class ClothingTypeTest {
    private ClothingType clothingType;

    @BeforeEach
    public void runBefore() {
        clothingType = new ClothingType("pants");
    }

    @Test
    public void testAddClothing() {
        clothingType.getClothing(1);
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        assertEquals(1, clothingType.getMyClothes().size());
        clothingType.addClothing(nikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());

    }

    @Test
    public void testAddDuplicateClothing() {
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");
        Clothing newerNikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(nikeJoggers);
        assertEquals(1, clothingType.getMyClothes().size());
        clothingType.addClothing(newerNikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());
    }

    @Test
    public void testRemoveClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        clothingType.addClothing(nikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());
        clothingType.removeClothing(1);
        assertEquals(1, clothingType.getMyClothes().size());
        assertEquals(2, clothingType.getClothing(2).getId());
    }

    @Test
    public void testRemoveDuplicateClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");
        Clothing newerNikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        clothingType.addClothing(nikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());
        clothingType.removeClothing(1);
        assertEquals(1, clothingType.getMyClothes().size());
        clothingType.addClothing(newerNikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());
        clothingType.removeClothing(3);
        assertEquals(1, clothingType.getMyClothes().size());
        assertEquals(2, clothingType.getClothing(2).getId());
    }

    @Test
    public void testGetClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        clothingType.addClothing(nikeJoggers);
        assertEquals(nikeJoggers, clothingType.getClothing(2));
    }

    @Test
    public void testGetMissingClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        clothingType.addClothing(nikeJoggers);
        assertEquals(nikeJoggers, clothingType.getClothing(2));
        clothingType.removeClothing(2);
        assertNull(clothingType.getClothing(2));
    }

    @Test
    public void testGetMyClothing() {
        assertEquals(emptyList(), clothingType.getMyClothes());
    }

    @Test
    public void testDoesntContainClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");

        assertEquals(0, clothingType.getMyClothes().size());
        assertFalse(clothingType.containsClothing(1));
        clothingType.addClothing(blueJeans);
        assertEquals(1, clothingType.getMyClothes().size());
        assertFalse(clothingType.containsClothing(2));

    }

    @Test
    public void testContainsClothing() {
        Clothing blueJeans = new Clothing("Calvin Klein", "S", "darks", 7, 0,
                "denim");
        Clothing nikeJoggers = new Clothing("Nike", "M", "colours", 3, 0,
                "cotton");

        assertEquals(0, clothingType.getMyClothes().size());
        clothingType.addClothing(blueJeans);
        assertEquals(1, clothingType.getMyClothes().size());
        assertTrue(clothingType.containsClothing(1));
        clothingType.addClothing(nikeJoggers);
        assertEquals(2, clothingType.getMyClothes().size());
        assertTrue(clothingType.containsClothing(2));

    }

    @Test
    public void testSetLowStock() {
        assertEquals(0, clothingType.getMyClothes().size());
        assertEquals(0, clothingType.getLowStock());
        clothingType.setLowStock(5);
        assertEquals(5, clothingType.getLowStock());
    }
}
