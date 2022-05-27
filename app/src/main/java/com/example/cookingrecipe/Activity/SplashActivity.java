package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //chua login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            //Toast.makeText(this, user.getUid()+"", Toast.LENGTH_SHORT).show();
            Log.e("Id",user.getUid()+"");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}