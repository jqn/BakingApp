package io.jqn.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeDetailsAdapter;
import io.jqn.bakingapp.model.Ingredient;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class RecipeDetailsFragment extends Fragment {
    @BindView(R.id.detail_serving)
    TextView serving;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RetroRecipe mRecipe;
    private List<Object> mDetails;

    private String ingText;

    // Mandatory constructor for instantiating the fragment
    public RecipeDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        // inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recipe_details_fragment, container, false);

        // Inflate the fragment layout and set resources
        ButterKnife.bind(this, rootView);

        mRecyclerView = rootView.findViewById(R.id.recipe_details_list);
        // use linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Initialize the list - otherwise this will throw a null reference error.
        mDetails = new ArrayList<>();
        // specify an adapter
        mAdapter = new RecipeDetailsAdapter(mDetails);
        mRecyclerView.setAdapter(mAdapter);


        if (getActivity().getIntent().hasExtra("RECIPE_KEY")) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable("RECIPE_KEY");
            Timber.v("RECIPE FRAGMENT %s", mRecipe.toString());
            Timber.v("servings %s", mRecipe.getServings());
            serving.setText(String.format("  %d Person's", mRecipe.getServings()));
        }

        ingText = "";
        int i = 1;
        for (Ingredient ing : mRecipe.getIngredients()) {
            ingText += "\n" + i + "- " + ing.getQuantity() + " " + ing.getMeasure() + (ing.getQuantity() > 1 ? "'s" : "") + " of " + ing.getIngredient() + "\n";
            i++;
        }
        Timber.v("quatity %s", ingText);
        serving.setText(ingText);

        return rootView;
    }
}
