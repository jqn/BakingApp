package io.jqn.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.model.Step;

public class RecipeStepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {
    public static final String RECIPE_BUNDLE = "RECIPE_KEY";
    public static final String RECIPE_STEP = "RECIPE_STEPS";

    private List<Step> mSteps;
    private RetroRecipe mRecipe;
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

        if (savedInstanceState == null) {
            if (intent != null && intent.hasExtra(RECIPE_BUNDLE)) {
                RetroRecipe mRecipe = intent.getExtras().getParcelable(RECIPE_BUNDLE);
                actionBar.setTitle(mRecipe.getName());
                mSteps = mRecipe.getSteps();
            }

            // Create a new recipe ingredients and steps fragment
            StepsFragment mDetailsFragment = new StepsFragment();

            // Initializing Fragment manager
            mFragmentManager = getSupportFragmentManager();
            mDetailsFragment.setSelectStep(this);
            // create and display the steps and ingredients fragment
            mFragmentManager.beginTransaction()
                    .add(R.id.steps_container, mDetailsFragment)
                    .addToBackStack(mDetailsFragment.getClass().getName())
                    .commit();
        } else {
            mSteps = savedInstanceState.getParcelable(RECIPE_BUNDLE);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_BUNDLE, mRecipe);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void stepSelected(int position) {
        // Save steps to a bundle
        Bundle args = new Bundle();
        args.putString("SHORT_DESCRIPTION", mSteps.get(position).getShortDescription());
        args.putString("DESCRIPTION", mSteps.get(position).getDescription());
        args.putString("VIDEO", mSteps.get(position).getVideoURL());
        // Create a new step fragment
        StepFragment mStepFragment = new StepFragment();
        mStepFragment.setArguments(args);
        // Create and display the step fragment
        mFragmentManager.beginTransaction()
                .replace(R.id.steps_container, mStepFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 2) {
            getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 2) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

}
