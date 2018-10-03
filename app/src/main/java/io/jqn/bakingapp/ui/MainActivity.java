package io.jqn.bakingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeAdapter;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.network.RetrofitClientInstance;
import io.jqn.bakingapp.network.RetrofitRecipeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<RetroRecipe> mRecipes;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_wrapper);
        // Use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        mRecipes = new ArrayList<>();
        mAdapter = new RecipeAdapter(this);
        mAdapter.setRecipeList(mRecipes);
        // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        RetrofitRecipeService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitRecipeService.class);
        Call<List<RetroRecipe>> call = service.getRecipes();
        call.enqueue((new Callback<List<RetroRecipe>>() {
            @Override
            public void onResponse(Call<List<RetroRecipe>> call, Response<List<RetroRecipe>> response) {
                progressDialog.dismiss();
                mRecipes = response.body();
                mAdapter.setRecipeList(mRecipes);
            }

            @Override
            public void onFailure(Call<List<RetroRecipe>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong... Please try later", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void onClick(RetroRecipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        //intent.putExtra(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
        startActivity(intent);

    }


}
