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
    public void setUp(){
        feeder = new PetFeeder();
    }

    private MealPlan createTestMealPlan(String kibble, String treats, String water, String wetFood) throws Exception{
        MealPlan plan = new MealPlan();
        plan.setName("TestMeal");
        plan.setAmtKibble(kibble);
        plan.setAmtTreats(treats);
        plan.setAmtWater(water);
        plan.setAmtWetFood(wetFood);
        return plan;
    }

    /* Test dispensing a valid meal
     */

    @Test
    void testDispenseMealValidInput()throws Exception{
        
        
        MealPlan plan = createTestMealPlan("5","2","1","1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("100", "100", "100", "100");

        int before = feeder.getRemainingEnergyBudget();
        boolean result = feeder.dispenseMeal(0);
        int after = feeder.getRemainingEnergyBudget();
        assertTrue(result);
        assertTrue(after < before);
    }

    @Test
    void testDispenseMealInvalidIndex(){
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->{
            feeder.dispenseMeal(10);
        });
    }

    @Test
    void testDispenseMealInsufficientstock() throws Exception{
        MealPlan plan = createTestMealPlan("20","20","20","20");

        feeder.addMealPlan(plan);
        int before = feeder.getRemainingEnergyBudget();
        boolean result = feeder.dispenseMeal(0);
        int after = feeder.getRemainingEnergyBudget();

        

        assertFalse(result);
        assertEquals(before, after);
    }

    @Test
    void testDispenseMealInsufficientEnergy() throws Exception{
        MealPlan plan = createTestMealPlan("20", "10", "10", "10");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "10", "10", "10");

        int before = feeder.getRemainingEnergyBudget();
        boolean result = feeder.dispenseMeal(0);
        int after = feeder.getRemainingEnergyBudget();

        assertFalse(result);
        assertEquals(before, after);
    }

    @Test
    void testAddMealplanValidInput() throws Exception{
        MealPlan plan = createTestMealPlan("5","2","1","1");

        boolean result = feeder.addMealPlan(plan);
     
        assertTrue(result);
        assertEquals(plan, feeder.getMealPlans()[0]);
    }

    @Test 
    void testAddMealPlanInvalidInput()throws Exception{
        
        boolean result = feeder.addMealPlan(null);
        assertFalse(result);
    }

    @Test 
    void testEditExistingMealPlan()throws Exception{
        MealPlan plan = createTestMealPlan("5","2","1","1");
        MealPlan newplan = createTestMealPlan("10", "5", "6", "4");
        feeder.addMealPlan(plan);

        String result = feeder.editMealPlan(0, newplan);

        assertEquals(plan.getName(),result);
        assertEquals(newplan, feeder.getMealPlans()[0]);
    }

    @Test
    void testEditNoneExistingMealPlan() throws Exception {
        MealPlan newplan = createTestMealPlan("10", "5", "6", "4");
        String result = feeder.editMealPlan(10,newplan);

        assertNull(result);
    }

    @Test
    void testDeleteValidMealplan()throws Exception{
        MealPlan plan = createTestMealPlan("5","2","1","1");
        feeder.addMealPlan(plan);
        String result = feeder.deleteMealPlan(0);

        assertEquals(result, plan.getName());
        assertNull(feeder.getMealPlans()[0]);
    }

    @Test
    void testDeleteNoneExistingMealPlan() throws Exception{
        MealPlan plan = createTestMealPlan("5","2","1","1");
        feeder.addMealPlan(plan);
        String result = feeder.deleteMealPlan(9);

        assertNull(result);
        assertEquals(plan, feeder.getMealPlans()[0]);
    }

    @Test
    void testReplenishFoodValidInput() throws Exception {
    feeder.replenishFood("20", "0", "0", "0");

    assertTrue(feeder.checkFoodStock().contains("Kibble: 35"));
    }

    @Test
    void testReplenishFoodInvalidInput()throws Exception{

        String stockBefore = feeder.checkFoodStock();
        assertThrows(FoodStockException.class, () ->{
            feeder.replenishFood("abc", "0", "0", "0");
        });
        assertEquals(stockBefore, feeder.checkFoodStock());
    }
    @Test
    void testReplenishFoodNegativeInput()throws Exception{

        String stockBefore = feeder.checkFoodStock();
        assertThrows(FoodStockException.class, () ->{
            feeder.replenishFood("-5", "0", "0", "0");
        });
        assertEquals(stockBefore, feeder.checkFoodStock());
    }



}
