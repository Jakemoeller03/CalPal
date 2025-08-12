package edu.utsa.cs3443.calpalhs.model;


/**
 * The Class Meal.
 * @author Fxm177
 */

public class Meal {

    /**
     * Meal, Macros, and favorite boolean
     */

    private String mealName;
    private double calorie;
    private double protein;
    private double carb;
    private double fat;
    private double sugar;
    private boolean isFavorite;

    /**
     * Instantiates a new meal.
     *
     * @param mealName the meal name
     * @param calorie the calorie
     * @param protein the protein
     * @param carb the carb
     * @param fat the fat
     * @param sugar the sugar
     * @param isFavorite the is favorite
     */


    public Meal(String mealName, double calorie, double protein, double carb, double fat, double sugar, boolean isFavorite) {
        this.mealName = mealName;
        this.calorie = calorie;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
        this.sugar = sugar;
        this.isFavorite = isFavorite;
    }
    /**
     * Gets the meal name.
     *
     * @return the meal name
     */
    public String getMealName() {
        return mealName;
    }
    /**
     * Gets the meal name.
     *
     * @return the meal name
     */
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    /**
     * Gets the calorie.
     *
     * @return the calorie
     */
    public double getCalorie() {
        return calorie;
    }
    /**
     * Sets the calorie.
     *
     * @param calorie the new calorie
     */
    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }
    /**
     * Gets the protein.
     *
     * @return the protein
     */
    public double getProtein() {
        return protein;
    }
    /**
     * Sets the protein.
     *
     * @param protein the new protein
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }
    /**
     * Gets the carb.
     *
     * @return the carb
     */
    public double getCarb() {
        return carb;
    }
    /**
     * Sets the carb.
     *
     * @param carb the new carb
     */
    public void setCarb(double carb) {
        this.carb = carb;
    }
    /**
     * Gets the fat.
     *
     * @return the fat
     */
    public double getFat() {
        return fat;
    }
    /**
     * Sets the fat.
     *
     * @param fat the new fat
     */
    public void setFat(double fat) {
        this.fat = fat;
    }
    /**
     * Gets the sugar.
     *
     * @return the sugar
     */
    public double getSugar() {
        return sugar;
    }
    /**
     * Sets the sugar.
     *
     * @param sugar the new sugar
     */
    public void setSugar(double sugar) {
        this.sugar = sugar;
    }
    /**
     * Checks if is favorite.
     *
     * @return true, if is favorite
     */
    public boolean isFavorite() {
        return isFavorite;
    }
    /**
     * Sets the favorite.
     *
     * @param isFavorite the new favorite
     */
    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", calorie=" + calorie +
                ", protein=" + protein +
                ", carb=" + carb +
                ", fat=" + fat +
                ", sugar=" + sugar +
                ", isFavorite=" + isFavorite +
                '}';
    }
    /**
     * Adds the.
     *
     * @param meal the meal
     */
    public void add(Meal meal) {
    }
    /**
     * Removes the.
     *
     * @param meal the meal
     */
    public void remove(Meal meal) {
    }
}
