package com.example.cookingrecipe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.Activity.SearchActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>  {
    private Context context;
    private List<Recipe> recipeList;
    private ItemListener itemListener;


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }
    public RecycleViewAdapter(Context context) {
        this.context = context;
        recipeList = new ArrayList<>();
    }




    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }




    public Recipe getRecipe(int position){
        return recipeList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position)  {
        Recipe recipe =recipeList.get(position);

        Glide.with(context).load(recipe.getUrlImage()).error(R.drawable.food_400x267).into(holder.img);
        holder.idRecipe.setText(recipe.getIdRecipe()+"");
        holder.foodName.setText(recipe.getFoodName());
        holder.author.setText(recipe.getAuthor());
        holder.tag.setText(recipe.getTag());
        float rating = recipe.getRating();
        holder.ratting.setText(Math.round(rating*100.0)/100.0+"");







    }



    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }else return 0;

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView foodName, author,tag,ratting, idRecipe;
        ImageButton btFavorite;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_Recipe);
            foodName = view.findViewById(R.id.food_name);
            author = view.findViewById(R.id.author_name);
            tag = view.findViewById(R.id.tag);
            ratting = view.findViewById(R.id.ratting);
            idRecipe = view.findViewById(R.id.id_recipe);
            btFavorite = view.findViewById(R.id.bt_favorite);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if(itemListener != null){
                itemListener.onItemClick(view, getBindingAdapterPosition());
                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
            }

        }
    }




    public interface ItemListener{
        void onItemClick(View view,int position);
    }

    public void filterList(List<Recipe> filterList){
        recipeList = filterList;
        notifyDataSetChanged();
    }



}

