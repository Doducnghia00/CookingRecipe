package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.R;

public class TestActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.material);
        textView.setText("Got some text <b>another</b> line");

        Intent intent = this.getIntent();
        boolean favorite = intent.getBooleanExtra("favorite",true);
        Toast.makeText(this, favorite+"", Toast.LENGTH_SHORT).show();
    }
}