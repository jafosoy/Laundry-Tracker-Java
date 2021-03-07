package model.persistence;

import model.clothes.Clothing;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonClothingTest {
    public void checkClothes(String brand, String size, String colour, int frequency, int days, String material,
                             int id, Clothing clothing) {
        assertEquals(brand, clothing.getBrand());
        assertEquals(size, clothing.getSize());
        assertEquals(colour, clothing.getColour());
        assertEquals(frequency, clothing.getFrequency());
        assertEquals(days, clothing.getDays());
        assertEquals(material, clothing.getMaterial());
        assertEquals(id, clothing.getID());
    }
}
