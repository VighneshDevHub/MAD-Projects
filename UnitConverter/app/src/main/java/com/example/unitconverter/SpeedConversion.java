package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SpeedConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Meters per Second";
    private String toUnit = "Kilometers per Hour";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Meters per Second", "Kilometers per Hour", "Miles per Hour",
            "Feet per Second", "Knots", "Mach",
            "Centimeters per Second", "Inches per Second",
            "Millimeters per Second", "Yards per Minute",
            "Miles per Minute", "Miles per Second"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.speed_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertSpeed());

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

    private void convertSpeed() {
        String speedInput = et_fromUnit.getText().toString();
        if (speedInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(speedInput);
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
        double metersPerSecond = toMetersPerSecond(value, from);
        return fromMetersPerSecond(metersPerSecond, to);
    }

    private double toMetersPerSecond(double value, String from) {
        switch (from) {
            case "Kilometers per Hour":
                return value / 3.6;
            case "Miles per Hour":
                return value * 0.44704;
            case "Feet per Second":
                return value * 0.3048;
            case "Knots":
                return value * 0.514444;
            case "Mach":
                return value * 343;
            case "Centimeters per Second":
                return value / 100;
            case "Inches per Second":
                return value * 0.0254;
            case "Millimeters per Second":
                return value / 1000;
            case "Yards per Minute":
                return value * 0.01524;
            case "Miles per Minute":
                return value * 26.8224;
            case "Miles per Second":
                return value * 1609.344;
            default:
                return value; // Meters per Second
        }
    }

    private double fromMetersPerSecond(double value, String to) {
        switch (to) {
            case "Kilometers per Hour":
                return value * 3.6;
            case "Miles per Hour":
                return value / 0.44704;
            case "Feet per Second":
                return value / 0.3048;
            case "Knots":
                return value / 0.514444;
            case "Mach":
                return value / 343;
            case "Centimeters per Second":
                return value * 100;
            case "Inches per Second":
                return value / 0.0254;
            case "Millimeters per Second":
                return value * 1000;
            case "Yards per Minute":
                return value / 0.01524;
            case "Miles per Minute":
                return value / 26.8224;
            case "Miles per Second":
                return value / 1609.344;
            default:
                return value; // Meters per Second
        }
    }
}
