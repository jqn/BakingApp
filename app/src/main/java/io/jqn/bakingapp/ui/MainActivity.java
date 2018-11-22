package io.jqn.bakingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.BuildConfig;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeAdapter;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.network.RetrofitClientInstance;
import io.jqn.bakingapp.network.RetrofitRecipeService;
import io.jqn.bakingapp.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recipes_wrapper)
    RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    private List<RetroRecipe> mRecipes;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Baking with Panache");

        // Planting Timber
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        if (findViewById(R.id.recipes_wrapper) != null) {
            // Bind views with ButterKnife
            ButterKnife.bind(this);

            // Use a linear layout manager
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);

            // Add a divider for better readability
            mRecyclerView.addItemDecoration(new ListDivider(mRecyclerView.getContext()));

        } else {
//            mRecyclerView = (RecyclerView) findViewById(R.id.recipes_wrapper);
//            GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        }


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Initialize the recipes adapter with an empty array
        mRecipes = new ArrayList<>();
        mAdapter = new RecipeAdapter(this);
        mAdapter.setRecipeList(mRecipes);

        // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        boolean isConnected = NetworkUtils.checkNetworkStatus(getApplicationContext());

        if (isConnected) {
            // Make a call to collect the recipes
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
        } else {
            Toast.makeText(MainActivity.this, getText(R.string.network_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(RetroRecipe recipe) {
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(RecipeStepsActivity.RECIPE_BUNDLE, recipe);
        startActivity(intent);

    }

}
