package io.jqn.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import io.jqn.bakingapp.ui.RecipeListActivity;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        String recipeTitle = context.getString(R.string.recipe_title);
        String recipeIngredients = context.getString(R.string.recipe_ingredients);

        // Get the most recently viewed recipe
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);

        recipeTitle = sharedPreferences.getString(context.getString(R.string.recipe_title), recipeTitle);
        recipeIngredients = sharedPreferences.getString(context.getString(R.string.recipe_ingredients), recipeIngredients);

        Timber.v("Recipe ingredients %s", recipeIngredients);
        views.setTextViewText(R.id.widget_title, recipeTitle);
        views.setTextViewText(R.id.widget_ingredients, recipeIngredients);

        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName recipeWidgetProvider = new ComponentName(context.getPackageName(), RecipeWidgetProvider.class.getName());
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(recipeWidgetProvider);
        onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
        super.onReceive(context, intent);
    }
}

