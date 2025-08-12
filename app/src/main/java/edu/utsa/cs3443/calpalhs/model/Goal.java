package edu.utsa.cs3443.calpalhs.model;

/**
 * The Class Goal.
 * Fxm177
 */
public class Goal {

    /** The lose weight. */
    private boolean loseWeight;

    /** The gain weight. */
    private boolean gainWeight;

    /** The gain muscle. */
    private boolean gainMuscle;

    /**
     * Instantiates a new goal.
     *
     * @param loseWeight the lose weight
     * @param gainWeight the gain weight
     * @param gainMuscle the gain muscle
     */
    public Goal(boolean loseWeight, boolean gainWeight, boolean gainMuscle) {
        this.loseWeight = loseWeight;
        this.gainWeight = gainWeight;
        this.gainMuscle = gainMuscle;
    }

    /**
     * Checks if is lose weight.
     *
     * @return true, if is lose weight
     */
    public boolean isLoseWeight() {
        return loseWeight;
    }

    /**
     * Sets the lose weight.
     *
     * @param loseWeight the new lose weight
     */
    public void setLoseWeight(boolean loseWeight) {
        this.loseWeight = loseWeight;
    }

    /**
     * Checks if is gain weight.
     *
     * @return true, if is gain weight
     */
    public boolean isGainWeight() {
        return gainWeight;
    }

    /**
     * Sets the gain weight.
     *
     * @param gainWeight the new gain weight
     */
    public void setGainWeight(boolean gainWeight) {
        this.gainWeight = gainWeight;
    }

    /**
     * Checks if is gain muscle.
     *
     * @return true, if is gain muscle
     */
    public boolean isGainMuscle() {
        return gainMuscle;
    }

    /**
     * Sets the gain muscle.
     *
     * @param gainMuscle the new gain muscle
     */
    public void setGainMuscle(boolean gainMuscle) {
        this.gainMuscle = gainMuscle;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Goal{" +
                "loseWeight=" + loseWeight +
                ", gainWeight=" + gainWeight +
                ", gainMuscle=" + gainMuscle +
                '}';
    }
}