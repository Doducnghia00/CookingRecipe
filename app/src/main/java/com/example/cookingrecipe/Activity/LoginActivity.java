package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText email, password;
    private Button btLogin;
    private LinearLayout layoutSignUp, fogotPassword;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


        btLogin.setOnClickListener(this);
        layoutSignUp.setOnClickListener(this);
        fogotPassword.setOnClickListener(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btLogin = findViewById(R.id.bt_login);
        layoutSignUp = findViewById(R.id.layout_signUp);
        fogotPassword = findViewById(R.id.forgot);
        progressBar = findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View view) {
        if(view == btLogin){
            onClickLogin();

        }
        if(view == layoutSignUp){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
        if(view  == fogotPassword){
            password.setVisibility(View.INVISIBLE);
            fogotPassword.setVisibility(View.INVISIBLE);
            btLogin.setText("Gửi email");
            onClickFogot();
        }
    }

    private void onClickFogot() {
        // Todo check trường có bị trống
        String emailUser = email.getText().toString().trim();
        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //String emailAddress = "doducnghia00@gmail.com";

        auth.sendPasswordResetEmail(emailUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            Log.d("Send email", "Email sent.");
                            Toast.makeText(LoginActivity.this, "Kiểm tra email của bạn", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Gửi email thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onClickLogin() {

        //TODO check trường có bị trống ? email 6 kí tự?
        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


}