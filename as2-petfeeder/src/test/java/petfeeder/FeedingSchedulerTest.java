package petfeeder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedingSchedulerTest {
    
    private FeedingScheduler scheduler;
    private PetFeeder feeder;
    @BeforeEach
    void setUp(){
        feeder = new PetFeeder();
        scheduler = new FeedingScheduler(feeder);
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

    @Test
    void testScheduleRecurringFeedingStartsSchedule()throws Exception{
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");

        scheduler.scheduleRecurringFeeding(0, 1);

        assertTrue(scheduler.hasActiveSchedule());
    }
    @Test
    void testScheduleRecurringFeedingException(){
        assertDoesNotThrow(() ->
    scheduler.scheduleRecurringFeeding(10, 1));
        
    }



    @Test
    void testScheduleRecurringFeedingExistingSchedule()throws Exception{
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");

        scheduler.scheduleRecurringFeeding(0, 1);

        boolean firstState = scheduler.hasActiveSchedule();
        scheduler.scheduleRecurringFeeding(0, 1);
        boolean secondState = scheduler.hasActiveSchedule();

        assertTrue(firstState);
        assertTrue(secondState);
    }


    @Test
    void testStopActiveSchedule()throws Exception{
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");

        scheduler.scheduleRecurringFeeding(0, 1);

        scheduler.stop();
        assertFalse(scheduler.hasActiveSchedule());
    }
    
}
