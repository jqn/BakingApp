package io.jqn.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetroRecipe implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /* Parcelable Implementation */

    public RetroRecipe() {}

    public RetroRecipe(Parcel in) {
        id = (Integer) in.readValue(Integer.class.getClassLoader());
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = (Integer) in.readValue(Integer.class.getClassLoader());
        image = in.readString();
    }


    @Override
    public String toString() {
        return "{" + "\n" +
                "recipe Id: " + getId() + "\n" +
                "name: " + getName() + "\n" +
                "ingrdients list size: " + getIngredients().size() + "\n" +
                "steps list size: " + getSteps().size() + "\n" +
                "serving: " + getServings() + "\n" +
                "image id path: " + getImage() + "\n" +
                "}";
    }

    @Override
    public int describeContents() {return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(getId());
        dest.writeString(getName());
        dest.writeTypedList(getIngredients());
        dest.writeTypedList(getSteps());
        dest.writeValue(getServings());
        dest.writeString(getImage());
    }

    public static final Parcelable.Creator<RetroRecipe> CREATOR = new Parcelable.Creator<RetroRecipe>() {
        public RetroRecipe createFromParcel(Parcel source) {
            return new RetroRecipe(source);
        }

        public RetroRecipe[] newArray(int size) {
            return new RetroRecipe[size];
        }
    };

}