package model.clothing;

import model.clothes.Clothing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for clothing class
public class ClothingTest {
    private Clothing clothing;
    private int freq = 1;


    @BeforeEach
    public void runBefore() {
        clothing = new Clothing("NONAME", "S", "darks", freq, 0,
                "cotton");
    }

    @Test
    public void testSetNewFrequency(){
        assertEquals(freq, clothing.getFrequency());
        clothing.setNewFrequency(5);
        assertEquals(5, clothing.getFrequency());
    }

    @Test
    public void testAddDays() {
        assertEquals(0, clothing.getDays());
        clothing.addDays(1);
        assertEquals(1, clothing.getDays());
        clothing.addDays(7);
        assertEquals(8, clothing.getDays());
    }

    @Test
    public void testResetDaysAlreadyZero() {
        assertEquals(0, clothing.getDays());
        clothing.resetDays();
        assertEquals(0, clothing.getDays());
    }

    @Test
    public void testResetDaysNonZero() {
        assertEquals(0, clothing.getDays());
        clothing.addDays(7);
        assertEquals(7, clothing.getDays());
        clothing.resetDays();
        assertEquals(0, clothing.getDays());
    }

    @Test
    public void testNotDirty() {
        assertFalse(clothing.isDirty());
    }

    @Test
    public void isDirty() {
        clothing.addDays(freq);
        assertTrue(clothing.isDirty());
        clothing.addDays(1);
        assertTrue(clothing.isDirty());
    }

    @Test
    public void testGetBrand() {
        assertEquals("NONAME", clothing.getBrand());
    }

    @Test
    public void testGetSize() {
        assertEquals("S", clothing.getSize());
    }
}
