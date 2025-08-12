package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.User;

/**
 * UserActivity class allows users to input and save their personal information.
 * This information is then stored in SharedPreferences. Upon saving, the user is
 * redirected to the CreatePlanActivity.
 *
 * @author hnw465
 */
public class UserActivity extends AppCompatActivity {

    private User user;
    private EditText nameEditText;
    private EditText ageEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_GOAL_CHOICE = "goalChoice";

    /**
     * Called when the activity is first created. Sets up the layout, initializes
     * UI elements, and sets an OnClickListener for the button. Retrieves goalChoice
     * from SharedPreferences and saves user information when the button is clicked.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize EditText fields
        nameEditText = findViewById(R.id.editTextName);
        ageEditText = findViewById(R.id.editTextAge);
        weightEditText = findViewById(R.id.editTextWeight);
        heightEditText = findViewById(R.id.editTextHeight);

        // Retrieve the goalChoice from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String goalChoice = prefs.getString(KEY_GOAL_CHOICE, ""); // Default to empty string if not found

        // Initialize the Button and set a click listener
        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText fields
                String name = nameEditText.getText().toString();
                int age = Integer.parseInt(ageEditText.getText().toString());
                float weight = Float.parseFloat(weightEditText.getText().toString()); // in pounds
                float height = Float.parseFloat(heightEditText.getText().toString()); // in centimeters

                // Save the user information in SharedPreferences
                saveUserInfo(name, age, weight, height);

                Intent intent = new Intent(UserActivity.this, CreatePlanActivity.class);
                startActivity(intent);
                finish(); // Close UserActivity
            }
        });
    }

    /**
     * Saves user information to SharedPreferences.
     *
     * @param username The username of the user
     * @param age The age of the user
     * @param weight The weight of the user in pounds
     * @param height The height of the user in centimeters
     */
    private void saveUserInfo(String username, int age, float weight, float height) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_AGE, age);
        editor.putFloat(KEY_WEIGHT, weight); // saving weight in pounds
        editor.putFloat(KEY_HEIGHT, height); // saving height in centimeters
        editor.apply();
    }

    /**
     * Gets the User object associated with this activity.
     *
     * @return The User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Updates the User object with new information.
     *
     * @param username The new username
     * @param age The new age
     * @param weight The new weight
     * @param height The new height
     */
    public void updateUser(String username, int age, double weight, double height) {
        user.setUsername(username);
        user.setAge(age);
        user.setWeight(weight);
        user.setHeight(height);
    }

    /**
     * Returns a string representation of the UserActivity, including the User object.
     *
     * @return String representation of the UserActivity
     */
    @Override
    public String toString() {
        return "UserActivity{" +
                "user=" + user +
                '}';
    }
}