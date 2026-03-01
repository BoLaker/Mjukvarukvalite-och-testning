package petfeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petfeeder.exceptions.FoodStockException;

import static org.junit.jupiter.api.Assertions.*;

public class FoodContainerTest {
    private FoodContainer container;

    /**
     * Sets up a fresh FoodContainer before each test.
     * The default constructor sets all ingredient levels to 15.
     */
    @BeforeEach
    public void setUp() {
        container = new FoodContainer();
    }

    /**
     * Tests the default constructor to ensure initial values are correctly set.
     */
    @Test
    public void testInitialStock() {
        assertEquals(15, container.getKibble(), "Initial kibble should be 15");
        assertEquals(15, container.getWater(), "Initial water should be 15");
        assertEquals(15, container.getWetFood(), "Initial wet food should be 15");
        assertEquals(15, container.getTreats(), "Initial treats should be 15");
    }

    /**
     * Tests adding a valid amount of kibble to the stock.
     */
    @Test
    public void testAddKibbleSuccess() throws FoodStockException {
        container.addKibble("5");
        assertEquals(20, container.getKibble(), "Kibble should increase by 5, totaling 20");
    }

    /**
     * Tests adding a negative amount of kibble, expecting an exception.
     */
    @Test
    public void testAddKibbleNegativeAmount() {
        assertThrows(FoodStockException.class, () -> container.addKibble("-5"), "Adding a negative amount should throw a FoodStockException");
    }

    /**
     * Tests adding an invalid non-numeric string, expecting an exception.
     */
    @Test
    public void testAddKibbleNotANumber() {
        assertThrows(FoodStockException.class, () -> container.addKibble("abc"), "Adding a non-numeric string should throw a FoodStockException");
    }

    /**
     * Tests adding a valid amount of wet food.
     */
    @Test
    public void testAddWetFoodSuccess() throws FoodStockException {
        container.addWetFood("5");
        assertEquals(20, container.getWetFood(), "Wet food should increase by 5, totaling 20");
    }

    /**
     * Tests checking if there are enough ingredients for a small meal.
     */
    @Test
    public void testEnoughIngredientsSuccess() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("10");
        meal.setAmtWater("10");
        meal.setAmtWetFood("10");
        meal.setAmtTreats("10");

        assertTrue(container.enoughIngredients(meal), "Should return true when stock is sufficient");
    }

    /**
     * Tests checking if there are enough ingredients when stock is too low.
     */
    @Test
    public void testEnoughIngredientsInsufficient() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("20"); // Stock is only 15

        assertFalse(container.enoughIngredients(meal), "Should return false when stock is insufficient");
    }

    /**
     * Tests using ingredients for a meal, expecting stock to decrease.
     */
    @Test
    public void testUseIngredientsSuccess() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("5");
        meal.setAmtWater("5");
        meal.setAmtWetFood("5");
        meal.setAmtTreats("5");

        boolean result = container.useIngredients(meal);

        assertTrue(result, "Should successfully use ingredients");
        assertEquals(10, container.getKibble(), "Kibble should be reduced to 10");
        assertEquals(10, container.getWater(), "Water should be reduced to 10");
        assertEquals(10, container.getWetFood(), "Wet food should be reduced to 10");
        assertEquals(10, container.getTreats(), "Treats should be reduced to 10");
    }
}