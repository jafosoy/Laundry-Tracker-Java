package model;

import model.clothes.Clothing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaundryTrackerTest {
    LaundryTracker testLaundryTracker;

    @BeforeEach
    public void runBefore() {
        testLaundryTracker = new LaundryTracker("Aaron", "Bedroom", "Bedroom Floor",
                "Main");
    }

    @Test
    public void testResetLocations() {
        assertEquals(0, testLaundryTracker.getMyCloset().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryBasket().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryRoom().getClothingCategories().size());
        for (int i = 0; i < 10; i++) {
            testLaundryTracker.addClothingType(String.valueOf(i));
        }
        assertEquals(10, testLaundryTracker.getMyCloset().getClothingCategories().size());
        assertEquals(10, testLaundryTracker.getLaundryBasket().getClothingCategories().size());
        assertEquals(10, testLaundryTracker.getLaundryRoom().getClothingCategories().size());
        assertEquals("Bedroom", testLaundryTracker.getMyCloset().getName());
        assertEquals("Bedroom Floor", testLaundryTracker.getLaundryBasket().getName());
        assertEquals("Main", testLaundryTracker.getLaundryRoom().getName());

        testLaundryTracker.resetLocation("clst", "Bathroom");
        testLaundryTracker.resetLocation("lbkt", "Bathroom Floor");
        testLaundryTracker.resetLocation("lndrm", "Second Floor");

        assertEquals(0, testLaundryTracker.getMyCloset().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryBasket().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryRoom().getClothingCategories().size());
        assertEquals("Bathroom", testLaundryTracker.getMyCloset().getName());
        assertEquals("Bathroom Floor", testLaundryTracker.getLaundryBasket().getName());
        assertEquals("Second Floor", testLaundryTracker.getLaundryRoom().getName());
    }

    @Test
    public void testChangeLaundryTrackerName() {
        assertEquals("Aaron's Laundry Tracker", testLaundryTracker.getLaundryTrackerName());
        testLaundryTracker.changeName("Ethan");
        assertEquals("Ethan's Laundry Tracker", testLaundryTracker.getLaundryTrackerName());
    }

    @Test
    public void testAddClothingType() {
        assertEquals(0, testLaundryTracker.getMyCloset().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryBasket().getClothingCategories().size());
        assertEquals(0, testLaundryTracker.getLaundryRoom().getClothingCategories().size());
        testLaundryTracker.addClothingType("shirts");
        assertEquals(1, testLaundryTracker.getMyCloset().getClothingCategories().size());
        assertEquals(1, testLaundryTracker.getLaundryBasket().getClothingCategories().size());
        assertEquals(1, testLaundryTracker.getLaundryRoom().getClothingCategories().size());
    }

    @Test
    public void testTransferLaundryToClosetByClothingType() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "colours",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "polyester"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "denim"));
        }
        testLaundryTracker.transferClothingType("shirts", testLaundryTracker.getLaundryRoom());
        assertEquals(0,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes().size());
        assertEquals(0,
                testLaundryTracker.getMyCloset().getClothingType("pants").getMyClothes().size());
    }

    @Test
    public void testTransferBasketToLaundryByClothingType() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "colours",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "polyester"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "denim"));
        }
        testLaundryTracker.transferClothingType("shirts", testLaundryTracker.getLaundryBasket());
        assertEquals(0,
                testLaundryTracker.getLaundryBasket().getClothingType("shirts").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getLaundryBasket().getClothingType("pants").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(0,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
    }

    @Test
    public void testTransferBasketToLaundryByColour() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "cotton"));
        }
        testLaundryTracker.transferClothingColour("darks", testLaundryTracker.getLaundryBasket());
        assertEquals(5,
                testLaundryTracker.getLaundryBasket().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryBasket().getClothingType("pants").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
    }

    @Test
    public void testTransferLaundryToClosetByColour() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "cotton"));
        }
        testLaundryTracker.transferClothingColour("darks", testLaundryTracker.getLaundryRoom());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getMyCloset().getClothingType("pants").getMyClothes().size());
    }



    @Test
    public void testTransferBasketToLaundryByMaterial() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "polyester"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryBasket().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "denim"));
        }
        testLaundryTracker.transferClothingMaterial("denim", testLaundryTracker.getLaundryBasket());
        assertEquals(10,
                testLaundryTracker.getLaundryBasket().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryBasket().getClothingType("pants").getMyClothes().size());
        assertEquals(0,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
    }

    @Test
    public void testTransferLaundryToClosetByMaterial() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "polyester"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "denim"));
        }
        testLaundryTracker.transferClothingMaterial("denim", testLaundryTracker.getLaundryRoom());
        assertEquals(10,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryRoom().getClothingType("pants").getMyClothes().size());
        assertEquals(0,
                testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getMyCloset().getClothingType("pants").getMyClothes().size());
    }

    @Test
    public void testTransferAllDirtyLaundryToBasket() {
        testLaundryTracker.addClothingType("shirts");
        testLaundryTracker.addClothingType("pants");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getMyCloset().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 6, "cotton"));
            testLaundryTracker.getMyCloset().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "darks",4, 3, "cotton"));
        }
        for(int i = 0; i < 6; i++) {
            testLaundryTracker.getMyCloset().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "colours",4, 0, "polyester"));
            testLaundryTracker.getMyCloset().getClothingType("pants").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 4, "denim"));
        }
        testLaundryTracker.transferDirtyClothes();
        assertEquals(6,
                testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getLaundryBasket().getClothingType("shirts").getMyClothes().size());
        assertEquals(5,
                testLaundryTracker.getMyCloset().getClothingType("pants").getMyClothes().size());
        assertEquals(6,
                testLaundryTracker.getLaundryBasket().getClothingType("pants").getMyClothes().size());
    }



    @Test
    public void testTransferAllLaundryToCloset() {
        testLaundryTracker.addClothingType("shirts");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "colours",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "polyester"));
        }
        testLaundryTracker.transferAllClothes(testLaundryTracker.getLaundryRoom());
        assertEquals(0,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes().size());
        for(Clothing testShirt: testLaundryTracker.getMyCloset().getClothingType("shirts").getMyClothes()) {
            assertEquals(0, testShirt.getDays());
        }
    }

    @Test
    public void testTransferAllLaundryToLaundryRoom() {
        testLaundryTracker.addClothingType("shirts");
        for(int i = 0; i < 5; i++) {
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "colours",4, 6, "cotton"));
            testLaundryTracker.getLaundryRoom().getClothingType("shirts").addClothing(new Clothing(String.valueOf(i),
                    "S", "lights",4, 6, "polyester"));
        }
        testLaundryTracker.transferAllClothes(testLaundryTracker.getLaundryBasket());
        assertEquals(0,
                testLaundryTracker.getLaundryBasket().getClothingType("shirts").getMyClothes().size());
        assertEquals(10,
                testLaundryTracker.getLaundryRoom().getClothingType("shirts").getMyClothes().size());
    }

    @Test
    public void testGetAllClothingTypesNotThere() {
        assertEquals(0, testLaundryTracker.getAllClothingTypes().size());
    }

    @Test
    public void testGetAllClothingTypesOneThere() {
        assertEquals(0, testLaundryTracker.getAllClothingTypes().size());
        testLaundryTracker.addClothingType("jeans");
        assertEquals(1, testLaundryTracker.getAllClothingTypes().size());

    }

}
