package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StorageConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Bytes";
    private String toUnit = "Kilobytes";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes",
            "Petabytes", "Exabytes", "Zettabytes", "Yottabytes", "Bits"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.storage_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertStorage());

        cv_toUnit.setOnClickListener(v -> showUnitSelectorDialog(tv_toUnit, selected -> toUnit = selected));
        cv_fromUnit.setOnClickListener(v -> showUnitSelectorDialog(tv_fromUnit, selected -> fromUnit = selected));
    }

    private void showUnitSelectorDialog(TextView textView, UnitSelectionListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Unit");
        builder.setSingleChoiceItems(units, -1, (dialog, which) -> {
            String selectedUnit = units[which];
            textView.setText(selectedUnit);
            listener.onUnitSelected(selectedUnit);
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void convertStorage() {
        String storageInput = et_fromUnit.getText().toString();
        if (storageInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(storageInput);
        } catch (NumberFormatException e) {
            et_fromUnit.setError("Invalid input. Please enter a numeric value.");
            return;
        }

        double outputValue = convert(inputValue, fromUnit, toUnit);
        et_toUnit.setText(String.valueOf(outputValue));
    }

    @FunctionalInterface
    interface UnitSelectionListener {
        void onUnitSelected(String unit);
    }

    private double convert(double value, String from, String to) {
        double bytes = toBytes(value, from);
        return fromBytes(bytes, to);
    }

    private double toBytes(double value, String from) {
        switch (from) {
            case "Kilobytes":
                return value * 1e3;
            case "Megabytes":
                return value * 1e6;
            case "Gigabytes":
                return value * 1e9;
            case "Terabytes":
                return value * 1e12;
            case "Petabytes":
                return value * 1e15;
            case "Exabytes":
                return value * 1e18;
            case "Zettabytes":
                return value * 1e21;
            case "Yottabytes":
                return value * 1e24;
            case "Bits":
                return value / 8;
            default:
                return value; // Bytes
        }
    }

    private double fromBytes(double bytes, String to) {
        switch (to) {
            case "Kilobytes":
                return bytes / 1e3;
            case "Megabytes":
                return bytes / 1e6;
            case "Gigabytes":
                return bytes / 1e9;
            case "Terabytes":
                return bytes / 1e12;
            case "Petabytes":
                return bytes / 1e15;
            case "Exabytes":
                return bytes / 1e18;
            case "Zettabytes":
                return bytes / 1e21;
            case "Yottabytes":
                return bytes / 1e24;
            case "Bits":
                return bytes * 8;
            default:
                return bytes; // Bytes
        }
    }
}
