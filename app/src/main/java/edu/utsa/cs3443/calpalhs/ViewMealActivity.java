package edu.utsa.cs3443.calpalhs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.Meal;

/**
 * Activity to view and manage details of a meal.
 * Allows users to view meal details, delete the meal, mark it as favorite, and track its nutritional values.
 *
 * @author hnw465
 */
public class ViewMealActivity extends AppCompatActivity {
    private static final String TAG = "ViewMealActivity";
    private String mealName;

    /**
     * Called when the activity is first created.
     * Initializes the UI components and displays the meal details.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmeal);

        // Initialize UI elements
        TextView mealNameTextView = findViewById(R.id.mealName);
        TextView calorieTextView = findViewById(R.id.calorie);
        TextView proteinTextView = findViewById(R.id.protein);
        TextView carbTextView = findViewById(R.id.carb);
        TextView fatTextView = findViewById(R.id.fat);
        TextView sugarTextView = findViewById(R.id.sugar);

        // Get data from Intent
        mealName = getIntent().getStringExtra("mealName");
        double calorie = getIntent().getDoubleExtra("calorie", 0);
        double protein = getIntent().getDoubleExtra("protein", 0);
        double carb = getIntent().getDoubleExtra("carb", 0);
        double fat = getIntent().getDoubleExtra("fat", 0);
        double sugar = getIntent().getDoubleExtra("sugar", 0);

        // Set data to TextViews
        mealNameTextView.setText("Meal Name: " + mealName);
        calorieTextView.setText("Calories: " + calorie);
        proteinTextView.setText("Protein: " + protein + " g");
        carbTextView.setText("Carbs: " + carb + " g");
        fatTextView.setText("Fat: " + fat + " g");
        sugarTextView.setText("Sugar: " + sugar + " g");
    }

    /**
     * Handles the event when the "Back" button is clicked.
     * Navigates back to the CreateMealActivity.
     *
     * @param view The view that was clicked.
     */
    public void onBackClicked(View view) {
        Intent intent = new Intent(this, CreateMealActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Handles the event when the "Delete" button is clicked.
     * Deletes the meal from SharedPreferences and updates the meal list.
     *
     * @param view The view that was clicked.
     */
    public void onDeleteMealClicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(CreateMealActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Load current meal count
        int mealCount = sharedPreferences.getInt(CreateMealActivity.KEY_MEAL_COUNT, 0);

        // Find the index of the meal to delete
        int mealIndexToDelete = -1;
        for (int i = 0; i < mealCount; i++) {
            String storedMealName = sharedPreferences.getString("mealName_" + i, "");
            if (storedMealName.equals(mealName)) {
                mealIndexToDelete = i;
                break;
            }
        }

        if (mealIndexToDelete >= 0) {
            // Remove the meal
            editor.remove("mealName_" + mealIndexToDelete);
            editor.remove("mealCalorie_" + mealIndexToDelete);
            editor.remove("mealProtein_" + mealIndexToDelete);
            editor.remove("mealCarb_" + mealIndexToDelete);
            editor.remove("mealFat_" + mealIndexToDelete);
            editor.remove("mealSugar_" + mealIndexToDelete);
            editor.remove("mealIsFavorite_" + mealIndexToDelete);

            // Shift remaining meals down
            for (int i = mealIndexToDelete; i < mealCount - 1; i++) {
                String nextMealName = sharedPreferences.getString("mealName_" + (i + 1), "");
                float nextMealCalorie = sharedPreferences.getFloat("mealCalorie_" + (i + 1), 0);
                float nextMealProtein = sharedPreferences.getFloat("mealProtein_" + (i + 1), 0);
                float nextMealCarb = sharedPreferences.getFloat("mealCarb_" + (i + 1), 0);
                float nextMealFat = sharedPreferences.getFloat("mealFat_" + (i + 1), 0);
                float nextMealSugar = sharedPreferences.getFloat("mealSugar_" + (i + 1), 0);
                boolean nextMealIsFavorite = sharedPreferences.getBoolean("mealIsFavorite_" + (i + 1), false);

                editor.putString("mealName_" + i, nextMealName);
                editor.putFloat("mealCalorie_" + i, nextMealCalorie);
                editor.putFloat("mealProtein_" + i, nextMealProtein);
                editor.putFloat("mealCarb_" + i, nextMealCarb);
                editor.putFloat("mealFat_" + i, nextMealFat);
                editor.putFloat("mealSugar_" + i, nextMealSugar);
                editor.putBoolean("mealIsFavorite_" + i, nextMealIsFavorite);
            }

            // Decrement meal count
            editor.putInt(CreateMealActivity.KEY_MEAL_COUNT, mealCount - 1);
            editor.apply();

            Toast.makeText(this, "Meal deleted successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, CreateMealActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Meal not found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the event when the "Favorite" button is clicked.
     * Marks the meal as a favorite and updates the meal list.
     *
     * @param view The view that was clicked.
     */
    public void onFavoriteMealClicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(CreateMealActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Load current meal count
        int mealCount = sharedPreferences.getInt(CreateMealActivity.KEY_MEAL_COUNT, 0);

        // Find the index of the meal to mark as favorite
        int mealIndexToFavorite = -1;
        for (int i = 0; i < mealCount; i++) {
            String storedMealName = sharedPreferences.getString("mealName_" + i, "");
            if (storedMealName.equals(mealName)) {
                mealIndexToFavorite = i;
                break;
            }
        }

        if (mealIndexToFavorite >= 0) {
            // Mark the meal as favorite
            editor.putBoolean("mealIsFavorite_" + mealIndexToFavorite, true);

            // Save the updated data
            editor.apply();

            // Add the meal to the favorites list in CreateMealActivity
            Meal meal = new Meal(mealName,
                    sharedPreferences.getFloat("mealCalorie_" + mealIndexToFavorite, 0),
                    sharedPreferences.getFloat("mealProtein_" + mealIndexToFavorite, 0),
                    sharedPreferences.getFloat("mealCarb_" + mealIndexToFavorite, 0),
                    sharedPreferences.getFloat("mealFat_" + mealIndexToFavorite, 0),
                    sharedPreferences.getFloat("mealSugar_" + mealIndexToFavorite, 0),
                    true);
            CreateMealActivity.addToFavorites(meal);

            // Notify the user that the meal was added to favorites
            Toast.makeText(this, "Meal added to favorites!", Toast.LENGTH_SHORT).show();

            // Navigate back to the all meals list
            Intent intent = new Intent(this, AllMealsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Meal not found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the event when the "Track Meal" button is clicked.
     * Tracks the meal's nutritional values based on the number of servings and updates the current nutritional values.
     *
     * @param view The view that was clicked.
     */
    public void onTrackMealClicked(View view) {
        Log.d(TAG, "Track Meal button clicked");

        // Retrieve the number of servings from the EditText
        EditText servingsEditText = findViewById(R.id.editTextServings);
        String servingsStr = servingsEditText.getText().toString();
        double servings = 1; // Default to 1 if the field is empty or invalid
        try {
            servings = Double.parseDouble(servingsStr);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid number of servings entered", e);
            Toast.makeText(this, "Invalid number of servings", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the meal's nutritional information
        double mealCalories = getIntent().getDoubleExtra("calorie", 0);
        double mealProtein = getIntent().getDoubleExtra("protein", 0);
        double mealCarb = getIntent().getDoubleExtra("carb", 0);
        double mealFat = getIntent().getDoubleExtra("fat", 0);
        double mealSugar = getIntent().getDoubleExtra("sugar", 0);

        // Calculate the total nutritional values based on the servings
        mealCalories *= servings;
        mealProtein *= servings;
        mealCarb *= servings;
        mealFat *= servings;
        mealSugar *= servings;

        // Retrieve the current values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(FinalPlanActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int currentCalories = sharedPreferences.getInt(FinalPlanActivity.KEY_CURRENT_CALORIES, 0);
        currentCalories += (int) mealCalories; // Cast to int for consistency

        // Update macros
        float currentProtein = sharedPreferences.getFloat(FinalPlanActivity.KEY_CURRENT_PROTEIN, 0) + (float) mealProtein;
        float currentCarb = sharedPreferences.getFloat(FinalPlanActivity.KEY_CURRENT_CARB, 0) + (float) mealCarb;
        float currentFat = sharedPreferences.getFloat(FinalPlanActivity.KEY_CURRENT_FAT, 0) + (float) mealFat;
        float currentSugar = sharedPreferences.getFloat(FinalPlanActivity.KEY_CURRENT_SUGAR, 0) + (float) mealSugar;

        // Save the updated data
        editor.putInt(FinalPlanActivity.KEY_CURRENT_CALORIES, currentCalories);
        editor.putFloat(FinalPlanActivity.KEY_CURRENT_PROTEIN, currentProtein);
        editor.putFloat(FinalPlanActivity.KEY_CURRENT_CARB, currentCarb);
        editor.putFloat(FinalPlanActivity.KEY_CURRENT_FAT, currentFat);
        editor.putFloat(FinalPlanActivity.KEY_CURRENT_SUGAR, currentSugar);
        editor.apply();

        // Notify the user
        Toast.makeText(this, "Meal tracked!", Toast.LENGTH_SHORT).show();

        // Start FinalPlanActivity to reflect the updated values
        Intent intent = new Intent(this, FinalPlanActivity.class);
        startActivity(intent);

        finish();
    }
}