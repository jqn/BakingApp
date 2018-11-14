package io.jqn.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.model.Step;
import timber.log.Timber;

public class StepFragment  extends Fragment {
    private RetroRecipe mRecipe;
    private Step mStep;
    private List<Step> mStepList;
    private String mDescription;

    // Mandatory empty constructor
    public StepFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Timber.v("Bundle arg description %s", getArguments().getString("DESCRIPTION"));
            Timber.v("Bundle arg video %s", getArguments().getString("VIDEO"));
            mDescription = getArguments().getString("DESCRIPTION");
        }
    }

    // Inflate fragment layout file
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        // Inflate the Step fragment layout
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        // Get a reference to the content views in the fragment layout
        TextView mTextView = rootView.findViewById(R.id.step_description);

        // Set the step content
        mTextView.setText(mDescription);
        Timber.v("Setting step fragment text");

        if (getActivity().getIntent().hasExtra("RECIPE_KEY")) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable("RECIPE_KEY");
        }

        return rootView;
    }

}
