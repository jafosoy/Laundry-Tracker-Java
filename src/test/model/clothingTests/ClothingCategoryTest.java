package model.clothingTests;

import model.clothes.ClothingCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClothingCategoryTest {
    ClothingCategory testCategory;

    @BeforeEach
    public void runBefore() {
        testCategory = new ClothingCategory("shirts/tanks", 0);
    }

    @Test
    public void testGetCategoryName() {
        assertEquals("shirts/tanks", testCategory.getCategoryName());
    }

    @Test
    public void testGetLowStock() {
        assertEquals(0, testCategory.getLowStock());
    }

    @Test
    public void testUpdateLowStock() {
        testCategory.updateLowStock(10);
        assertEquals(10, testCategory.getLowStock());
    }

    @Test
    public void testGetNextID() {
        assertEquals(1, testCategory.getNextID());
    }

    @Test
    public void testUpdateID() {
        assertEquals(1, testCategory.getNextID());
        testCategory.updateID();
        assertEquals(2, testCategory.getNextID());
    }

}
