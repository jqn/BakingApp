package io.jqn.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeAdapter;
import io.jqn.bakingapp.adapter.RecipeStepsAdapter;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.model.Step;
import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {
    public static final String RECIPE_BUNDLE = "RECIPE_KEY";
    public static final String RECIPE_STEP = "RECIPE_STEP";

    private List<Step> mSteps;

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

    }

    public void stepSelected(int position) {
        Timber.v("onclick steps %s", mSteps.get(position).getDescription());
        Timber.v("onclick steps %s", mSteps.get(position).getVideoURL());
        Bundle args = new Bundle();
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


}
