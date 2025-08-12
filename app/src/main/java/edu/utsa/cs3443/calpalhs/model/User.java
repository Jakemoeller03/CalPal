package edu.utsa.cs3443.calpalhs.model;

/**
 * The Class User.
 * @author Fxm177
 */
public class User {

    /** The username. */
    private String username;

    /** The age. */
    private int age;

    /** The weight. */
    private double weight;

    /** The height. */
    private double height;

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param age the age
     * @param weight the weight
     * @param height the height
     */
    public User(String username, int age, double weight, double height) {
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param age the new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
