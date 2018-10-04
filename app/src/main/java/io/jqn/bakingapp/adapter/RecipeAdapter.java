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
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(RetroRecipe recipe);
    }

    // Constructor
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    // Provide a reference to the views for each data item
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_icon)
        ImageView mRecipeIcon;
        @BindView(R.id.recipe_name)
        TextView mRecipeName;
        @BindView(R.id.recipe_servings)
        TextView mServings;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(dataList.get(position));
        }
    }


    // Create new views
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (null == dataList) return 0;
        return dataList.size();
    }

    public void setRecipeList(List<RetroRecipe> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();

    }
}
