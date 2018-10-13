package io.jqn.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import timber.log.Timber;

public class IngredientsStepsFragment extends Fragment {
    // Mandatory constructor for instantiating the fragment
    public IngredientsStepsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);

        //// Inflate the fragment layout and set resources
        //View rootView = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        //
        //ButterKnife.bind(this, rootView);
        //
        ////if (intent().hasExtra("RECIPE_BUNDLE")) {
        ////    mRecipe = getIntent().getExtras().getParcelable("RECIPE_BUNDLE");
        ////    Timber.v("RECIPE %s", mRecipe.toString() );
        ////
        ////}
        //if (getActivity().getIntent().hasExtra("RECIPE_BUNDLE")) {
        //
        //}
        //// Get reference to the TextView in the fragment layout
        //
        //// Set the text source to display
        //
        //// Set the source to display
        return rootView;
    }
}
