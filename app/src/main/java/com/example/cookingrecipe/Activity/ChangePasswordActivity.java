package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookingrecipe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPass, newPass, reNewPass;
    Button btChangePassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();

            }
        });
    }

    private void changePassword() {
        String oldPassword = oldPass.getText().toString().trim();
        String newPassword = newPass.getText().toString().trim();
        String reNewPassword = reNewPass.getText().toString().trim();
        //TODO 2 Check rePass == pass, check pass >= 6 ki t
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.d("Change Password", "User password updated.");
                            Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                        }else{
                            //TODO 3 hien thi dialog re-authenticate
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        oldPass = findViewById(R.id.old_password);
        newPass = findViewById(R.id.new_password);
        reNewPass = findViewById(R.id.r_new_password);
        btChangePassword = findViewById(R.id.bt_change_password);
    }

    public void reAuthenticate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            changePassword();
                        }else{

                        }
                        Log.d("Re-authenticated", "User re-authenticated.");
                    }
                });
    }
}