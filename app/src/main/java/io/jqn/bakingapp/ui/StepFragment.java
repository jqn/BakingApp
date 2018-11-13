package io.jqn.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.jqn.bakingapp.R;
import timber.log.Timber;

public class StepFragment  extends Fragment {

    // Mandatory empty constructor
    public StepFragment() {

    }

    // Inflate fragment layout file
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        // Inflate the Step fragment layout
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        // Get a reference to the content views in the fragment layout
        TextView mTextView = rootView.findViewById(R.id.step_description);

        // Set the step content
        mTextView.setText("Hello");
        Timber.v("Setting step fragment text");

        return rootView;
    }

}
