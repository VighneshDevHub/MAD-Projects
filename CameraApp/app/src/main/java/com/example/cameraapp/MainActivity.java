package com.example.cameraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE=199;
    ImageView imageView,imageView2;
    ImageButton imageButton;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        imageView2 = findViewById(R.id.imageView2);
        textView3=findViewById(R.id.textView3);

        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v->{
            Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE&&resultCode==RESULT_OK){
            Bitmap image =(Bitmap) data.getExtras().get("data");
            imageView2.setImageBitmap(image);
            imageView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
        }
    }
}