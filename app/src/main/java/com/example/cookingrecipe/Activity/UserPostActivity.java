package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cookingrecipe.R;
import com.example.cookingrecipe.adapter.RecycleViewAdapter;
import com.example.cookingrecipe.adapter.UserPostAdapter;
import com.example.cookingrecipe.model.UserPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class UserPostActivity extends AppCompatActivity implements View.OnClickListener, UserPostAdapter.ItemListener{

    private FloatingActionButton btAdd;

    private UserPostAdapter adapter;
    private RecyclerView recyclerView;
    private List<UserPost> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        initView();
//        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
        //fakeData();
        getData();
        adapter = new UserPostAdapter(UserPostActivity.this);
        adapter.setPostList(postList);
        adapter.setItemListener(this);
        //recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(UserPostActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);



        btAdd.setOnClickListener(this);
    }

    private void getData() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database  = FirebaseDatabase.getInstance();
        String path = "post/" +  user.getUid() ;
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserPost userPost = dataSnapshot.getValue(UserPost.class);
                    postList.add(userPost);
                    adapter.notifyDataSetChanged();
                    Log.e("AAA",userPost.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fakeData() {
        postList.add(0,new UserPost(1,"","22/05/2022","Bánh Mì Nướng","Đã Duyệt"));
        postList.add(1,new UserPost(2,"","23/05/2022","Bánh Mì Không","Từ Chối"));
        postList.add(2,new UserPost(3,"","24/05/2022","Bánh Mì Kem","Chờ Duyệt"));
    }

    private void initView() {
        btAdd = findViewById(R.id.add);
       recyclerView = findViewById(R.id.recycle_view);
        postList = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        if(view == btAdd){
            Intent intent = new Intent(UserPostActivity.this, AddPostActivity.class);
            intent.putExtra("status","add");
            startActivity(intent);
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        UserPost userPost = adapter.getPostList().get(position);
        Intent intent = new Intent(UserPostActivity.this, AddPostActivity.class);
        intent.putExtra("status","mod");
        intent.putExtra("userPost",userPost);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}