package edu.utsa.cs3443.calpalhs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display all meals.
 * Provides functionality to view details of each meal, delete them, and favorite them.
 *
 * @author hnw465
 */
public class AllMealsActivity extends AppCompatActivity {
    private LinearLayout mealContainer;
    private Button[] mealButtons;
    private static List<Meal> favoriteMeals = new ArrayList<>();

    /**
     * Called when the activity is first created.
     * Initializes the UI components and sets up the meal buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmeals);

        mealContainer = findViewById(R.id.mealContainer);
        mealButtons = new Button[10];

        // Initialize buttons and set their IDs
        mealButtons[0] = findViewById(R.id.button6);
        mealButtons[1] = findViewById(R.id.button7);
        mealButtons[2] = findViewById(R.id.button8);
        mealButtons[3] = findViewById(R.id.button9);
        mealButtons[4] = findViewById(R.id.button10);
        mealButtons[5] = findViewById(R.id.button11);
        mealButtons[6] = findViewById(R.id.button12);
        mealButtons[7] = findViewById(R.id.button13);
        mealButtons[8] = findViewById(R.id.button14);
        mealButtons[9] = findViewById(R.id.button15);

        Meal[] mealArray = CreateMealActivity.getMealArray();
        int mealCount = CreateMealActivity.getMealCount();

        // Set button texts and click listeners based on the meal data
        for (int i = 0; i < mealCount; i++) {
            Meal meal = mealArray[i];
            mealButtons[i].setText(meal.getMealName());
            final int index = i;
            mealButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AllMealsActivity.this, ViewMealActivity.class);
                    intent.putExtra("mealName", mealArray[index].getMealName());
                    intent.putExtra("calorie", mealArray[index].getCalorie());
                    intent.putExtra("protein", mealArray[index].getProtein());
                    intent.putExtra("carb", mealArray[index].getCarb());
                    intent.putExtra("fat", mealArray[index].getFat());
                    intent.putExtra("sugar", mealArray[index].getSugar());
                    startActivity(intent);
                }
            });
        }

        for (int i = mealCount; i < mealButtons.length; i++) {
            mealButtons[i].setVisibility(View.GONE);
        }

        // Set click listener for the "Favorite Meals" button
        Button favoriteMealsButton = findViewById(R.id.button4);
        favoriteMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllMealsActivity.this, FavoriteMealsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Adds a meal to the favorites list if it is not already present.
     *
     * @param meal The meal to be added to the favorites list.
     */
    public static void addToFavorites(Meal meal) {
        if (!favoriteMeals.contains(meal)) {
            favoriteMeals.add(meal);
        }
    }

    /**
     * Returns the list of favorite meals.
     *
     * @return A list of favorite meals.
     */
    public static List<Meal> getFavoriteMeals() {
        return favoriteMeals;
    }
}