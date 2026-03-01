package petfeeder;

import petfeeder.exceptions.FoodStockException;

public class PetFeeder {
    
    /** Array of meal plans in pet feeder */
    private static MealPlanBook mealPlanBook;
    /** Food container (inventory) of the pet feeder */
    private static FoodContainer foodContainer;
    /** Simple total energy limit (in the same abstract energy points as meal energyCost). */
    private static final int ENERGY_LIMIT = 500;
    /** Energy consumed so far since the feeder was started. */
    private int energyConsumedSoFar;
    
    /**
     * Constructor for the pet feeder.
     */
    public PetFeeder() {
        mealPlanBook = new MealPlanBook();
        foodContainer = new FoodContainer();
        this.energyConsumedSoFar = 0;
    }
    
    /**
     * Returns true if the meal plan is added to the
     * list of meal plans in the PetFeeder and false
     * otherwise.
     * @param m The meal plan to add.
     * @return boolean
     */
    public boolean addMealPlan(MealPlan m) {
        return mealPlanBook.addMealPlan(m);
    }
    
    /**
     * Returns the name of the successfully deleted meal plan
     * or null if the meal plan cannot be deleted.
     * @param mealPlanToDelete The index of the meal plan to delete.
     * @return String
     */
    public String deleteMealPlan(int mealPlanToDelete) {
        return mealPlanBook.deleteMealPlan(mealPlanToDelete);
    }
    
    /**
     * Returns the name of the successfully edited meal plan
     * or null if the meal plan cannot be edited.
     * @param mealPlanToEdit The index of the meal plan to edit.
     * @param m The new meal plan object.
     * @return String
     */
    public String editMealPlan(int mealPlanToEdit, MealPlan m) {
        return mealPlanBook.editMealPlan(mealPlanToEdit, m);
    }
    
    /**
     * Returns true if food stock was successfully replenished.
     * @param amtKibble The amount of kibble to add.
     * @param amtWater The amount of water to add.
     * @param amtWetFood The amount of wet food to add.
     * @param amtTreats The amount of treats to add.
     * @throws FoodStockException if inputs are invalid.
     */
    public synchronized void replenishFood(String amtKibble, String amtWater, String amtWetFood, String amtTreats) throws FoodStockException {
        foodContainer.addKibble(amtKibble);
        foodContainer.addWater(amtWater);
        // This will call the method with the seeded bug
        foodContainer.addWetFood(amtWetFood);
        foodContainer.addTreats(amtTreats);
    }
    
    /**
     * Returns the food stock status of the pet feeder.
     * @return String
     */
    public synchronized String checkFoodStock() {
        return foodContainer.toString();
    }
    
    /**
     * Attempts to dispense the selected meal plan.
     * Returns true if the meal was successfully dispensed and false otherwise.
     * @param mealPlanToPurchase The index of the meal plan selected by the user.
     * @return boolean True if dispensing succeeded.
     */
    public synchronized boolean dispenseMeal(int mealPlanToPurchase) {
        boolean dispensed = false;
        MealPlan[] plans = getMealPlans();

        if (plans[mealPlanToPurchase] == null) {
            dispensed = false;
        } else {
            MealPlan selected = plans[mealPlanToPurchase];
            int mealEnergy = selected.getEnergyCost();
            int remainingEnergyBudget = ENERGY_LIMIT - energyConsumedSoFar;

            // Require enough remaining total energy budget.
            if (mealEnergy <= remainingEnergyBudget) {
                if (foodContainer.useIngredients(selected)) {
                    energyConsumedSoFar += mealEnergy;
                    dispensed = true;
                } else {
                    dispensed = false;
                }
            } else {
                dispensed = false;
            }
        }

        return dispensed;
    }

    /**
     * Returns the list of MealPlans in the MealPlanBook.
     * @return MealPlan[]
     */
    public synchronized MealPlan[] getMealPlans() {
        return mealPlanBook.getMealPlans();
    }

    /**
     * Returns the configured total energy limit for this feeder.
     * @return int
     */
    public int getEnergyLimit() {
        return ENERGY_LIMIT;
    }

    /**
     * Returns the remaining energy budget (limit minus energy consumed so far).
     * @return int
     */
    public int getRemainingEnergyBudget() {
        return ENERGY_LIMIT - energyConsumedSoFar;
    }
}
