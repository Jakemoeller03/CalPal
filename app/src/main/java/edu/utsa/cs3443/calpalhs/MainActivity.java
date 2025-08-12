package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity class that acts as the entry point of the application. It checks if the user has
 * provided complete information and navigates to the appropriate activity based on this check.
 *
 * @author hnw465
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";

    /**
     * Called when the activity is first created. Sets up the layout, initializes UI elements,
     * and sets an OnClickListener for the button. The listener navigates to either CreatePlanActivity
     * or UserActivity based on whether user information is complete.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserInfoComplete()) {
                    // Navigate to CreatePlanActivity if user information is complete
                    Intent intent = new Intent(MainActivity.this, CreatePlanActivity.class);
                    startActivity(intent);
                } else {
                    // Navigate to UserActivity to ask for user information
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Checks if the user has provided complete information by retrieving values from SharedPreferences.
     *
     * @return true if all required user information fields are filled; false otherwise
     */
    private boolean isUserInfoComplete() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString(KEY_USERNAME, "");
        int age = prefs.getInt(KEY_AGE, -1);
        float weight = prefs.getFloat(KEY_WEIGHT, -1);
        float height = prefs.getFloat(KEY_HEIGHT, -1);

        // Check if any of the user information fields are empty
        return !username.isEmpty() && age != -1 && weight != -1 && height != -1;
    }
}