package io.jqn.bakingapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.jqn.bakingapp.R;
import io.jqn.bakingapp.adapter.RecipeAdapter;
import io.jqn.bakingapp.model.RetroRecipe;
import io.jqn.bakingapp.network.RetrofitClientInstance;
import io.jqn.bakingapp.network.RetrofitRecipeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<RetroRecipe> mRecipes;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_wrapper);
        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        RetrofitRecipeService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitRecipeService.class);
        Call<List<RetroRecipe>> call = service.getRecipes();
        call.enqueue((new Callback<List<RetroRecipe>>() {
            @Override
            public void onResponse(Call<List<RetroRecipe>> call, Response<List<RetroRecipe>> response) {
                progressDialog.dismiss();
                mRecipes = response.body();
                Log.v(TAG, "recipes" + mRecipes);
                // specify an adapter
                mAdapter = new RecipeAdapter(MainActivity.this, mRecipes);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<RetroRecipe>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong... Please try later", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    //private void generateDataList(List<RetroRecipe> recipeList) {
        //mRecyclerView = findViewById(R.id.recipes_wrapper);

        //https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23
    //    recyclerView = findViewById(R.id);
    //    adapter = new CustomAdapter(this,photoList);
    //    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
    //    recyclerView.setLayoutManager(layoutManager);
    //    recyclerView.setAdapter(adapter);
    //}
}
