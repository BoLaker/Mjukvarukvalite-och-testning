package petfeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MealPlanBook.
 * Verifies the functionality of adding, deleting, and editing meal plans,
 * including both normal execution and erroneous inputs.
 */
public class MealPlanBookTest {

    private MealPlanBook book;

    /**
     * Sets up a fresh MealPlanBook instance before each test.
     */
    @BeforeEach
    public void setUp() {
        book = new MealPlanBook();
    }

    /**
     * Tests adding a new meal plan to an empty book.
     * Verifies that the addition is successful and the plan is stored at the correct index.
     */
    @Test
    public void testAddMealPlanSuccess() {
        MealPlan plan = new MealPlan();
        plan.setName("Breakfast");

        boolean result = book.addMealPlan(plan);

        assertTrue(result, "It should be possible to add a meal plan to an empty book");
        assertEquals(plan, book.getMealPlans()[0], "The plan should be in the first available slot (index 0)");
    }

    /**
     * Tests adding a duplicate meal plan.
     * Verifies that the system prevents adding the exact same meal plan twice.
     */
    @Test
    public void testAddDuplicateMealPlan() {
        MealPlan plan = new MealPlan();
        plan.setName("Breakfast");

        book.addMealPlan(plan);
        boolean result = book.addMealPlan(plan);

        assertFalse(result, "It should not be possible to add the exact same meal plan twice");
    }

    /**
     * Tests adding a meal plan when the book has reached its maximum capacity.
     * Verifies that the system prevents adding more plans than the allowed maximum.
     */
    @Test
    public void testAddMealPlanWhenFull() {
        for (int i = 0; i < 4; i++) {
            MealPlan plan = new MealPlan();
            plan.setName("Meal " + i);
            book.addMealPlan(plan);
        }

        MealPlan extraPlan = new MealPlan();
        extraPlan.setName("Extra meal");
        boolean result = book.addMealPlan(extraPlan);

        assertFalse(result, "It should not be possible to add a fifth plan when the book is full");
    }

    @Test
    public void testAddNullMealPlan() {
        boolean result = book.addMealPlan(null);
        assertFalse(result, "Adding a null meal plan should return false");
    }

    /**
     * Tests deleting an existing meal plan.
     * Verifies that the correct name is returned and the array slot is set to null.
     */
    @Test
    public void testDeleteMealPlanSuccess() {
        MealPlan plan = new MealPlan();
        plan.setName("Dinner");
        book.addMealPlan(plan);

        String deletedName = book.deleteMealPlan(0);

        assertEquals("Dinner", deletedName, "The method should return the name of the deleted plan");
        assertNull(book.getMealPlans()[0], "The slot in the array should be null after deletion so it can be reused");
    }

    /**
     * Tests deleting a meal plan from an empty slot.
     * Verifies that the method returns null.
     */
    @Test
    public void testDeleteMealPlanEmptySlot() {
        String result = book.deleteMealPlan(0);
        assertNull(result, "Deleting an empty slot should return null");
    }

    /**
     * Tests deleting a meal plan using an out-of-bounds index.
     * Verifies that an ArrayIndexOutOfBoundsException is thrown.
     */
    @Test
    public void testDeleteMealPlanOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            book.deleteMealPlan(10);
        }, "The method crashes completely if the index is out of bounds, lacking bounds-checking!");
    }

    /**
     * Tests editing an existing meal plan.
     * Verifies that the old plan's name is returned and the new plan replaces the old one properly.
     */
    @Test
    public void testEditMealPlanSuccess() {
        MealPlan oldPlan = new MealPlan();
        oldPlan.setName("Old Plan");
        book.addMealPlan(oldPlan);

        MealPlan newPlan = new MealPlan();
        newPlan.setName("New Plan");

        String returnedName = book.editMealPlan(0, newPlan);

        assertEquals("Old Plan", returnedName, "The method should return the name of the old plan");
        assertEquals("New Plan", book.getMealPlans()[0].getName(), "The new plan should retain its name after editing");
    }

    /**
     * Tests editing a meal plan in an empty slot.
     * Verifies that the method returns null.
     */
    @Test
    public void testEditMealPlanEmptySlot() {
        MealPlan newPlan = new MealPlan();
        newPlan.setName("New Plan");

        String result = book.editMealPlan(0, newPlan);
        assertNull(result, "Editing an empty slot should return null");
    }
}