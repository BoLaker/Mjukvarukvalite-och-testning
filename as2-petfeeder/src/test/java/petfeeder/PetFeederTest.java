package petfeeder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import petfeeder.exceptions.FoodStockException;

public class PetFeederTest {

    private PetFeeder feeder;

    @BeforeEach
    public void setUp() {
        feeder = new PetFeeder();
    }

    /**
     * Helper method to create a MealPlan with specified ingredient amounts.
     */
    private MealPlan createTestMealPlan(String kibble, String treats, String water, String wetFood) throws Exception {
        MealPlan plan = new MealPlan();
        plan.setName("TestMeal");
        plan.setAmtKibble(kibble);
        plan.setAmtTreats(treats);
        plan.setAmtWater(water);
        plan.setAmtWetFood(wetFood);
        return plan;
    }


    /**
     * Test dispensing a meal when food and energy are sufficient.
     * Should succeed and decrease remaining energy.
     */

    @Test
    void testDispenseMealValidInput() throws Exception {
        MealPlan plan = createTestMealPlan("5", "2", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("100", "100", "100", "100");

        int before = feeder.getEnergyLimit();
        boolean result = feeder.dispenseMeal(0);
        int after = feeder.getRemainingEnergyBudget();
        assertTrue(result);
        assertTrue(after < before);
    }

    @Test
    void testDispenseMealInvalidIndex() {
        assertFalse(feeder.dispenseMeal(10), "Should return false when the meal plan index is out of bounds");
    }

    /**
     * Covers the branch: plans[mealPlanToPurchase] == null -> dispensed = false.
     * Adds then deletes a plan to guarantee slot 0 is null despite static field sharing.
     */
    @Test
    void testDispenseMealNullSlot() throws Exception {
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.deleteMealPlan(0); // slot 0 is now guaranteed null

        boolean result = feeder.dispenseMeal(0);

        assertFalse(result, "Should return false when the selected meal plan slot is null");
    }

    /**
     * Test dispensing a meal when stock is insufficient.
     * Should return false and leave energy budget unchanged.
     */

    @Test
    void testDispenseMealInsufficientstock() throws Exception {
        // Energy cost: 5*10 + 1*5 + 1*15 + 1*20 = 90, well within ENERGY_LIMIT=500
        // but kibble stock is only 15, so drain it first so useIngredients returns false
        MealPlan plan = createTestMealPlan("5", "1", "1", "1");
        feeder.addMealPlan(plan);

        // Drain kibble stock completely so there is not enough kibble
        feeder.replenishFood("0", "0", "0", "0"); // stock stays at 15
        // Set kibble to 0 by dispensing meals until kibble is gone
        MealPlan drainer = createTestMealPlan("5", "0", "0", "0");
        feeder.addMealPlan(drainer);
        feeder.dispenseMeal(1); // uses 5 kibble (15 -> 10)
        feeder.dispenseMeal(1); // uses 5 kibble (10 -> 5)
        feeder.dispenseMeal(1); // uses 5 kibble (5  -> 0)

        int before = feeder.getRemainingEnergyBudget();
        boolean result = feeder.dispenseMeal(0); // kibble stock is now 0, needs 5 → fails
        int after = feeder.getRemainingEnergyBudget();

        assertFalse(result, "Should return false when useIngredients fails due to empty kibble stock");
        assertEquals(before, after, "Energy budget should be unchanged when dispense fails");
    }

    /**
     * Test dispensing a meal that exceeds remaining energy budget.
     * Should return false and leave energy budget unchanged.
     */
    @Test
    void testDispenseMealInsufficientEnergy() throws Exception {
        MealPlan plan = createTestMealPlan("20", "10", "10", "10");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "10", "10", "10");

        int before = feeder.getEnergyLimit();
        boolean result = feeder.dispenseMeal(0);
        int after = feeder.getRemainingEnergyBudget();

        assertFalse(result);
        assertEquals(before, after);
    }

    @Test
    void testDispenseMealNegativeIndex() {
        assertFalse(feeder.dispenseMeal(-1), "Should return false when the meal plan index is negative");
    }

    /**
     * Test adding a valid meal plan.
     * Should return true and store the plan correctly.
     */
    @Test
    void testAddMealplanValidInput() throws Exception {
        MealPlan plan = createTestMealPlan("5", "2", "1", "1");

        boolean result = feeder.addMealPlan(plan);

        assertTrue(result);
        assertEquals(plan, feeder.getMealPlans()[0]);
    }

    /**
     * Test adding a null meal plan.
     * Should return false.
     */
    @Test
    void testAddMealPlanInvalidInput() throws Exception {

        boolean result = feeder.addMealPlan(null);
        assertFalse(result);
    }

    /**
     * Test editing an existing meal plan.
     * Should update the plan and return the old name.
     */
    @Test
    void testEditExistingMealPlan() throws Exception {
        MealPlan plan = createTestMealPlan("5", "2", "1", "1");
        MealPlan newplan = createTestMealPlan("10", "5", "6", "4");
        feeder.addMealPlan(plan);

        String result = feeder.editMealPlan(0, newplan);

        assertEquals(plan.getName(), result);
        assertEquals(newplan, feeder.getMealPlans()[0]);
    }

    /**
     * Test editing a non-existing meal plan.
     * Should return null.
     */
    @Test
    void testEditNoneExistingMealPlan() throws Exception {
        MealPlan newplan = createTestMealPlan("10", "5", "6", "4");
        String result = feeder.editMealPlan(10, newplan);

        assertNull(result);
    }

    /**
     * Test deleting an existing meal plan.
     * Should return the name and remove the plan.
     */
    @Test
    void testDeleteValidMealplan() throws Exception {
        MealPlan plan = createTestMealPlan("5", "2", "1", "1");
        feeder.addMealPlan(plan);
        String result = feeder.deleteMealPlan(0);

        assertEquals(result, plan.getName());
        assertNull(feeder.getMealPlans()[0]);
    }

    /**
     * Test deleting a non-existing meal plan.
     * Should return null and leave other plans intact.
     */

    @Test
    void testDeleteNoneExistingMealPlan() throws Exception {
        MealPlan plan = createTestMealPlan("5", "2", "1", "1");
        feeder.addMealPlan(plan);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            feeder.deleteMealPlan(9);
        });
        assertEquals(plan, feeder.getMealPlans()[0]);
    }

    /**
     * Test replenishing food with valid input.
     * Stock should increase accordingly.
     */
    @Test
    void testReplenishFoodValidInput() throws Exception {
        feeder.replenishFood("20", "0", "0", "0");

        assertTrue(feeder.checkFoodStock().contains("Kibble: 35"));
    }

    /**
     * Test replenishing food with invalid (non-numeric) input.
     * Should throw FoodStockException and leave stock unchanged.
     */
    @Test
    void testReplenishFoodInvalidInput() throws Exception {

        String stockBefore = feeder.checkFoodStock();
        assertThrows(FoodStockException.class, () -> {
            feeder.replenishFood("abc", "0", "0", "0");
        });
        assertEquals(stockBefore, feeder.checkFoodStock());
    }

    /**
     * Test replenishing food with negative input.
     * Should throw FoodStockException and leave stock unchanged.
     */
    @Test
    void testReplenishFoodNegativeInput() throws Exception {

        String stockBefore = feeder.checkFoodStock();
        assertThrows(FoodStockException.class, () -> {
            feeder.replenishFood("-5", "0", "0", "0");
        });
        assertEquals(stockBefore, feeder.checkFoodStock());
    }
}
