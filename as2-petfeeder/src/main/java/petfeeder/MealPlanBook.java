package petfeeder;

public class MealPlanBook {

    /**
     * Array of meal plans in the pet feeder
     */
    private MealPlan[] mealPlanArray;
    /**
     * Number of meal plans slots
     */
    private final int NUM_MEALPLANS = 4;

    /**
     * Default constructor for a MealPlanBook.
     */
    public MealPlanBook() {
        mealPlanArray = new MealPlan[NUM_MEALPLANS];
    }

    /**
     * Returns the meal plan array.
     *
     * @return MealPlan[]
     */
    public synchronized MealPlan[] getMealPlans() {
        return mealPlanArray;
    }

    /**
     * Returns true if the meal plan is added to the
     * list of meal plans in the MealPlanBook and false
     * otherwise.
     *
     * @param m The meal plan to add.
     * @return boolean
     */
    public synchronized boolean addMealPlan(MealPlan m) {
        //Assume meal plan doesn't exist in the array until 
        //find out otherwise
        boolean exists = false;
        //Check that meal plan doesn't already exist in array
        if(m==null){return false;}
        for (int i = 0; i < mealPlanArray.length; i++) {
            if (m.equals(mealPlanArray[i])) {
                exists = true;
            }
        }
        //Assume meal plan cannot be added until find an empty
        //spot
        boolean added = false;
        //Check for first empty spot in array
        if (!exists) {
            for (int i = 0; i < mealPlanArray.length && !added; i++) {
                if (mealPlanArray[i] == null) {
                    mealPlanArray[i] = m;
                    added = true;
                }
            }
        }
        return added;
    }

    /**
     * Returns the name of the meal plan deleted at the position specified
     * and null if the meal plan does not exist.
     *
     * @param mealPlanToDelete The index of the meal plan to delete.
     * @return String The name of the deleted meal plan.
     */
    public synchronized String deleteMealPlan(int mealPlanToDelete) {
        if(mealPlanToDelete<0 || mealPlanToDelete >= mealPlanArray.length){return null;}
        if (mealPlanArray[mealPlanToDelete] != null) {
            String name = mealPlanArray[mealPlanToDelete].getName();
            mealPlanArray[mealPlanToDelete] = null;
            return name;
        } else {
            return null;
        }
    }

    /**
     * Returns the name of the meal plan edited at the position specified
     * and null if the meal plan does not exist.
     *
     * @param mealPlanToEdit The index of the meal plan to edit.
     * @param newMealPlan    The new meal plan object to replace the old one.
     * @return String The name of the original meal plan.
     */
    public synchronized String editMealPlan(int mealPlanToEdit, MealPlan newMealPlan) {
        if(mealPlanToEdit<0 || mealPlanToEdit >= mealPlanArray.length){return null;}
        if (mealPlanArray[mealPlanToEdit] != null) {
            String name = mealPlanArray[mealPlanToEdit].getName();
            mealPlanArray[mealPlanToEdit] = newMealPlan;
            return name;
        } else {
            return null;
        }
    }
}
