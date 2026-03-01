package petfeeder;

import petfeeder.exceptions.FoodStockException;

/**
 * Food stock/container for the pet feeder.
 */
public class FoodContainer {
    
    private static int kibble;
    private static int water;
    private static int wetFood;
    private static int treats;
    
    /**
     * Creates a pet feeder food container object and
     * fills each item in the container with 15 units.
     */
    public FoodContainer() {
        setKibble(15);
        setWater(15);
        setWetFood(15);
        setTreats(15);
    }
    
    /**
     * Returns the current number of treat units in
     * the food container.
     * @return int
     */
    public int getTreats() {
        return treats;
    }
    
    /**
     * Sets the number of treat units in the food container
     * to the specified amount.
     * @param treats The amount of treats to set.
     */
    public synchronized void setTreats(int treats) {
        if(treats >= 0) {
            FoodContainer.treats = treats;
        }
    }
    
    /**
     * Add the number of treat units in the container 
     * to the current amount of treat units.
     * @param treats The amount of treats to add (as a string).
     * @throws FoodStockException if the input is not a positive integer.
     */
    public synchronized void addTreats(String treats) throws FoodStockException {
        int amtTreats = 0;
        try {
            amtTreats = Integer.parseInt(treats);
        } catch (NumberFormatException e) {
            throw new FoodStockException("Units of treats must be a positive integer");
        }
        if (amtTreats >= 0) {
            FoodContainer.treats += amtTreats;
        } else {
            throw new FoodStockException("Units of treats must be a positive integer");
        }
    }
    
    /**
     * Returns the current number of kibble units in
     * the food container.
     * @return int
     */
    public int getKibble() {
        return kibble;
    }
    
    /**
     * Sets the number of kibble units in the food container 
     * to the specified amount.
     * @param kibble The amount of kibble to set.
     */
    public synchronized void setKibble(int kibble) {
        if(kibble >= 0) {
            FoodContainer.kibble = kibble;
        }
    }
    
    /**
     * Add the number of kibble units in the container 
     * to the current amount of kibble units.
     * @param kibble The amount of kibble to add (as a string).
     * @throws FoodStockException if the input is not a positive integer.
     */
    public synchronized void addKibble(String kibble) throws FoodStockException {
        int amtKibble = 0;
        try {
            amtKibble = Integer.parseInt(kibble);
        } catch (NumberFormatException e) {
            throw new FoodStockException("Units of kibble must be a positive integer");
        }
        if (amtKibble >= 0) {
            FoodContainer.kibble += amtKibble;
        } else {
            throw new FoodStockException("Units of kibble must be a positive integer");
        }
    }
    
    /**
     * Returns the current number of water units in
     * the food container.
     * @return int
     */
    public int getWater() {
        return water;
    }
    
    /**
     * Sets the number of water units in the food container
     * to the specified amount.
     * @param water The amount of water to set.
     */
    public synchronized void setWater(int water) {
        if(water >= 0) {
            FoodContainer.water = water;
        }
    }
    
    /**
     * Add the number of water units in the container 
     * to the current amount of water units.
     * @param water The amount of water to add (as a string).
     * @throws FoodStockException if the input is not a positive integer.
     */
    public synchronized void addWater(String water) throws FoodStockException {
        int amtWater = 0;
        try {
            amtWater = Integer.parseInt(water);
        } catch (NumberFormatException e) {
            throw new FoodStockException("Units of water must be a positive integer");
        }
        if (amtWater >= 0) {
            FoodContainer.water += amtWater;
        } else {
            throw new FoodStockException("Units of water must be a positive integer");
        }
    }
    
    /**
     * Returns the current number of wet food units in 
     * the food container.
     * @return int
     */
    public int getWetFood() {
        return wetFood;
    }
    
    /**
     * Sets the number of wet food units in the food container
     * to the specified amount.
     * @param wetFood The amount of wet food to set.
     */
    public synchronized void setWetFood(int wetFood) {
        if(wetFood >= 0) {
            FoodContainer.wetFood = wetFood;
        }
    }
    
    /**
     * Add the number of wet food units in the container 
     * to the current amount of wet food units.
     * @param wetFood The amount of wet food to add (as a string).
     * @throws FoodStockException if the input is not a positive integer.
     */
    public synchronized void addWetFood(String wetFood) throws FoodStockException {
        int amtWetFood = 0;
        try {
            amtWetFood = Integer.parseInt(wetFood);
        } catch (NumberFormatException e) {
            throw new FoodStockException("Units of wet food must be a positive integer");
        }
        if (amtWetFood >= 0) {
            FoodContainer.wetFood += amtWetFood;
        } else {
            throw new FoodStockException("Units of wet food must be a positive integer");
        }
    }
    
    /**
     * Returns true if there are enough ingredients to make
     * the meal.
     * @param m The meal plan to check against the food stock.
     * @return boolean
     */
    protected synchronized boolean enoughIngredients(MealPlan m) {
        boolean isEnough = true;
        if(FoodContainer.kibble < m.getAmtKibble()) {
            isEnough = false;
        }
        if(FoodContainer.water < m.getAmtWater()) {
            isEnough = false;
        }
        if(FoodContainer.wetFood < m.getAmtWetFood()) {
            isEnough = false;
        }
        if(FoodContainer.treats < m.getAmtTreats()) {
            isEnough = false;
        }
        return isEnough;
    }
    
    /**
     * Removes the ingredients used to make the specified 
     * meal. Assumes that the user has checked that there
     * are enough ingredients to make.
     * @param m The meal plan to dispense.
     * @return boolean True if ingredients were successfully used.
     */
    public synchronized boolean useIngredients(MealPlan m) {
        if (enoughIngredients(m)) {
            FoodContainer.kibble -= m.getAmtKibble();
            FoodContainer.water -= m.getAmtWater();
            FoodContainer.wetFood -= m.getAmtWetFood();
            FoodContainer.treats -= m.getAmtTreats();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns a string describing the current contents 
     * of the food container.
     * @return String
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Kibble: ");
        buf.append(getKibble());
        buf.append("\n");
        buf.append("Water: ");
        buf.append(getWater());
        buf.append("\n");
        buf.append("Wet Food: ");
        buf.append(getWetFood());
        buf.append("\n");
        buf.append("Treats: ");
        buf.append(getTreats());
        buf.append("\n");
        return buf.toString();
    }
}
