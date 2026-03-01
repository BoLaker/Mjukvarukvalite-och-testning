package petfeeder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedingSchedulerTest {
    
    private FeedingScheduler scheduler;
    private PetFeeder feeder;

    /**
     * Sets up a fresh PetFeeder and FeedingScheduler instance before each test.
     */
    @BeforeEach
    void setUp(){
        feeder = new PetFeeder();
        scheduler = new FeedingScheduler(feeder);
    }

    /**
     * Helper method to quickly create a test meal plan with specified ingredient amounts.
     * * @param kibble Amount of kibble as a String
     * @param treats Amount of treats as a String
     * @param water Amount of water as a String
     * @param wetFood Amount of wet food as a String
     * @return A configured MealPlan object
     * @throws Exception If input strings cannot be parsed into valid ingredient amounts
     */
    private MealPlan createTestMealPlan(String kibble, String treats, String water, String wetFood) throws Exception{
        MealPlan plan = new MealPlan();
        plan.setName("TestMeal");
        plan.setAmtKibble(kibble);
        plan.setAmtTreats(treats);
        plan.setAmtWater(water);
        plan.setAmtWetFood(wetFood);
        return plan;
    }

    /**
     * Tests that scheduling a recurring feeding successfully starts an active schedule.
     */
    @Test
    void testScheduleRecurringFeedingStartsSchedule()throws Exception{
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");

        scheduler.scheduleRecurringFeeding(0, 1);

        assertTrue(scheduler.hasActiveSchedule());
    }

    /**
     * Tests that attempting to schedule a feeding with an invalid meal plan index
     * does not throw an unexpected exception and is handled gracefully.
     */
    @Test
    void testScheduleRecurringFeedingException(){
        assertDoesNotThrow(() ->
    scheduler.scheduleRecurringFeeding(10, 1));
        
    }

    /**
     * Tests that scheduling a new feeding while one is already active
     * successfully replaces the old task and maintains an active state.
     */
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

    /**
     * Tests that stopping an active schedule successfully cancels the task.
     */
    @Test
    void testStopActiveSchedule()throws Exception{
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");

        scheduler.scheduleRecurringFeeding(0, 1);

        scheduler.stop();
        assertFalse(scheduler.hasActiveSchedule());
    }

    /**
     * Tests shutting down the executor service completely.
     */
    @Test
    void testShutdown() {
        scheduler.shutdown();
    }

    /**
     * Tests that calling stop when there is no active schedule does not cause errors.
     */
    @Test
    void testStopNoActiveSchedule() {
        scheduler.stop();
    }

    /**
     * Tests the background runnable task simulating a successful meal dispensing event.
     * The thread is paused to allow the scheduled task enough time to execute.
     */
    @Test
    void testRunnableSuccess() throws Exception {
        MealPlan plan = createTestMealPlan("1", "1", "1", "1");
        feeder.addMealPlan(plan);
        feeder.replenishFood("20", "20", "20", "20");


        scheduler.scheduleRecurringFeeding(0, 1);

        Thread.sleep(1500);
        scheduler.stop();
    }

    /**
     * Tests the background runnable task simulating a failed meal dispensing event
     * (e.g., due to insufficient ingredients or energy).
     * The thread is paused to allow the scheduled task enough time to execute and fail gracefully.
     */
    @Test
    void testRunnableFailDispense() throws Exception {
        MealPlan plan = createTestMealPlan("100", "100", "100", "100");
        feeder.addMealPlan(plan);
        feeder.replenishFood("10", "10", "10", "10");

        scheduler.scheduleRecurringFeeding(0, 1);

        Thread.sleep(1500);
        scheduler.stop();
    }

    /**
     * Tests the exception handling within the background runnable task.
     * By injecting a null PetFeeder, an exception is forced during the execution,
     * ensuring the catch block in the run() method is triggered.
     */
    @Test
    void testRunnableException() throws Exception {
        FeedingScheduler badScheduler = new FeedingScheduler(null);
        badScheduler.scheduleRecurringFeeding(0, 1);

        Thread.sleep(1500);
        badScheduler.stop();
    }


    
}
