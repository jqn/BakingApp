package io.jqn.bakingapp.adapter;

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
import io.jqn.bakingapp.model.Step;
import timber.log.Timber;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeDetailsViewHolder> {
    public List<Step> mSteps;

    // Define an on click handler
    private final ListItemClickListener mOnClickListener;

    // Define and interface to receive onClick messages
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    // provide a suitable constructor and add ListItemClickListener as a parameter
    public RecipeStepsAdapter(List<Step> steps, ListItemClickListener listener) {
        mSteps = steps;
        mOnClickListener = listener;
    }

    // Provide a reference to the views for each data item
    // and implement OnClickListener
    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string
        @BindView(R.id.step_short_description) TextView mShortDescription;
        @BindView(R.id.step_description) TextView mStepDescription;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        // Override on click and pss the clicked item position to mOnClickListener
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


    // create new views
    @Override
    public RecipeStepsAdapter.RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecipeDetailsViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // create new view
        View ingredientView = inflater.inflate(R.layout.steps_item, parent, false);

        viewHolder = new RecipeDetailsViewHolder(ingredientView);

        return viewHolder;
    }

    // replace the contents of the view
    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder holder, int position) {
        Timber.v("steps in fragment %s", mSteps);
        Step step = mSteps.get(position);
        holder.mShortDescription.setText(step.getShortDescription());
        holder.mStepDescription.setText(step.getDescription());
    }

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        Timber.v("steps %s", mSteps.size());

        return mSteps.size();
    }

}
