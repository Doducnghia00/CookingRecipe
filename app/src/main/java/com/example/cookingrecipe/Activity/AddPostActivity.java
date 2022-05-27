package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.Recipe;
import com.example.cookingrecipe.model.UserPost;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameFood, description, material, step;
    private Button btSelectImg, btRemove;
    private FloatingActionButton btSave;
    private String linkImage;
    private ImageView imageView;
    private UserPost userPost;
    private int currentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        initView();

        Intent intent = this.getIntent();
        String status = intent.getStringExtra("status");
        if(status.equals("mod")){
            userPost = (UserPost) intent.getSerializableExtra("userPost");
            currentId = userPost.getId();
            getDataPost(userPost.getId());
            btRemove.setVisibility(View.VISIBLE);

        }else{

        }

        btSelectImg.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btRemove.setOnClickListener(this);


    }

    private void getDataPost(int id) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database  = FirebaseDatabase.getInstance();
        String path = "user_post/" +  user.getUid() +"/"+id;
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if(recipe == null){
                    return;
                }
                Log.e("Recipe",recipe.toString());
                // private EditText nameFood, description, material, step;
                nameFood.setText(recipe.getFoodName());
                description.setText(recipe.getDescription().replace("\\n","\n"));
                material.setText(recipe.getMaterial().replace("\\n","\n"));
                step.setText(recipe.getPractice().replace("\\n","\n"));
                Glide.with(AddPostActivity.this).load(recipe.getUrlImage()).error(R.drawable.ic_error).into(imageView);

                linkImage = recipe.getUrlImage();
                //currentId = recipe.getIdRecipe();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initView() {
        currentId = -1;
        userPost = new UserPost();
        linkImage="";
        nameFood = findViewById(R.id.food_name);
        description = findViewById(R.id.description);
        material = findViewById(R.id.material);
        step = findViewById(R.id.step);
        btSelectImg = findViewById(R.id.bt_select_image);
        btSave = findViewById(R.id.btSave);
        imageView = findViewById(R.id.img);
        btRemove = findViewById(R.id.remove);
    }

    @Override
    public void onClick(View view) {
        if(view == btSelectImg){
            ImagePicker.with(this)
                    .galleryOnly()	//User can only select image from Gallery
                    .maxResultSize(1080, 1080)
                    .crop()
                    .compress(1024)
                    .start(200);	//Default Request Code is ImagePicker.REQUEST_CODE
        }
        if(view == btSave){
            //Toast.makeText(this, "btSave", Toast.LENGTH_SHORT).show();
            savePost();
            finish();
        }
        if(view == btRemove){
            removePost();
            finish();
        }
    }

    private void removePost() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database  = FirebaseDatabase.getInstance();
        String path = "user_post/" +  user.getUid() +"/"+currentId;
        String path1 = "post/" + user.getUid() + "/"+currentId;
        DatabaseReference myRef = database.getReference(path);
        DatabaseReference myRef2 = database.getReference(path1);
        myRef.removeValue();
        myRef2.removeValue();
    }

    private void savePost() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database  = FirebaseDatabase.getInstance();

        Recipe recipe = new Recipe();
        recipe.setNumRate(0);
        recipe.setRating(0);
        recipe.setIdRecipe(1);
        recipe.setAuthor(user.getDisplayName());
        recipe.setDate("27/05/2022");
        recipe.setDescription(description.getText().toString().trim());
        recipe.setFoodName(nameFood.getText().toString().trim());
        recipe.setMaterial(material.getText().toString().trim());
        recipe.setPractice(step.getText().toString().trim());
        recipe.setUrlImage(linkImage);
        int max = 100000000;
        int min = 0;
        int idPost1;
        if(currentId>0){
            idPost1 = currentId;
        }else{
            idPost1 = (int) Math.floor(Math.random()*(max-min+1)+min);
        }



        String idPost = "1";
        if (userPost != null){
            idPost = userPost.getId()+"";
        }
        String path = "user_post/" +  user.getUid() + "/" + idPost1;
        DatabaseReference myRef = database.getReference(path);

        myRef.setValue(recipe, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(AddPostActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        userPost.setIdUser(user.getUid());
        userPost.setPostName(nameFood.getText().toString().trim());
        userPost.setStatus("Chờ Duyệt");
        userPost.setDate("27/05/2022");
        userPost.setLinkImage(linkImage);
        userPost.setId(idPost1);
        String path1 = "post/"+  user.getUid() + "/" + idPost1;
        DatabaseReference myRef1 = database.getReference(path1);
        myRef1.setValue(userPost, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                //
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            linkImage = uri.toString();
        }
    }
}