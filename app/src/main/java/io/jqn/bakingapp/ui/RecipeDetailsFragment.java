package io.jqn.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class RecipeDetailsFragment extends Fragment {
    private RetroRecipe mRecipe;
    private List<Object> mDetails;

    // Mandatory constructor for instantiating the fragment
    public RecipeDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        //// Inflate the fragment layout and set resources
        View rootView = inflater.inflate(R.layout.recipe_details_fragment, container, false);

        ButterKnife.bind(this, rootView);

        if (getActivity().getIntent().hasExtra("RECIPE_KEY")) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable("RECIPE_KEY");
            Timber.v("RECIPE FRAGMENT %s", mRecipe.toString());
        }

        return rootView;
    }
}
