package com.example.cookingrecipe.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.Activity.ChangePasswordActivity;
import com.example.cookingrecipe.Activity.InformationActivity;
import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.Activity.UserPostActivity;
import com.example.cookingrecipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentAccount extends Fragment implements View.OnClickListener {
    private ImageView avatar;
    private TextView username, email;

    private LinearLayout layoutInfo, layoutLogout;
    private LinearLayout layoutChangePassword, layoutPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        showUserInfo();

        layoutLogout.setOnClickListener(this);
        layoutInfo.setOnClickListener(this);
        layoutChangePassword.setOnClickListener(this);
        layoutPost.setOnClickListener(this);

    }



    private void initView(View view) {
        avatar = view.findViewById(R.id.avatar);
        username = view.findViewById(R.id.username);

        email = view.findViewById(R.id.email);
        layoutInfo = view.findViewById(R.id.info_account);
        layoutChangePassword = view.findViewById(R.id.change_password);
        layoutLogout = view.findViewById(R.id.layout_logout);
        layoutPost = view.findViewById(R.id.user_post);


//        String urlAvatar = "https://scontent.fhan4-3.fna.fbcdn.net/v/t1.6435-9/66630708_886866635024136_7413215498239737856_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=g79eiVku1PgAX9RTxGS&_nc_ht=scontent.fhan4-3.fna&oh=00_AT9e0k0LqnpeWu5QcU64oEAev62ZrAn120EcNF4-jVOudA&oe=62AE0985";
//        Picasso.with(getActivity()).load(urlAvatar)
//                .placeholder(R.drawable.ic_account).error(R.drawable.ic_error).into(avatar);
//        userName.setText("Đỗ Đức Nghĩa");


    }

    @Override
    public void onClick(View view) {
        if(view == layoutLogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        }
        if(view == layoutInfo){
            Intent intent = new Intent(getActivity(), InformationActivity.class);
            startActivity(intent);
        }
        if(view == layoutChangePassword){
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }
        if(view == layoutPost){
            Intent intent = new Intent(getActivity(), UserPostActivity.class);
            startActivity(intent);
        }
    }

    public void showUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        String nameUser = user.getDisplayName();
        String emailUser = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if(nameUser == null){
            //username.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
        }else{
            username.setVisibility(View.VISIBLE);
            username.setText(nameUser);
        }


        email.setText(emailUser);
        Glide.with(this).load(photoUrl).error(R.drawable.user).into(avatar);


    }


}
