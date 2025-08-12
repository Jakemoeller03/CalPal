package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.Meal;

/**
 * Activity to create and manage meals.
 * Provides functionality to add meals, save them, and view all meals.
 *
 * @author hnw465
 */
public class CreateMealActivity extends AppCompatActivity {
    public static final String KEY_MEAL_COUNT = "mealCount";
    public static final String PREFS_NAME = "MealPrefs";
    private static final int MAX_MEALS = 10;
    private static Meal[] mealArray = new Meal[MAX_MEALS];
    private static int mealCount = 0;

    /**
     * Called when the activity is first created.
     * Initializes the UI components and loads meals from shared preferences.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeal);

        // Load meals from SharedPreferences
        loadMealsFromPreferences();
    }

    /**
     * Handles the event when the "Save & Add Another" button is clicked.
     * Adds a new meal and starts the AllMealsActivity.
     *
     * @param view The view that was clicked.
     */
    public void onSaveAddAnotherClicked(View view) {
        if (mealCount < MAX_MEALS) {
            if (addMeal()) {
                saveMealsToPreferences();
                Intent intent = new Intent(CreateMealActivity.this, AllMealsActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Cannot add more than 10 meals.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the event when the "Confirm Meal" button is clicked.
     * Adds a new meal, clears the input fields, and notifies the user.
     *
     * @param view The view that was clicked.
     */
    public void onConfirmMealClicked(View view) {
        if (mealCount < MAX_MEALS) {
            if (addMeal()) {
                saveMealsToPreferences();

                // Clear the form fields
                ((EditText)findViewById(R.id.editTextName)).setText("");
                ((EditText)findViewById(R.id.editTextCalories)).setText("");
                ((EditText)findViewById(R.id.editTextProtein)).setText("");
                ((EditText)findViewById(R.id.editTextCarbs)).setText("");
                ((EditText)findViewById(R.id.editTextFat)).setText("");
                ((EditText)findViewById(R.id.editTextSugar)).setText("");

                // Notify the user that the meal was added
                Toast.makeText(this, "Meal added successfully!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cannot add more than 10 meals.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the event when the "View All Meals" button is clicked.
     * Starts the AllMealsActivity.
     *
     * @param view The view that was clicked.
     */
    public void onViewAllMealsClicked(View view) {
        Intent intent = new Intent(CreateMealActivity.this, AllMealsActivity.class);
        startActivity(intent);
    }

    /**
     * Adds a new meal based on user input.
     * Validates the input and updates the meal array, only 10 meals at a time.
     *
     * @return true if the meal was added successfully, false otherwise.
     */
    private boolean addMeal() {
        EditText nameEditText = findViewById(R.id.editTextName);
        EditText calorieEditText = findViewById(R.id.editTextCalories);
        EditText proteinEditText = findViewById(R.id.editTextProtein);
        EditText carbEditText = findViewById(R.id.editTextCarbs);
        EditText fatEditText = findViewById(R.id.editTextFat);
        EditText sugarEditText = findViewById(R.id.editTextSugar);

        String name = nameEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Meal name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Default values if fields are empty
        double calorie = parseDoubleOrDefault(calorieEditText.getText().toString(), 0.0);
        double protein = parseDoubleOrDefault(proteinEditText.getText().toString(), 0.0);
        double carb = parseDoubleOrDefault(carbEditText.getText().toString(), 0.0);
        double fat = parseDoubleOrDefault(fatEditText.getText().toString(), 0.0);
        double sugar = parseDoubleOrDefault(sugarEditText.getText().toString(), 0.0);

        Meal meal = new Meal(name, calorie, protein, carb, fat, sugar, false);
        mealArray[mealCount++] = meal;
        return true;
    }

    /**
     * Parses a string to a double, returning a default value if parsing fails.
     *
     * @param value The string to parse.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed double value or the default value.
     */
    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Saves the meals to shared preferences.
     */
    private void saveMealsToPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_MEAL_COUNT, mealCount);
        for (int i = 0; i < mealCount; i++) {
            editor.putString("mealName_" + i, mealArray[i].getMealName());
            editor.putFloat("mealCalorie_" + i, (float) mealArray[i].getCalorie());
            editor.putFloat("mealProtein_" + i, (float) mealArray[i].getProtein());
            editor.putFloat("mealCarb_" + i, (float) mealArray[i].getCarb());
            editor.putFloat("mealFat_" + i, (float) mealArray[i].getFat());
            editor.putFloat("mealSugar_" + i, (float) mealArray[i].getSugar());
            editor.putBoolean("mealIsFavorite_" + i, mealArray[i].isFavorite());
        }
        editor.apply();
    }

    /**
     * Loads the meals from shared preferences.
     */
    private void loadMealsFromPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mealCount = prefs.getInt(KEY_MEAL_COUNT, 0);
        for (int i = 0; i < mealCount; i++) {
            String mealName = prefs.getString("mealName_" + i, "");
            double mealCalorie = prefs.getFloat("mealCalorie_" + i, 0);
            double mealProtein = prefs.getFloat("mealProtein_" + i, 0);
            double mealCarb = prefs.getFloat("mealCarb_" + i, 0);
            double mealFat = prefs.getFloat("mealFat_" + i, 0);
            double mealSugar = prefs.getFloat("mealSugar_" + i, 0);
            boolean mealIsFavorite = prefs.getBoolean("mealIsFavorite_" + i, false);
            mealArray[i] = new Meal(mealName, mealCalorie, mealProtein, mealCarb, mealFat, mealSugar, mealIsFavorite);
        }
    }

    /**
     * Gets the array of meals.
     *
     * @return The array of meals.
     */
    public static Meal[] getMealArray() {
        return mealArray;
    }

    /**
     * Gets the count of meals.
     *
     * @return The count of meals.
     */
    public static int getMealCount() {
        return mealCount;
    }

    /**
     * Adds a meal to the favorites list.
     *
     * @param meal The meal to add to favorites.
     */
    public static void addToFavorites(Meal meal) {
        for (int i = 0; i < mealCount; i++) {
            if (mealArray[i].getMealName().equals(meal.getMealName())) {
                mealArray[i].setFavorite(true);
                break;
            }
        }
    }
}