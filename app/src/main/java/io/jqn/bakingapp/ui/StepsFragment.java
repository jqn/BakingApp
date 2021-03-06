package io.jqn.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeStepsAdapter;
import io.jqn.bakingapp.model.Ingredient;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class StepsFragment extends Fragment implements RecipeStepsAdapter.ListItemClickListener {

    private static final String POSITION = "POSITION";
    private static final String RECIPE = "RECIPE";

    @BindView(R.id.ingredients)
    TextView serving;
    // Define a new interface
    OnStepClickListener mOnStepClickListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RetroRecipe mRecipe;
    private String ingText;

    // Mandatory constructor for instantiating the fragment
    public StepsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnStepClickListener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnStepClickListener = null;
    }

    public void setSelectStep(OnStepClickListener onStepClickListener) {
        this.mOnStepClickListener = onStepClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        // inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.steps_fragment, container, false);

        // Inflate the fragment layout and set resources
        ButterKnife.bind(this, rootView);

        mRecyclerView = rootView.findViewById(R.id.recipe_details_list);
        // use linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (savedInstance == null) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(RecipeStepsActivity.RECIPE_BUNDLE)) {
                mRecipe = getActivity().getIntent().getExtras().getParcelable(RecipeStepsActivity.RECIPE_BUNDLE);


            }
        } else {
            mRecipe = savedInstance.getParcelable(RECIPE);
        }

        // specify the adapter
        // and pass in this as the ListItemClickListener to the GreenAdapter constructor
        mAdapter = new RecipeStepsAdapter(mRecipe.getSteps(), this);
        mRecyclerView.setAdapter(mAdapter);

        ingText = "";
        int i = 1;
        for (Ingredient ing : mRecipe.getIngredients()) {
            ingText += "\n" + " " + ing.getQuantity() + " " + ing.getMeasure() + (ing.getQuantity() > 1 ? "'s" : "") + " of " + ing.getIngredient() + "\n";
            i++;
        }

        serving.setText(ingText);


        // Save selected recipe for widget to use
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.preferences_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.recipe_title), mRecipe.getName());
        editor.putString(getString(R.string.recipe_ingredients), ingText);
        editor.commit();
        // let the widget know it needs to update
        Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        getContext().sendBroadcast(widgetIntent);

        return rootView;
    }

    // Override ListItemClickListener's onListItemClick method
    // This callback is invoked when a user clicks on an item in the list
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Timber.v("item index %s", clickedItemIndex);
        mOnStepClickListener.stepSelected(clickedItemIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save list position
        int position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        outState.putInt(POSITION, position);
        outState.putParcelable(RECIPE, mRecipe);
    }

    // OnStepClickListener interface calls a method in the host activity
    public interface OnStepClickListener {
        void stepSelected(int position);
    }

}
