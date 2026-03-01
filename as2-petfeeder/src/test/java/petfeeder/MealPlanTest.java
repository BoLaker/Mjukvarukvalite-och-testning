package petfeeder;

import petfeeder.exceptions.MealPlanException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for MealPlan.
 * Verifies the functionality of setting ingredient amounts and calculating energy cost,
 * as well as the equals and hashCode methods,
 * including both normal execution and erroneous inputs.
 */
public class MealPlanTest {
    private MealPlan mp;

    /**
     * Sets up a fresh MealPlan instance before each test.
     */
    @BeforeEach
    public void setUp() {
        mp = new MealPlan();
    }

    /**
     * Tests the setAmtTreats method with normal input.
     * Verifies that the amount of treats is set correctly.
     */
    @Test
    void testSetAmtTreats_Normal() throws Exception {
        mp.setAmtTreats("5");
        assertEquals(5, mp.getAmtTreats());
    }

    /**
     * Tests the setAmtTreats method with non-integer input.
     * Verifies that a MealPlanException is thrown when the input is not a valid integer.
     */
    @Test
    void testSetAmtTreats_NonInt() {
        // Non-integer values should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtTreats("abc"));
    }

    /**
     * Tests the setAmtTreats method with negative input.
     * Verifies that a MealPlanException is thrown when the input is a negative number.
     */
    @Test
    void testSetAmtTreats_Negative() {
        // Negative number should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtTreats("-3"));
    }

    /**
     * Tests the setAmtTreats method with zero input.
     * Verifies that setting the amount of treats to zero is valid and results in 0 treats.
     */
    @Test
    void testSetAmtTreats_Zero() throws Exception {
        // Setting to zero should be valid and result in 0 treats.
        mp.setAmtTreats("0");
        assertEquals(0, mp.getAmtTreats());
    }

    /**
     * Tests the setAmtKibble method with normal input.
     * Verifies that the amount of kibble is set correctly.
     */
    @Test
    void testSetAmtKibble_Normal() throws Exception {
        mp.setAmtKibble("4");
        assertEquals(4, mp.getAmtKibble());
    }

    /**
     * Tests the setAmtKibble method with non-integer input.
     * Verifies that a MealPlanException is thrown when the input is not a valid integer.
     */
    @Test
    void testSetAmtKibble_NonInt() {
        // Non-integer values should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtKibble("abc"));
    }

    /**
     * Tests the setAmtKibble method with negative input.
     * Verifies that a MealPlanException is thrown when the input is a negative number.
     */
    @Test
    void testSetAmtKibble_Negative() {
        // Negative number should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtKibble("-3"));
    }

    /**
     * Tests the setAmtWater method with normal input.
     * Verifies that the amount of water is set correctly.
     */
    @Test
    void testSetAmtWater_Normal() throws Exception {
        mp.setAmtWater("2");
        assertEquals(2, mp.getAmtWater());
    }

    /**
     * Tests the setAmtWater method with non-integer input.
     * Verifies that a MealPlanException is thrown when the input is not a valid integer.
     */
    @Test
    void testSetAmtWater_NonInt() {
        // Non-integer values should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtWater("abc"));
    }

    /**
     * Tests the setAmtWater method with negative input.
     * Verifies that a MealPlanException is thrown when the input is a negative number.
     */
    @Test
    void testSetAmtWater_Negative() {
        // Negative number should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtWater("-3"));
    }

    /**
     * Tests the setAmtWetFood method with normal input.
     * Verifies that the amount of wet food is set correctly.
     */
    @Test
    void testSetAmtWetFood_Normal() throws Exception {
        mp.setAmtWetFood("3");
        assertEquals(3, mp.getAmtWetFood());
    }

    /**
     * Tests the setAmtWetFood method with non-integer input.
     * Verifies that a MealPlanException is thrown when the input is not a valid integer.
     */
    @Test
    void testSetAmtWetFood_NonInt() {
        // Non-integer values should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtWetFood("abc"));
    }

    /**
     * Tests the setAmtWetFood method with negative input.
     * Verifies that a MealPlanException is thrown when the input is a negative number.
     */
    @Test
    void testSetAmtWetFood_Negative() {
        // Negative number should throw MealPlanException
        // as they should not be possible.
        assertThrows(MealPlanException.class, () -> mp.setAmtWetFood("-3"));
    }

    /**
     * Tests the energy cost calculation for a normal meal plan.
     * Verifies that the energy cost is calculated correctly based on the amounts of each ingredient.
     */
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

    /**
     * Tests the equals method for MealPlan with various scenarios.
     * Verifies that two MealPlan objects are considered equal if they have the same attributes,
     * and not equal if they differ in any attribute, or if compared to a different class or null.
     */
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

    /**
     * Tests the equals method for MealPlan when the name attribute differs.
     * Verifies that two MealPlan objects with different names are not considered equal,
     * even if all other attributes are the same.
     */
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

    /**
     * Tests the equals method for MealPlan when the ingredient amounts differ.
     * Verifies that two MealPlan objects with the same name but different ingredient amounts are not considered equal.
     */
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

        // Internally this calls the MealPlan equals method
        assertNotEquals(mp1, mp2);
    }

    /**
     * Tests the equals method for MealPlan when comparing the same object.
     * Verifies that a MealPlan object is considered equal to itself.
     */
    @Test
    void testEquals_SameMeals() {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");

        // Internally this calls the MealPlan equals method
        assertEquals(mp1, mp1);
    }

    /**
     * Tests the equals method for MealPlan when comparing to an object of a different class.
     * Verifies that a MealPlan object is not considered equal to an object of a different type.
     */
    @Test
    void testEquals_DifferentClass() {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");

        String notAMealPlan = "I am not a meal plan";

        // Internally this calls the MealPlan equals method
        assertNotEquals(mp1, notAMealPlan);
    }

    /**
     * Tests the equals method for MealPlan when comparing to null.
     * Verifies that a MealPlan object is not considered equal to null.
     */
    @Test
    void testEquals_Null() {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");

        // Internally this calls the MealPlan equals method
        assertNotEquals(mp1, null);
    }

    /**
     * Tests the hashCode method for MealPlan with valid input.
     * Verifies that two MealPlan objects with the same name have the same hash code.
     */
    @Test
    void getHashCode_Valid() {
        MealPlan mp1 = new MealPlan();
        mp1.setName("Meal A");

        MealPlan mp2 = new MealPlan();
        mp2.setName("Meal A");

        assertEquals(mp1.hashCode(), mp2.hashCode());
    }

    /**
     * Tests the hashCode method for MealPlan with null name.
     * Verifies that a MealPlan object with a null name has a hash code of 1.
     */
    @Test
    void testSetName_Normal() {
        mp.setName("My Meal Plan");
        assertEquals("My Meal Plan", mp.getName());
    }

    /**
     * Tests the setName method for MealPlan with null input.
     * Verifies that setting the name to null does not change the name and it remains an empty string.
     */
    @Test
    void testSetName_Null() {
        mp.setName(null);
        assertEquals("", mp.getName());
    }

    /**
     * Tests the toString method for MealPlan.
     * Verifies that the toString method returns the name of the meal plan.
     */
    @Test
    void testToString() {
        mp.setName("Test Meal");
        assertEquals("Test Meal", mp.toString());
    }
}
