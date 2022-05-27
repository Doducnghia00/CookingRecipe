package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText email,displayName, password, rePassword;
    private Button btRegister;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        btRegister.setOnClickListener(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.email);
        displayName = findViewById(R.id.display_name);
        displayName.setVisibility(View.GONE);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rPassword);
        btRegister = findViewById(R.id.bt_register);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        if(view == btRegister){
            onClickRegister();
        }
    }

    private void onClickRegister() {
        String displayNameString = displayName.getText().toString().trim();
        String rPassword = rePassword.getText().toString().trim();
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

        if(!passwordString.equals(rPassword)){
            Toast.makeText(this, "Hai trường mật khẩu phải giống nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            Log.d("Register", "createUserWithEmail:success");
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(displayNameString).build();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}