package io.jqn.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "RECIPE_KEY";
    private RetroRecipe mRecipe;
    private FragmentManager mFragmentManager;
    //private steps mIngredientAndStepsFragment;
    private TextView mDummyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Use action bar to provide a back arrow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing Fragment manager
        mFragmentManager = getSupportFragmentManager();

        //mIngredientAndStepFragment = new IngredientAndStepFragment();
        //mIngredientAndStepFragment.setSelectStep(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_KEY);
            //mRecipe = intent.getExtras().getParcelable(RECIPE_BUNDLE_KEY);
            Timber.d("Recipe received: %s", mRecipe.toString());
        }

        StepsFragment stepsFragment = new StepsFragment();

        mFragmentManager.beginTransaction()
                .add(R.id.ingredients_and_steps_container, stepsFragment)
                .addToBackStack(null)
                .commit();

    }
}
