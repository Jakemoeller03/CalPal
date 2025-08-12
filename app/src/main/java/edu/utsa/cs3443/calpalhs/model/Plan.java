package edu.utsa.cs3443.calpalhs.model;

import java.util.List;

/**
 * The Class Plan.
 * @author Fxm177
 */
public class Plan {

    /** The user. */
    private User user;

    /** The goal. */
    private Goal goal;

    /** The meal. */
    private Meal meal;

    /**
     * Instantiates a new plan.
     *
     * @param user the user
     * @param goal the goal
     * @param meal the meal
     */
    public Plan(User user, Goal goal,Meal meal) {
        this.user = user;
        this.goal = goal;
        this.meal = meal;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the goal.
     *
     * @return the goal
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Sets the goal.
     *
     * @param goal the new goal
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * Gets the meal.
     *
     * @return the meal
     */
    public Meal getMeal() {
        return meal;
    }

    /**
     * Sets the meal.
     *
     * @param meals the new meal
     */
    public void setMeal(Meal meals) {
        this.meal = meal;
    }

    /**
     * Adds the meal.
     *
     * @param meal the meal
     */
    public void addMeal(Meal meal) {
        meal.add(meal);
    }

    /**
     * Removes the meal.
     *
     * @param meal the meal
     */
    public void removeMeal(Meal meal) {
        meal.remove(meal);
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Plan{" +
                "user=" + user +
                ", goal=" + goal +
                ", meal=" + meal +
                '}';
    }
}
