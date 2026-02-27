package petfeeder;

import petfeeder.exceptions.MealPlanException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MealPlanTest {
    private MealPlan mp;

    @BeforeEach
    public void setUp() {
        mp = new MealPlan();
    }

    // setAmtTreats
    @Test
    void testSetAmtTreats_Normal() throws Exception {
        mp.setAmtTreats("5");
        assertEquals(5, mp.getAmtTreats());
    }

    @Test
    void testSetAmtTreats_Invalid() {
        // Negative number and non-integer string should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtTreats("-3"));
        assertThrows(MealPlanException.class, () -> mp.setAmtTreats("abc"));
    }

    @Test
    void testSetAmtTreats_Zero() throws Exception {
        // Setting to zero should be valid and result in 0 treats.
        mp.setAmtTreats("0");
        assertEquals(0, mp.getAmtTreats());
    }

    // We only test single normal cases for the other setters since they have similar logic, and we want to avoid redundancy.
    // setAmtKibble
    @Test
    void testSetAmtKibble_Normal() throws Exception {
        mp.setAmtKibble("4");
        assertEquals(4, mp.getAmtKibble());
    }

    // setAmtWater
    @Test
    void testSetAmtWater_Normal() throws Exception {
        mp.setAmtWater("2");
        assertEquals(2, mp.getAmtWater());
    }

    // setAmtWetFood
    @Test
    void testSetAmtWetFood_Normal() throws Exception {
        mp.setAmtWetFood("3");
        assertEquals(3, mp.getAmtWetFood());
    }

    // Test all together for energy cost calculation
    // This also indirectly tests that the erroneous setter methods work together correctly to update the energy cost.
    @Test
    void testMealPlanEnergyCostCalculation_Normal() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("2"); // 2 * 10 = 20
        meal.setAmtWater("1");  // 1 * 5  = 5
        meal.setAmtWetFood("1"); // 1 * 15 = 15
        meal.setAmtTreats("0"); // 0 * 20 = 0

        // Expected energy cost: 20 + 5 + 15 + 0 = 40
        assertEquals(40, meal.getEnergyCost());
    }

    // equals
    @Test
    void testEquals_SameAttributes() throws Exception {
        MealPlan mp1 = new MealPlan();

        // Set attributes for Meal A
        mp1.setName("Meal A");
        mp1.setAmtKibble("2");
        mp1.setAmtWater("1");
        mp1.setAmtWetFood("1");
        mp1.setAmtTreats("0");

        // Set the same attributes for another MealPlan object
        MealPlan mp2 = new MealPlan();
        mp2.setName("Meal A");
        mp2.setAmtKibble("2");
        mp2.setAmtWater("1");
        mp2.setAmtWetFood("1");
        mp2.setAmtTreats("0");

        assertEquals(mp1, mp2);
    }

    @Test
    void testEquals_DifferentAttributes() throws Exception {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");
        mp1.setAmtKibble("2");
        mp1.setAmtWater("1");
        mp1.setAmtWetFood("1");
        mp1.setAmtTreats("0");

        MealPlan mp2 = new MealPlan();
        mp2.setName("Meal B"); // Different name
        mp2.setAmtKibble("2");
        mp2.setAmtWater("1");
        mp2.setAmtWetFood("1");
        mp2.setAmtTreats("0");

        assertNotEquals(mp1, mp2);
    }

    @Test
    void testEquals_SameNameDifferentIngredients() throws Exception {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");
        mp1.setAmtKibble("2");
        mp1.setAmtWater("1");
        mp1.setAmtWetFood("1");
        mp1.setAmtTreats("0");

        MealPlan mp2 = new MealPlan();
        mp2.setName("Meal A");
        mp2.setAmtKibble("1"); // Different amount of kibble(!)
        mp2.setAmtWater("1");
        mp2.setAmtWetFood("1");
        mp2.setAmtTreats("0");

        assertNotEquals(mp1, mp2);
    }
}
