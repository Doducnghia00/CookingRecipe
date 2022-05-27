package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.utils.MySharedPreferences;
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
    private TextView textForgot;
    private MySharedPreferences preferences;

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
        textForgot = findViewById(R.id.textForgot);
        preferences = new MySharedPreferences(LoginActivity.this);
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
            //fogotPassword.setVisibility(View.INVISIBLE);

            btLogin.setText("Gửi email");

            Toast.makeText(LoginActivity.this, "Vui lòng điền email", Toast.LENGTH_SHORT).show();
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Todo check trường có bị trống
                    String emailUser = email.getText().toString().trim();
                    //progressDialog.show();
                    if(emailUser.equals("")){
                        Toast.makeText(LoginActivity.this, "Vui lòng điền email", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
            });
            //onClickFogot();
        }
    }

    private void onClickFogot() {
        // Todo check trường có bị trống
        String emailUser = email.getText().toString().trim();
        //progressDialog.show();
        if(emailUser.equals("")){
            Toast.makeText(this, "Vui lòng điền email", Toast.LENGTH_SHORT).show();
            return;
        }
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


        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        if(emailString.equals("") || passwordString.equals("")){
            Toast.makeText(this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordString.length() < 6){
            Toast.makeText(this, "Password phải từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                            //Toast.makeText(LoginActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            preferences.setDisplayName(user.getDisplayName());
                            preferences.setEmail(user.getEmail());

                            String displayName = preferences.getDisplayName();
                            if(!displayName.equals("")){
                                Toast.makeText(LoginActivity.this, "Xin chào " + displayName , Toast.LENGTH_SHORT).show();
                            }

                            Log.d("Login", "signInWithEmail:success");
                            //preferences.setUsername();

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