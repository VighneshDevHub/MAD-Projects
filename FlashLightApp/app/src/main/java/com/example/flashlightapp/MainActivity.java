package com.example.flashlightapp;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imgBtn);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        imageButton.setOnClickListener(v -> {
            try {
                if (isFlashlightOn) {
                    turnOffFlashLight();
                } else {
                    turnOnFlashLight();
                }
                isFlashlightOn = !isFlashlightOn;
                updateButtonImage();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void turnOnFlashLight() throws CameraAccessException {
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(this, "Flashlight turned on", Toast.LENGTH_SHORT).show();
    }

    private void turnOffFlashLight() throws CameraAccessException {
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(this, "Flashlight turned off", Toast.LENGTH_SHORT).show();
    }

    private void updateButtonImage() {
        if (isFlashlightOn) {
            imageButton.setImageResource(R.drawable.on);
        } else {
            imageButton.setImageResource(R.drawable.off);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (isFlashlightOn) {
                turnOffFlashLight();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
