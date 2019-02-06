package io.jqn.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.RecipeWidgetProvider;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.model.Step;
import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {
    public static final String RECIPE_BUNDLE = "RECIPE_KEY";
    public static final String RECIPE_STEP = "RECIPE_STEPS";
    private List<Step> mSteps;
    private RetroRecipe mRecipe;
    private FragmentManager mFragmentManager;
    private StepsFragment mStepsFragment;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        // Use action bar to provide a back arrow
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Use the fragment manager and transaction to add the Fragment to the screen
        mFragmentManager = getSupportFragmentManager();

        // Create a new recipe ingredients and steps fragment
        mStepsFragment = new StepsFragment();
        mStepsFragment.setSelectStep(this);

        // Create a new details fragment
        StepFragment mStepFragment = new StepFragment();


        Intent intent = getIntent();

        // Check if the device is a tablet
        if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            Timber.v("is tablet");
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }


        if (savedInstanceState == null) {
            if (intent != null && intent.hasExtra(RECIPE_BUNDLE)) {
                mRecipe = intent.getExtras().getParcelable(RECIPE_BUNDLE);
                actionBar.setTitle(mRecipe.getName());
                mSteps = mRecipe.getSteps();
            }

            if (mTwoPane) {

                // create and display the steps and ingredients fragment
                mFragmentManager.beginTransaction()
                        .add(R.id.steps_container, mStepsFragment)
                        .commit();

                // Create and display the step fragment
                mFragmentManager.beginTransaction()
                        .add(R.id.details_fragment_container, mStepFragment)
                        .commit();

            } else {
                // create and display the steps and ingredients fragment
                mFragmentManager.beginTransaction()
                        .add(R.id.steps_container, mStepsFragment)
                        .addToBackStack(mStepsFragment.getClass().getName())
                        .commit();

            }


        } else {
            mRecipe = savedInstanceState.getParcelable(RECIPE_BUNDLE);
            Timber.v("mRecipe %s", mRecipe.getName());
            actionBar.setTitle(mRecipe.getName());
            mSteps = mRecipe.getSteps();
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
        // Create a new step fragment
        StepFragment mStepFragment = new StepFragment();
        if (mTwoPane == false) {

            Timber.v("Step position %s", position);
            // Save steps to a bundle
            Bundle args = new Bundle();
            args.putString("SHORT_DESCRIPTION", mSteps.get(position).getShortDescription());
            args.putString("DESCRIPTION", mSteps.get(position).getDescription());
            args.putString("VIDEO", mSteps.get(position).getVideoURL());
            mStepFragment.setArguments(args);


            // Create and display the step fragment
            mFragmentManager.beginTransaction()
                    .replace(R.id.steps_container, mStepFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // Save steps to a bundle
            Bundle args = new Bundle();
            args.putString("SHORT_DESCRIPTION", mSteps.get(position).getShortDescription());
            args.putString("DESCRIPTION", mSteps.get(position).getDescription());
            args.putString("VIDEO", mSteps.get(position).getVideoURL());
            mStepFragment.setArguments(args);

            mFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment_container, mStepFragment)
                    .commit();
        }



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
