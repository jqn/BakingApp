package io.jqn.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeStepsAdapter;
import io.jqn.bakingapp.model.Ingredient;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.model.Step;
import timber.log.Timber;

public class StepsFragment extends Fragment implements RecipeStepsAdapter.ListItemClickListener {
    @BindView(R.id.ingredients)
    TextView serving;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RetroRecipe mRecipe;
    private List<Object> mDetails;

    private String ingText;

    // Define a new interface
    OnStepClickListener mOnStepClickListener;

    // OnStepClickListener interface calls a method in the host activity
    public interface OnStepClickListener {
        void stepSelected(int position);
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

    public void setSelectStep(OnStepClickListener onStepClickListener) {
        this.mOnStepClickListener = onStepClickListener;
    }

    // Mandatory constructor for instantiating the fragment
    public StepsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        if (getActivity().getIntent().hasExtra("RECIPE_KEY")) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable("RECIPE_KEY");
            Timber.v("RECIPE FRAGMENT %s", mRecipe.toString());
            Timber.v("servings %s", mRecipe.getServings());
            serving.setText(String.format("  %d Person's", mRecipe.getServings()));
        }
        // specify the adapter
        // and pass in this as the ListItemClickListener to the GreenAdapter constructor
        mAdapter = new RecipeStepsAdapter(mRecipe.getSteps(), this);
        mRecyclerView.setAdapter(mAdapter);

        ingText = "";
        int i = 1;
        for (Ingredient ing : mRecipe.getIngredients()) {
            ingText += "\n" +  " " + ing.getQuantity() + " " + ing.getMeasure() + (ing.getQuantity() > 1 ? "'s" : "") + " of " + ing.getIngredient() + "\n";
            i++;
        }

        serving.setText(ingText);

        return rootView;
    }

    // Override ListItemClickListener's onListItemClick method
    // This callback is invoked when a user clicks on an item in the list
    @Override
    public void onListItemClick(int clickedItemIndex) {
        mOnStepClickListener.stepSelected(clickedItemIndex);
    }

}
