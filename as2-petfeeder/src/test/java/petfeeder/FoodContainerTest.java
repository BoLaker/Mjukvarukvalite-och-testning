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
     * Tests checking if there are enough ingredients when kibble stock is too low.
     */
    @Test
    public void testEnoughIngredientsInsufficientKibble() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("20"); // Stock is only 15

        assertFalse(container.enoughIngredients(meal), "Should return false when kibble stock is insufficient");
    }

    /**
     * Tests checking if there are enough ingredients when water stock is too low.
     */
    @Test
    public void testEnoughIngredientsInsufficientWater() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtWater("20"); // Stock is only 15

        assertFalse(container.enoughIngredients(meal), "Should return false when water stock is insufficient");
    }

    /**
     * Tests checking if there are enough ingredients when wet food stock is too low.
     */
    @Test
    public void testEnoughIngredientsInsufficientWetFood() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtWetFood("20"); // Stock is only 15

        assertFalse(container.enoughIngredients(meal), "Should return false when wet food stock is insufficient");
    }

    /**
     * Tests checking if there are enough ingredients when treats stock is too low.
     */
    @Test
    public void testEnoughIngredientsInsufficientTreats() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtTreats("20"); // Stock is only 15

        assertFalse(container.enoughIngredients(meal), "Should return false when treats stock is insufficient");
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

    /**
     * Tests using ingredients when stock is insufficient, expecting the method to return false
     * and leave the stock unchanged.
     */
    @Test
    public void testUseIngredientsFailure() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("20"); // Stock is only 15

        boolean result = container.useIngredients(meal);

        assertFalse(result, "Should return false when there are not enough ingredients");
        assertEquals(15, container.getKibble(), "Kibble stock should remain unchanged");
        assertEquals(15, container.getWater(), "Water stock should remain unchanged");
        assertEquals(15, container.getWetFood(), "Wet food stock should remain unchanged");
        assertEquals(15, container.getTreats(), "Treats stock should remain unchanged");
    }

    // additional tests for line coverage purposes.

    /**
     * Tests using invalid input such as negative or non-numeric values for treats, expecting exceptions.
     */
    @Test
    void testAddTreatsFailure() {
        assertThrows(FoodStockException.class, () -> container.addTreats("-1"), "Adding a negative amount of treats should throw a FoodStockException");
        assertThrows(FoodStockException.class, () -> container.addTreats("abc"), "Adding a non-numeric string for treats should throw a FoodStockException");
    }

    /**
     * Tests using invalid input such as negative or non-numeric values for treats, expecting exceptions.
     */
    @Test
    void testAddWaterFailure() {
        assertThrows(FoodStockException.class, () -> container.addWater("-1"), "Adding a negative amount of water should throw a FoodStockException");
        assertThrows(FoodStockException.class, () -> container.addWater("abc"), "Adding a non-numeric string for water should throw a FoodStockException");
    }

    /**
     * Tests using invalid input such as negative or non-numeric values for treats, expecting exceptions.
     */
    @Test
    void testAddWetFoodFailure() {
        assertThrows(FoodStockException.class, () -> container.addWetFood("-1"), "Adding a negative amount of wet food should throw a FoodStockException");
        assertThrows(FoodStockException.class, () -> container.addWetFood("abc"), "Adding a non-numeric string for wet food should throw a FoodStockException");
    }


}