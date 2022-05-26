package com.example.cookingrecipe.Activity;

import static com.example.cookingrecipe.MainActivity.MY_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView avatar;
    private EditText email, fullName, dateOfBirth, address;
    private Button btUpdate;
    private Context context = InformationActivity.this;
    private Uri mUri;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    String linkImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        
        initView();
        setUserInformation();
        avatar.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
    }

    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        fullName.setText(user.getDisplayName());
        email.setText(user.getEmail());
        Glide.with(getApplicationContext()).load(user.getPhotoUrl()).error(R.drawable.user).into(avatar);
    }

    private void initView() {
        progressDialog = new ProgressDialog(context);
        avatar = findViewById(R.id.avatar);
        email = findViewById(R.id.email);
        email.setEnabled(false);
        fullName = findViewById(R.id.fullName);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        address = findViewById(R.id.address);
        btUpdate = findViewById(R.id.btUpdate);
        progressBar =findViewById(R.id.progress_bar);
        linkImage = "";
    }

    @Override
    public void onClick(View view) {
        if(view == avatar){
            //onClickRequestPermission();

            ImagePicker.with(this)
                    .galleryOnly()	//User can only select image from Gallery
                    .maxResultSize(1080, 1080)
                    .cropSquare()
                    .start(100);	//Default Request Code is ImagePicker.REQUEST_CODE
        }
        if(view == btUpdate){
            updateProfile();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Uri uri = data.getData();
            avatar.setImageURI(uri);
            linkImage = uri.toString();
        }
    }

    private void onClickRequestPermission() { // Cho android 6 tro len
        //MainActivity mainActivity = (MainActivity) context;
        MainActivity mainActivity = new MainActivity();
        if(mainActivity == null) return;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }else{
            if(getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                mainActivity.openGallery();
            }else{
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                this.requestPermissions(permission,MY_REQUEST_CODE);
            }
        }
    }
    public  void setBitmapImageView(Bitmap bitmapImageView){
        avatar.setImageBitmap(bitmapImageView);
    }

    private void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        String newFullName =  fullName.getText().toString().trim();
        String newDate = dateOfBirth.getText().toString().trim();
        String newAddress = address.getText().toString().trim();
        //progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        //fake String uri
        //linkImage = "https://i.pinimg.com/originals/13/d0/3a/13d03aa8a7c4715e56adbb0c6272acae.png";
        Log.e("Uri",linkImage);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newFullName)
                .setPhotoUri(Uri.parse(linkImage))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //progressDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            Log.d("Update Profile", "User profile updated.");
                            Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            /*FragmentAccount fragmentAccount = new FragmentAccount();
                            fragmentAccount.showUserInfo();*/
                            Intent intent = new Intent(InformationActivity.this, SplashActivity.class);
                            startActivity(intent);

                            finishAffinity();
                        }
                    }
                });
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }
}