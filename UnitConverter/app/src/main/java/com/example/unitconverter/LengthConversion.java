package com.example.unitconverter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Arrays;

public class LengthConversion extends AppCompatActivity {
    CardView cv_fromUnit, cv_toUnit, cv_convert;
    RelativeLayout mCLayout;
    String fromUnit = "None";
    String toUnit = "None"; // Default units
    TextView tv_fromUnit, tv_toUnit;
    EditText et_fromUnit, et_toUnit;
    final String[] units = new String[]{
            "None",
            "Meter",
            "Kilometer",
            "Centimeter",
            "Millimeter",
            "Micrometer",
            "Nanometer"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);

        mCLayout = findViewById(R.id.length_relativeLayout);

        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);

        tv_fromUnit.setText(units[0]); // Set default unit
        tv_toUnit.setText(units[0]); // Set default unit

        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        cv_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et_fromUnit.getText().toString();
                if (input.equals("") || input == null) {
                    et_fromUnit.setError("Please enter a value");
                } else {
                    double inputValue = Double.parseDouble(input);
                    double result = convertLength(inputValue, fromUnit, toUnit);
                    et_toUnit.setText(String.valueOf(result));
                }
            }
        });

        cv_toUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnitPickerDialog(true);
            }
        });

        cv_fromUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnitPickerDialog(false);
            }
        });
    }

    private void showUnitPickerDialog(final boolean isToUnit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LengthConversion.this);
        builder.setTitle("Choose Unit");

        builder.setSingleChoiceItems(
                units, // Items list
                Arrays.asList(units).indexOf(isToUnit ? toUnit : fromUnit), // Index of checked item
                new DialogInterface.OnClickListener() // Item click listener
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the selected item's text
                        String selectedUnit = units[i];
                        if (isToUnit) {
                            toUnit = selectedUnit;
                            tv_toUnit.setText(toUnit);
                        } else {
                            fromUnit = selectedUnit;
                            tv_fromUnit.setText(fromUnit);
                        }
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        // Conversion logic
        double result = value;

        // Convert to base unit (Meter)
        if (!fromUnit.equals("Meter")) {
            result = convertToMeter(value, fromUnit);
        }

        // Convert from base unit to desired unit
        if (!toUnit.equals("Meter")) {
            result = convertFromMeter(result, toUnit);
        }

        return result;
    }

    private double convertToMeter(double value, String unit) {
        switch (unit) {
            case "Kilometer":
                return value * 1000;
            case "Centimeter":
                return value / 100;
            case "Millimeter":
                return value / 1000;
            case "Micrometer":
                return value / 1e+6;
            case "Nanometer":
                return value / 1e+9;
            default:
                return value; // Meter
        }
    }

    private double convertFromMeter(double value, String unit) {
        switch (unit) {
            case "Kilometer":
                return value / 1000;
            case "Centimeter":
                return value * 100;
            case "Millimeter":
                return value * 1000;
            case "Micrometer":
                return value * 1e+6;
            case "Nanometer":
                return value * 1e+9;
            default:
                return value; // Meter
        }
    }
}
