package io.jqn.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {
    public static final String RECIPE_BUNDLE = "RECIPE_KEY";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Use action bar to provide a back arrow
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE)) {
            RetroRecipe mRecipe = intent.getExtras().getParcelable(RECIPE_BUNDLE);
            Timber.d("Recipe received: %s", mRecipe.toString());
            actionBar.setTitle(mRecipe.getName());
        }

        // Create a new recipe ingredients and steps fragment
        StepsFragment mDetailsFragment = new StepsFragment();

        // Initializing Fragment manager
        mFragmentManager = getSupportFragmentManager();
        // create and display the steps and ingredients fragment
        mFragmentManager.beginTransaction()
                .add(R.id.ingredients_and_steps_container, mDetailsFragment)
                .addToBackStack(null)
                .commit();

    }

    public void stepSelected(int position) {
        Timber.v("Step selected %s", position);
        // Create a new step fragment
        StepFragment mStepFragment = new StepFragment();
        // Create and display the step fragment
        mFragmentManager.beginTransaction()
                .replace(R.id.step_main_container, mStepFragment)
                .addToBackStack(null)
                .commit();
    }

}
