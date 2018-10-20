package io.jqn.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.jqn.bakingapp.R;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Object> mSteps;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mIngredient;

        public ViewHolder(TextView v) {
            super(v);
            mIngredient = v;
        }
    }

    // constructor
    public StepAdapter(List<Object> mSteps) {
        mSteps = mSteps;
    }

    // create new views
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crate new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contest of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element fro datataset at this position
        // replace contents of the view with that element
        holder.mIngredient.setText(mSteps[position]);
    }

    // return the size of the dataset
    @Override
    public int getItemCount() {
        return mSteps.length;
    }


}

