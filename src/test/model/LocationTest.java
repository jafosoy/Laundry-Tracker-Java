package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private Location testLocation;

    @BeforeEach
    public void runBefore() {
        testLocation = new Location("Bedroom");
    }

    @Test
    public void testAddClothingTypeNotThere() {
        assertEquals(0, testLocation.getClothingCategories().size());
        assertTrue(testLocation.addClothingType("pants"));
        assertEquals(1, testLocation.getClothingCategories().size());
        assertTrue(testLocation.addClothingType("shirts"));
        assertEquals(2, testLocation.getClothingCategories().size());
    }

    @Test
    public void testAddClothingTypeAlreadyThere() {
        assertEquals(0, testLocation.getClothingCategories().size());
        assertTrue(testLocation.addClothingType("pants"));
        assertEquals(1, testLocation.getClothingCategories().size());
        assertFalse(testLocation.addClothingType("pants"));
        assertEquals(1, testLocation.getClothingCategories().size());
    }

    @Test
    public void testRemoveClothingTypeNotThere() {
        assertEquals(0, testLocation.getClothingCategories().size());
        assertFalse(testLocation.removeClothingType("pants"));
        testLocation.addClothingType("shirts");
        assertEquals(1, testLocation.getClothingCategories().size());
        assertFalse(testLocation.removeClothingType("sweaters"));
    }

    @Test
    public void testRemoveClothingTypeIsAvailable() {
        assertEquals(0, testLocation.getClothingCategories().size());
        testLocation.addClothingType("pants");
        assertEquals(1, testLocation.getClothingCategories().size());
        assertTrue(testLocation.removeClothingType("pants"));
        assertEquals(0, testLocation.getClothingCategories().size());
        testLocation.addClothingType("shirts");
        testLocation.addClothingType("sweaters");
        assertEquals(2, testLocation.getClothingCategories().size());
        assertTrue(testLocation.removeClothingType("sweaters"));
        assertEquals(1, testLocation.getClothingCategories().size());
    }
}
