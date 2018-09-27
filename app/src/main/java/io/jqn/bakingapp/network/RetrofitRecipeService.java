package io.jqn.bakingapp.network;

import java.util.List;

import io.jqn.bakingapp.model.RetroRecipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitRecipeService {
    @GET("baking.json")
    Call<List<RetroRecipe>> getRecipes();
}
