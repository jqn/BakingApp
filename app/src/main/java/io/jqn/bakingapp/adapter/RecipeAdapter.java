package io.jqn.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<RetroRecipe> dataList;
    private Context context;

    // Provide a reference to the views for each data item
    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_icon)
        ImageView mRecipeIcon;
        @BindView(R.id.recipe_name)
        TextView mRecipeName;
        @BindView(R.id.recipe_servings)
        TextView mServings;

        RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    // Constructor
    public RecipeAdapter(Context context, List<RetroRecipe> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Create new views
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RetroRecipe recipe = dataList.get(position);
        String name = recipe.getName();
        String servings = String.valueOf(recipe.getServings());

        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        holder.mRecipeName.setText(name);
        holder.mServings.setText(servings);

    }

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
