package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.Meal;

/**
 * Activity class for displaying and managing the user's final fitness plan. It allows users to
 * view and update their caloric and macro intake, reset their intake, add meals, and navigate to
 * other activities.
 *
 * @author hnw465
 */
public class FinalPlanActivity extends AppCompatActivity {

    public static final String KEY_CURRENT_PROTEIN = "currentProtein";
    public static final String KEY_CURRENT_CARB = "currentCarb";
    public static final String KEY_CURRENT_FAT = "currentFat";
    public static final String KEY_CURRENT_SUGAR = "currentSugar";
    private static final String TAG = "FinalPlanActivity";
    private ProgressBar progressBar;
    private TextView progressText;
    private TextView userInfoTextView;
    private TextView goalTextView;
    private TextView mealsLayout;
    private TextView calorieCounterTextView;
    private Button addMealButton;
    private Button resetCaloricIntakeButton;
    private Button editUserDataButton;

    public static final String PREFS_NAME = "UserPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_AGE = "age";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_GOAL_CHOICE = "goalChoice";
    public static final String KEY_CURRENT_CALORIES = "currentCalories";
    private TextView proteinCounterTextView;
    private TextView carbCounterTextView;
    private TextView fatCounterTextView;
    private TextView sugarCounterTextView;

    private int currentCalories = 0;
    private int currentProtein = 0;
    private int currentSugar = 0;
    private int currentFat = 0;
    private int currentCarbs = 0;
    private int goalCalories = 2000; // Default value, will be updated

    /**
     * Called when the activity is first created. Initializes the UI elements, retrieves user data
     * from SharedPreferences, and sets up click listeners for the various buttons and interactions.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_plan);

        userInfoTextView = findViewById(R.id.userInfoTextView);
        goalTextView = findViewById(R.id.goalTextView);
        mealsLayout = findViewById(R.id.mealsLayout);
        calorieCounterTextView = findViewById(R.id.textView);
        addMealButton = findViewById(R.id.addMealButton);
        resetCaloricIntakeButton = findViewById(R.id.button2);
        editUserDataButton = findViewById(R.id.editUserData);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentCalories = prefs.getInt(KEY_CURRENT_CALORIES, 0);
        float currentProtein = prefs.getFloat(KEY_CURRENT_PROTEIN, 0);
        float currentCarb = prefs.getFloat(KEY_CURRENT_CARB, 0);
        float currentFat = prefs.getFloat(KEY_CURRENT_FAT, 0);
        float currentSugar = prefs.getFloat(KEY_CURRENT_SUGAR, 0);
        String userInfo = "Name: " + prefs.getString(KEY_USERNAME, "") + "\n" +
                "Age: " + prefs.getInt(KEY_AGE, 0) + "\n" +
                "Weight: " + prefs.getFloat(KEY_WEIGHT, 0) + " lbs\n" +
                "Height: " + prefs.getFloat(KEY_HEIGHT, 0) + " cm";
        String selectedGoal = prefs.getString(KEY_GOAL_CHOICE, "");

        proteinCounterTextView = findViewById(R.id.proteinCounterTextView);
        carbCounterTextView = findViewById(R.id.carbCounterTextView);
        fatCounterTextView = findViewById(R.id.fatCounterTextView);
        sugarCounterTextView = findViewById(R.id.sugarCounterTextView);

        // Display the data
        userInfoTextView.setText(userInfo);
        goalTextView.setText(selectedGoal);

        // Retrieve user data
        int age = prefs.getInt(KEY_AGE, 0);
        float weightInPounds = prefs.getFloat(KEY_WEIGHT, 0);
        float heightInCm = prefs.getFloat(KEY_HEIGHT, 0);

        // Convert weight from pounds to kilograms
        double weightInKg = weightInPounds * 0.453592;

        // Calculate BMR
        double bmr = 88.36 + (13.397 * weightInKg) + (4.799 * heightInCm) - (5.677 * age);

        // Adjust for activity level (1.375 in this case)
        goalCalories = (int) (bmr * 1.375);

        // Adjust goalCalories based on the user's goal choice
        switch (selectedGoal) {
            case "Lose Weight":
                goalCalories -= 500;
                break;
            case "Gain Weight":
                goalCalories += 500;
                break;
            case "Build Muscle":
                goalCalories = (int) (weightInPounds * 20);
                break;
            case "Maintain Weight":
                goalCalories = goalCalories;
                break;
            default:
                // Handle unexpected values if needed
                break;
        }

        // Update calorie counter TextView
        updateCalorieCounter();
        updateMacroCounters(currentProtein, currentCarb, currentFat, currentSugar);

        // Retrieve meal data from intent
        Meal[] meals = (Meal[]) getIntent().getSerializableExtra("meals");
        if (meals == null) {
            meals = new Meal[0]; // Handle case where no meals are passed
        }

        // Display meal names and make them clickable
        StringBuilder mealsList = new StringBuilder();
        for (Meal meal : meals) {
            if (meal != null) {
                mealsList.append(meal.getMealName()).append("\n");
            }
        }
        mealsLayout.setText(mealsList.toString());

        Meal[] finalMeals = meals;
        mealsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Meal meal : finalMeals) {
                    if (meal != null) {
                        Toast.makeText(FinalPlanActivity.this, meal.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalPlanActivity.this, CreateMealActivity.class);
                startActivity(intent);
            }
        });

        resetCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCaloricIntake();
            }
        });

        editUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalPlanActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Updates the calorie counter TextView with the current and goal calorie values and updates
     * the progress bar accordingly.
     */
    public void updateCalorieCounter() {
        String calorieText = currentCalories + "/" + goalCalories + " Cal";
        calorieCounterTextView.setText(calorieText);
        updateProgressBar();
    }

    /**
     * Updates the macro counters TextViews with the provided values for protein, carbs, fat, and sugar.
     *
     * @param protein The amount of protein in grams
     * @param carb The amount of carbohydrates in grams
     * @param fat The amount of fat in grams
     * @param sugar The amount of sugar in grams
     */
    private void updateMacroCounters(float protein, float carb, float fat, float sugar) {
        proteinCounterTextView.setText("Protein: " + protein + " g");
        carbCounterTextView.setText("Carbs: " + carb + " g");
        fatCounterTextView.setText("Fat: " + fat + " g");
        sugarCounterTextView.setText("Sugar: " + sugar + " g");
    }

    /**
     * Updates the progress bar to reflect the percentage of the goal calories achieved.
     */
    private void updateProgressBar() {
        int progress = (currentCalories * 100) / goalCalories;
        progressBar.setProgress(progress);
        progressText.setText(progress + "%");
    }

    /**
     * Updates the current calorie count and refreshes the UI.
     *
     * @param calories The number of calories to add to the current total
     */
    public void setCurrentCalories(int calories) {
        currentCalories += calories; // Update current calories
        updateCalorieCounter();
        saveCurrentCalories();
    }

    /**
     * Saves the current calorie count to SharedPreferences.
     */
    private void saveCurrentCalories() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CURRENT_CALORIES, currentCalories);
        editor.apply();
    }

    /**
     * Resets the current calorie and macro counts to zero and updates the UI.
     */
    private void resetCaloricIntake() {
        // Reset current calories and macros
        currentCalories = 0;
        currentProtein = 0;
        currentCarbs = 0;
        currentFat = 0;
        currentSugar = 0;

        // Update UI
        updateCalorieCounter();
        updateMacroCounters(currentProtein, currentCarbs, currentFat, currentSugar);

        // Save updated values
        saveCurrentCalories();
        saveCurrentMacros();

        //Toast.makeText(this, "Caloric intake and macros reset to 0", Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves the current macro values to SharedPreferences.
     */
    private void saveCurrentMacros() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(KEY_CURRENT_PROTEIN, currentProtein);
        editor.putFloat(KEY_CURRENT_CARB, currentCarbs);
        editor.putFloat(KEY_CURRENT_FAT, currentFat);
        editor.putFloat(KEY_CURRENT_SUGAR, currentSugar);
        editor.apply();
    }

    /**
     * Refreshes the calorie counter when the activity resumes by retrieving updated calorie values
     * from SharedPreferences.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the calorie counter when the activity resumes
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentCalories = prefs.getInt(KEY_CURRENT_CALORIES, 0); // Retrieve updated calories
        updateCalorieCounter();
    }
}