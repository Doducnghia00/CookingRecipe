package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class RecipeDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name, author, date,description,material,practice, textRate;
    private ImageView img;
    private String  urlImage, numRate;
    private RatingBar ratingBar;
    private Button btRate;
    private Recipe recipe;
    private ImageButton btFavorite;
    boolean favor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);



        Intent intent = this.getIntent();
        boolean favorite = intent.getBooleanExtra("favorite",true);
        favor = favorite;
        //Toast.makeText(this, favorite+"", Toast.LENGTH_SHORT).show();
        recipe = (Recipe) intent.getSerializableExtra("object");
        //Toast.makeText(this, recipe.getNumRate()+"", Toast.LENGTH_SHORT).show();
        initView(favorite);
        setData(recipe);
        btRate.setOnClickListener(this);
        btFavorite.setOnClickListener(this);

    }

    private void setData(Recipe recipe) {
        description.setText(recipe.getDescription());

        Glide.with(RecipeDetailActivity.this).load(recipe.getUrlImage()).error(R.drawable.food_400x267).into(img);

        material.setText(recipe.getMaterial().replace("\\n","\n"));
        practice.setText(recipe.getPractice().replace("\\n","\n"));

        ratingBar.setRating(recipe.getRating());

        name.setText(recipe.getFoodName());
        author.setText(recipe.getAuthor());
        date.setText(recipe.getDate());

        textRate.setText(recipe.getNumRate() + " lượt đánh giá");

    }

    private void getData(String foodID) {
        FirebaseDatabase database  = FirebaseDatabase.getInstance();
        String path = "food/" + foodID;
        Log.e("path",path);
        DatabaseReference myRef = database.getReference(path);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe recipe = snapshot.getValue(Recipe.class);

                description.setText(recipe.getDescription());

                Glide.with(RecipeDetailActivity.this).load(recipe.getUrlImage()).error(R.drawable.food_400x267).into(img);

                material.setText(recipe.getMaterial().replace("\\n","\n"));
                practice.setText(recipe.getPractice().replace("\\n","\n"));

                ratingBar.setRating(recipe.getRating());

                name.setText(recipe.getFoodName());
                author.setText(recipe.getAuthor());
                date.setText(recipe.getDate());

                textRate.setText(recipe.getNumRate() + " lượt đánh giá");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecipeDetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(boolean favorite) {
        name = findViewById(R.id.nameFood);
        author = findViewById(R.id.author_name);
        date = findViewById(R.id.date);
        description = findViewById(R.id.description);
        material = findViewById(R.id.material);
        practice = findViewById(R.id.step);
        ratingBar = findViewById(R.id.ratingBar);
        btRate = findViewById(R.id.btRate);
        img = findViewById(R.id.img_food);
        textRate = findViewById(R.id.textRate);
        btFavorite = findViewById(R.id.bt_favorite);

        //Toast.makeText(this, favorite+"", Toast.LENGTH_SHORT).show();
        if(favorite){
            btFavorite.setColorFilter(getResources().getColor(R.color.pink));
        }else{
            btFavorite.setColorFilter(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btRate){
            try {
                float addRate = ratingBar.getRating();
                float currentRate = recipe.getRating();
                int currentNum = recipe.getNumRate();
                int newNum = currentNum +1;
                float newRate = (float) (currentRate * currentNum + addRate)/newNum;


                ratingBar.setRating(newRate);
                textRate.setText(newNum + " lượt đánh giá");

                String idRecipe = recipe.getIdRecipe()+"";

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String path = "food/" + idRecipe;
                DatabaseReference myRef = database.getReference(path);

                recipe.setRating(newRate);
                recipe.setNumRate(newNum);
                myRef.setValue(recipe, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(RecipeDetailActivity.this, "Gửi đánh giá thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){
                Log.e("Rate",e.getMessage());
            }
        }

        if(view == btFavorite){
            if(favor){
                btFavorite.setColorFilter(getResources().getColor(R.color.gray));
               // Toast.makeText(this, "Xóa khỏi mục yêu thích", Toast.LENGTH_SHORT).show();
                removeFavorite(recipe);
            }else{
                btFavorite.setColorFilter(getResources().getColor(R.color.pink));
                //Toast.makeText(this, "Đã thêm vào mục yêu thích", Toast.LENGTH_SHORT).show();
                addFavorite(recipe);
            }
        }
    }

    private void addFavorite(Recipe recipe) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String path = "favorite/" + user.getUid() +"/"+recipe.getIdRecipe();
        DatabaseReference myRef = database.getReference(path);
        myRef.setValue(recipe.getIdRecipe());
        Toast.makeText(this, "Đã thêm vào mục yêu thích", Toast.LENGTH_SHORT).show();
    }

    private void removeFavorite(Recipe recipe) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String path = "favorite/" + user.getUid() +"/"+recipe.getIdRecipe();
        DatabaseReference myRef = database.getReference(path);
        myRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(RecipeDetailActivity.this, "Đã xóa khỏi mục yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }


}