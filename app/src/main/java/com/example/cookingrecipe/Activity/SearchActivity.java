package com.example.cookingrecipe.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.R;
import com.example.cookingrecipe.adapter.RecycleViewAdapter;
import com.example.cookingrecipe.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewAdapter.ItemListener {
    private Button btSearch;
    //private FirebaseListAdapter<ChatMessage> adapter;
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<Recipe> recipeList, backUpList;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private List<String> favoriteList;
    private EditText keyword;
    private RadioGroup radioGroup;
    private RadioButton radioButton;



    String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        //getFavoritesList();

        Intent intent = this.getIntent();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            tag= null;
            //getList();
        } else {
            tag= extras.getString("tag");
            //Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
            //getListByTag(tag);
        }



        btSearch.setOnClickListener(this);


        adapter = new RecycleViewAdapter(SearchActivity.this);
        adapter.setRecipeList(recipeList);
        adapter.setItemListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);

        //this.dispatchKeyEvent()
    }



    private void getListByTag(String tag) {
        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        recipeList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if(recipe.getTag().equals(tag)){
                        recipeList.add(recipe);
                        Log.e("Tag","true");
                    }else {
                        Log.e("Tag","false");
                    }

                }
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                if(recipeList.size() <1){
                    recipeList = new ArrayList<>();
                    adapter.setRecipeList(recipeList);
                    Toast.makeText(SearchActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
                    //getFavoritesList();
                    adapter.setRecipeList(recipeList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Get data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getList() {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if (recipe != null){
                    recipeList.add(recipe);
                    //getFavoritesList();
                    adapter.setRecipeList(recipeList);
                    progressBar.setVisibility(View.GONE);
                }
                backUpList = recipeList;
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void initView() {
        recipeList = new ArrayList<>();
        btSearch = findViewById(R.id.bt_search);
        recyclerView = findViewById(R.id.recycle_view);
        progressDialog = new ProgressDialog(this);
        progressBar = findViewById(R.id.progress_bar);

        recipeList = new ArrayList<>();
        favoriteList = new ArrayList<>();

        keyword = findViewById(R.id.ed_search);
        radioGroup= findViewById(R.id.radio_group);
        RadioButton radioButton2 = findViewById(R.id.nameMaterial);
        radioButton2.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View view) {
        if(view == btSearch){

            String key = keyword.getText().toString().trim();
            
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            //String x = (String) radioButton.getText();
            switch (selectedId){
                        case R.id.nameFood:
                            searchByNameFood(key);
                            break;
                        case R.id.nameAuthor:
                            searchByNameAuthor(key);
                            break;
                        case R.id.nameMaterial:
                            searchByNameMaterial(key);
                            break;
                default: 
                    }

        }
    }

    private void searchByNameMaterial(String key) {
    }

    private void searchByNameAuthor(String key) {
        List<Recipe> filterList = new ArrayList<>();
        for (Recipe recipe: backUpList) {
            if(recipe.getAuthor().toLowerCase().contains(key.toLowerCase())){
                filterList.add(recipe);
            }
            adapter.setRecipeList(filterList);
            adapter.notifyDataSetChanged();
        }
    }

    private void searchByNameFood(String key) {
        //recipeList.clear();
        Log.e("key",key);
        Toast.makeText(this, "Search By Food's Name", Toast.LENGTH_SHORT).show();
        List<Recipe> filterList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Recipe recipe = snapshot.getValue(Recipe.class);
                if(recipe != null){
                    if(recipe.getFoodName().toLowerCase().contains(key.toLowerCase())){
                        filterList.add(recipe);
                        //recipeList.add(0,recipe);

                        Log.e("filter", recipe.getFoodName());

                    }
                    //adapter.filterList(recipeList);
                    adapter.setRecipeList(filterList);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Recipe recipe = adapter.getRecipe(position);

        Intent intent = new Intent(SearchActivity.this, RecipeDetailActivity.class);
        if (favoriteList.contains(recipe.getIdRecipe()+"")){
            intent.putExtra("favorite",true);
        }else intent.putExtra("favorite",false);


        recipeList = new ArrayList<>();
        adapter.setRecipeList(recipeList);
        intent.putExtra("object",recipe);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tag == null){
            getList();
        }else{
            getListByTag(tag);
        }
        getFavoritesList();
    }

    private void getFavoritesList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        String idUser = user.getUid() +"";
        String path = "favorite/" + idUser;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Integer value = dataSnapshot.getValue(Integer.class);

                    favoriteList.add(value+"");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Get favorite failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}