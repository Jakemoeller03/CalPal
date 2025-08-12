package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.R;

/**
 * Activity class for creating a fitness goal in the application.
 * This activity presents several options for the user to select a goal and saves the selected goal choice.
 * @author hnw465
 */
public class CreateGoalActivity extends AppCompatActivity {

    private Button choiceButton1;
    private Button choiceButton2;
    private Button choiceButton3;
    private Button choiceButton4;
    private String goalChoice;
    private static final String PREFS_NAME = "UserPrefs";
    public static final String KEY_GOAL_CHOICE = "goalChoice";

    /**
     * Called when the activity is first created. Initializes the UI components and sets up click listeners
     * for the goal choice buttons.
     * @param savedInstanceState If the activity is being reinitialized after previously being shut down
     * then this bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategoal);

        choiceButton1 = findViewById(R.id.choiceButton1);
        choiceButton2 = findViewById(R.id.choiceButton2);
        choiceButton3 = findViewById(R.id.choiceButton3);
        choiceButton4 = findViewById(R.id.choiceButton4);

        choiceButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalChoice = "Lose Weight";
                saveGoalChoice(goalChoice);
                Intent intent = new Intent(CreateGoalActivity.this, FinalPlanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        choiceButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalChoice = "Gain Weight";
                saveGoalChoice(goalChoice);
                Intent intent = new Intent(CreateGoalActivity.this, FinalPlanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        choiceButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalChoice = "Build Muscle";
                saveGoalChoice(goalChoice);
                Intent intent = new Intent(CreateGoalActivity.this, FinalPlanActivity.class);
                startActivity(intent);
            }
        });

        choiceButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalChoice = "Maintain Weight";
                saveGoalChoice(goalChoice);
                Intent intent = new Intent(CreateGoalActivity.this, FinalPlanActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Saves the selected goal choice to the shared preferences.
     * @param goalChoice The goal choice to be saved.
     */
    private void saveGoalChoice(String goalChoice) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_GOAL_CHOICE, goalChoice);
        editor.apply();
    }
}