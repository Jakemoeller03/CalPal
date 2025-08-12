package edu.utsa.cs3443.calpalhs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.calpalhs.model.Meal;

/**
 * Activity to display favorite meals.
 * Provides functionality to view details of each favorite meal and remove meals from the favorites list.
 *
 * @auther hnw465
 */
public class FavoriteMealsActivity extends AppCompatActivity {
    private LinearLayout mealContainer;
    private Button[] mealButtons;

    /**
     * Called when the activity is first created.
     * Initializes the UI components and sets up the favorite meal buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritemeals);

        mealContainer = findViewById(R.id.mealContainer);
        mealButtons = new Button[5];

        // Initialize buttons and set their IDs, only 5 favorite meals
        mealButtons[0] = findViewById(R.id.button6);
        mealButtons[1] = findViewById(R.id.button7);
        mealButtons[2] = findViewById(R.id.button8);
        mealButtons[3] = findViewById(R.id.button9);
        mealButtons[4] = findViewById(R.id.button10);

        Meal[] mealArray = CreateMealActivity.getMealArray();
        int mealCount = CreateMealActivity.getMealCount();

        int favoriteIndex = 0;
        for (int i = 0; i < mealCount; i++) {
            Meal meal = mealArray[i];
            if (meal.isFavorite() && favoriteIndex < mealButtons.length) {
                mealButtons[favoriteIndex].setText(meal.getMealName());
                final int index = i;
                mealButtons[favoriteIndex].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FavoriteMealsActivity.this, ViewMealActivity.class);
                        intent.putExtra("mealName", mealArray[index].getMealName());
                        intent.putExtra("calorie", mealArray[index].getCalorie());
                        intent.putExtra("protein", mealArray[index].getProtein());
                        intent.putExtra("carb", mealArray[index].getCarb());
                        intent.putExtra("fat", mealArray[index].getFat());
                        intent.putExtra("sugar", mealArray[index].getSugar());
                        startActivity(intent);
                    }
                });
                favoriteIndex++;
            }
        }

        // Hide buttons that are not used
        for (int i = favoriteIndex; i < mealButtons.length; i++) {
            mealButtons[i].setVisibility(View.GONE);
        }

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteMealsActivity.this, AllMealsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Removes a meal from the favorites list based on the meal name.
     * Updates the shared preferences and notifies the user.
     *
     * @param mealName The name of the meal to be removed from favorites.
     */
    public void onRemoveFromFavoritesClicked(String mealName) {
        SharedPreferences sharedPreferences = getSharedPreferences(CreateMealActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Load current meal count
        int mealCount = sharedPreferences.getInt(CreateMealActivity.KEY_MEAL_COUNT, 0);

        // Find the index of the meal to update
        int mealIndexToUpdate = -1;
        for (int i = 0; i < mealCount; i++) {
            String storedMealName = sharedPreferences.getString("mealName_" + i, "");
            if (storedMealName.equals(mealName)) {
                mealIndexToUpdate = i;
                break;
            }
        }

        if (mealIndexToUpdate >= 0) {
            // Mark the meal as not favorite
            editor.putBoolean("mealIsFavorite_" + mealIndexToUpdate, false);

            // Save the updated data
            editor.apply();
            
            Toast.makeText(this, "Meal removed from favorites", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Meal not found", Toast.LENGTH_SHORT).show();
        }
    }
}