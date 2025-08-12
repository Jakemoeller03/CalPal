package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity class for managing and creating a user's fitness plan. Provides functionality for
 * clearing user information, checking and creating goals, editing goals, and navigating to
 * different activities based on the user's current goals.
 *
 * @author hnw465
 */
public class CreatePlanActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    public static final String PREFS_NAME = "UserPrefs";
    public static final String KEY_GOAL_CHOICE = "goalChoice";
    private Button editUserDataButton;

    /**
     * Called when the activity is first created. Initializes the UI components and sets up
     * click listeners for the buttons that perform various actions related to the user's plan.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplan);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Button clearDataButton = findViewById(R.id.clearDataButton);
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserInfo();
                Intent intent = new Intent(CreatePlanActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button goalButton = findViewById(R.id.goalbutton);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalChoice = prefs.getString(KEY_GOAL_CHOICE, "");
                if (!goalChoice.isEmpty()) {
                    Toast.makeText(CreatePlanActivity.this, "You Already Have A Goal!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CreatePlanActivity.this, CreateGoalActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button editGoalButton = findViewById(R.id.editGoalButton);
        editGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalChoice = prefs.getString(KEY_GOAL_CHOICE, "");
                if (goalChoice.isEmpty()) {
                    Toast.makeText(CreatePlanActivity.this, "No Current Goals!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CreatePlanActivity.this, CreateGoalActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button goToFinal = findViewById(R.id.goToFinal);
        goToFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalChoice = prefs.getString(KEY_GOAL_CHOICE, "");
                if (goalChoice.isEmpty()) {
                    Toast.makeText(CreatePlanActivity.this, "No Current Goals!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CreatePlanActivity.this, FinalPlanActivity.class);
                    startActivity(intent);
                }
            }
        });

        editUserDataButton = findViewById(R.id.editUserData);
        editUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlanActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Clears all user information from SharedPreferences.
     */
    private void clearUserInfo() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}