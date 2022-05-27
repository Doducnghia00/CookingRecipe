package com.example.cookingrecipe.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Activity.RecipeDetailActivity;
import com.example.cookingrecipe.Activity.SearchActivity;
import com.example.cookingrecipe.Activity.TestActivity;
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
import java.util.List;

public class FragmentFavorite extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private List<Recipe> recipeList;
    private List<String> favoriteList;
    private FirebaseUser user;


    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
//        getFavoritesList();
        //fakeData();
        //adapter.setRecipeList(recipeList);
        adapter.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        recipeList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycle_view);
        favoriteList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new RecycleViewAdapter(getActivity());

    }

    private void fakeData() {
        recipeList = new ArrayList<>();
        recipeList.add(0,new Recipe(R.drawable.category_mon_xoi,1,"Mon A","Nghia","Mon xoi",  4.5f,"25/05/2022"));
        recipeList.add(1,new Recipe(R.drawable.category_mon_xao,2,"Mon B","Linh","Mon xao",4.5f,"25/05/2022"));
        recipeList.add(2,new Recipe(R.drawable.category_mon_nuong,3,"Mon C","Tuyen","Mon nuong",4.5f,"24/05/2022"));
        recipeList.add(3,new Recipe(R.drawable.category_mon_mut,4,"Mon D","Long","Mon mut",4.5f,"27/05/2022"));
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavoritesList();
    }


    private void getFavoritesList() {
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
                getList();
               // getRecipeList(favoriteList);
                //getListV2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Get favorite failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getListV2() {
        //progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if (favoriteList.contains(recipe.getIdRecipe()+"") ){
                    recipeList.add(recipe);
                    //getFavoritesList();
                    adapter.setRecipeList(recipeList);
                    //progressBar.setVisibility(View.GONE);
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                String recipeKey = snapshot.getKey();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String recipeKey = snapshot.getKey();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                String recipeKey = snapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load data.",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getRecipeList(List<String> favoriteList) {
        Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
        for (String i : favoriteList ) {
            String path = "food/" + i;
            Toast.makeText(getActivity(),i, Toast.LENGTH_SHORT).show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef1 = database.getReference(path);
            myRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Recipe recipe = snapshot.getValue(Recipe.class);

                        recipeList.add(recipe);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Loi", Toast.LENGTH_SHORT).show();
                }
            });
        }
        for (int i = 0; i < recipeList.size(); i++) {
            Log.e("A",recipeList.get(i).toString());
        }
        adapter.setRecipeList(recipeList);
    }

    private void getList() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                        if(favoriteList.contains(recipe.getIdRecipe()+"")){
                            recipeList.add(recipe);
                        }


                }


                if(recipeList.size() <1){
                    recipeList = new ArrayList<>();
                    adapter.setRecipeList(recipeList);
                    //Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }else{

                    adapter.setRecipeList(recipeList);
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Get data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Recipe recipe = adapter.getRecipe(position);
        //Toast.makeText(this, recipe.getNumRate()+"", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtra("favorite",true);

        //recipeList.remove(recipe);
        recipeList = new ArrayList<>();
        adapter.setRecipeList(recipeList);
        intent.putExtra("object",recipe);
        startActivity(intent);
    }
}
