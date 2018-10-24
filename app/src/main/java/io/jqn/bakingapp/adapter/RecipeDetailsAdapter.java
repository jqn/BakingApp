package io.jqn.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.Ingredient;
import timber.log.Timber;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder> {
    public List<Object> mIngredients;

    // Provide a reference to the views for each data item
    public static class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string
        @BindView(R.id.ingredient) TextView mIngredient;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // provide a suitable constructor
    public RecipeDetailsAdapter(List<Object> objects) {
        mIngredients = objects;
    }

    // create new views
    @Override
    public RecipeDetailsAdapter.RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecipeDetailsViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // create new view
        View ingredientView = inflater.inflate(R.layout.recipe_details_item, parent, false);

        viewHolder = new RecipeDetailsViewHolder(ingredientView);

        return viewHolder;
    }

    // replace the contents of the view
    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder holder, int position) {
        // get element for the dataset at this position
        // replace the contents of the view with that element
        RecipeDetailsViewHolder ingredientViewHolder = (RecipeDetailsViewHolder) holder;
        Ingredient ingredient = (Ingredient) mIngredients.get(position);
        if(ingredient != null) {
            ingredientViewHolder.mIngredient.setText("Foo");
        }
    }

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        Timber.v("ingredients size %s", mIngredients.size());
        return 0;
    }


}
