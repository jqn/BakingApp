package io.jqn.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class StepsFragment extends Fragment {

    private RetroRecipe mRecipe;
    @BindView(R.id.step_title)
    TextView mRecipeName;

    // Mandatory constructor for instantiating the fragment
    public StepsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        // Inflate the fragment layout and set resources
        final View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        if (getActivity().getIntent().hasExtra(RecipeDetailActivity.RECIPE_KEY)) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable(RecipeDetailActivity.RECIPE_KEY);
            Timber.v("RECIPE %s", mRecipe.toString() );
        }

        mRecipeName.setText(mRecipe.getName());

        return rootView;
    }
}
