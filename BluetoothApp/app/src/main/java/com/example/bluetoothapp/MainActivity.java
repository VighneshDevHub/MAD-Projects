package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ImageView imgv_BtStatus;
    TextView tv_BtStatus;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> pairedDevicesAdapter;
    private ArrayList<String> pairedDevicesList;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVERABLE_BT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Button btnOnBt = findViewById(R.id.btnOnBt);
        Button btnOffBt = findViewById(R.id.btnOffBt);
        Button btnDiscover = findViewById(R.id.btnDiscover);
        Button btnPairedDevices = findViewById(R.id.btnPairedDevices);
        ListView lvPairedDevices = findViewById(R.id.lvPairedDevices);
        imgv_BtStatus = findViewById(R.id.imgv_BtStatus);
        tv_BtStatus = findViewById(R.id.tv_BtStatus);

        pairedDevicesList = new ArrayList<>();
        pairedDevicesAdapter = new ArrayAdapter<>(this,R.layout.list_items, pairedDevicesList);
        lvPairedDevices.setAdapter(pairedDevicesAdapter);

        updateBluetoothStatusText();
        updateImage();

        btnOnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth is already ON", Toast.LENGTH_SHORT).show();
                }
                updateBluetoothStatusText();
                updateImage();
            }
        });

        btnOffBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    updateBluetoothStatusText();
                    updateImage();
                    Toast.makeText(MainActivity.this, "Bluetooth turned OFF", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth is already OFF", Toast.LENGTH_SHORT).show();
                }
                updateBluetoothStatusText();
                updateImage();
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT);
            }
        });

        btnPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPairedDevices();
            }
        });
    }

    private void showPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        pairedDevicesList.clear();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                pairedDevicesList.add(deviceName + "\n" + deviceAddress);
            }
        } else {
            pairedDevicesList.add("No paired devices found");
        }
        pairedDevicesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
                updateBluetoothStatusText();
                updateImage();
            } else {
                Toast.makeText(this, "Failed to enable Bluetooth", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_DISCOVERABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Failed to enable discoverable mode", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Device is discoverable for " + resultCode + " seconds", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateImage() {
        if (bluetoothAdapter.isEnabled()) {
            imgv_BtStatus.setImageResource(R.drawable.bt_on);
        } else {
            imgv_BtStatus.setImageResource(R.drawable.bt_off);
        }
    }

    private void updateBluetoothStatusText() {
        if (bluetoothAdapter.isEnabled()) {
            tv_BtStatus.setText("Bluetooth Status: ON");
        } else {
            tv_BtStatus.setText("Bluetooth Status: OFF");
        }
    }
}



















//package com.example.bluetoothapp;
//
//import android.Manifest;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.Toast;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.util.ArrayList;
//import java.util.Set;
//
//public class MainActivity extends AppCompatActivity {
//
//    // Bluetooth adapter to manage Bluetooth operations
//    private BluetoothAdapter bluetoothAdapter;
//
//    // Adapter to display the list of paired devices
//    private ArrayAdapter<String> pairedDevicesAdapter;
//
//    // List to hold paired device information
//    private ArrayList<String> pairedDevicesList;
//
//    // Request code for Bluetooth permissions
//    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 3;
//
//    // ActivityResultLaunchers for handling activity results
//    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
//    private ActivityResultLauncher<Intent> discoverableBluetoothLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize Bluetooth adapter
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//
//        // Initialize UI elements
//        Button btnOnBt = findViewById(R.id.btnOnBt);
//        Button btnOffBt = findViewById(R.id.btnOffBt);
//        Button btnDiscover = findViewById(R.id.btnDiscover);
//        Button btnPairedDevices = findViewById(R.id.btnPairedDevices);
//        ListView lvPairedDevices = findViewById(R.id.lvPairedDevices);
//
//        // Initialize the list and adapter for paired devices
//        pairedDevicesList = new ArrayList<>();
//        pairedDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pairedDevicesList);
//        lvPairedDevices.setAdapter(pairedDevicesAdapter);
//
//        // Initialize ActivityResultLaunchers
//        enableBluetoothLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK) {
//                        Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Failed to enable Bluetooth", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        discoverableBluetoothLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_CANCELED) {
//                        Toast.makeText(this, "Failed to enable discoverable mode", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Device is discoverable for " + result.getResultCode() + " seconds", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        // Set click listener for enabling Bluetooth
//        btnOnBt.setOnClickListener(v -> {
//            if (!bluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                enableBluetoothLauncher.launch(enableBtIntent);
//            } else {
//                Toast.makeText(MainActivity.this, "Bluetooth is already on", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Set click listener for disabling Bluetooth
//        btnOffBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Check if Bluetooth is enabled
//                if (bluetoothAdapter.isEnabled()) {
//                    // Turn off Bluetooth
//                    bluetoothAdapter.disable();
//                    Toast.makeText(MainActivity.this, "Bluetooth turned off", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Bluetooth is already off", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        // Set click listener for making the device discoverable
//        btnDiscover.setOnClickListener(v -> {
//            if (checkBluetoothPermissions()) {
//                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//                discoverableBluetoothLauncher.launch(discoverableIntent);
//            }
//        });
//
//        // Set click listener for showing paired devices
//        btnPairedDevices.setOnClickListener(v -> {
//            if (checkBluetoothPermissions()) {
//                showPairedDevices();
//            }
//        });
//    }
//
//    // Method to check Bluetooth permissions
//    private boolean checkBluetoothPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN},
//                        REQUEST_BLUETOOTH_PERMISSIONS);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // Handle the result of Bluetooth permissions request
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Bluetooth permissions granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Bluetooth permissions denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Method to show paired devices
//    private void showPairedDevices() {
//        try {
//            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//            pairedDevicesList.clear();
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//                    String deviceName = device.getName();
//                    String deviceAddress = device.getAddress(); // MAC address
//                    pairedDevicesList.add(deviceName + "\n" + deviceAddress);
//                }
//            } else {
//                pairedDevicesList.add("No paired devices found");
//            }
//            pairedDevicesAdapter.notifyDataSetChanged();
//        } catch (SecurityException e) {
//            Toast.makeText(this, "Permission error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//}
