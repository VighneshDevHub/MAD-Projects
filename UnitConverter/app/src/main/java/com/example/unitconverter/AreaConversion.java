package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AreaConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Square Meters";
    private String toUnit = "Square Kilometers";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Square Meters", "Square Kilometers", "Square Centimeters",
            "Square Millimeters", "Hectares", "Acres", "Square Miles",
            "Square Yards", "Square Feet", "Square Inches"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.area_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertArea());

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

    private void convertArea() {
        String areaInput = et_fromUnit.getText().toString();
        if (areaInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(areaInput);
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
        double squareMeters = toSquareMeters(value, from);
        return fromSquareMeters(squareMeters, to);
    }

    private double toSquareMeters(double value, String from) {
        switch (from) {
            case "Square Kilometers":
                return value * 1e6;
            case "Square Centimeters":
                return value / 1e4;
            case "Square Millimeters":
                return value / 1e6;
            case "Hectares":
                return value * 1e4;
            case "Acres":
                return value * 4046.85642;
            case "Square Miles":
                return value * 2.59e6;
            case "Square Yards":
                return value * 0.836127;
            case "Square Feet":
                return value * 0.092903;
            case "Square Inches":
                return value * 0.00064516;
            default:
                return value; // Square Meters
        }
    }

    private double fromSquareMeters(double squareMeters, String to) {
        switch (to) {
            case "Square Kilometers":
                return squareMeters / 1e6;
            case "Square Centimeters":
                return squareMeters * 1e4;
            case "Square Millimeters":
                return squareMeters * 1e6;
            case "Hectares":
                return squareMeters / 1e4;
            case "Acres":
                return squareMeters / 4046.85642;
            case "Square Miles":
                return squareMeters / 2.59e6;
            case "Square Yards":
                return squareMeters / 0.836127;
            case "Square Feet":
                return squareMeters / 0.092903;
            case "Square Inches":
                return squareMeters / 0.00064516;
            default:
                return squareMeters; // Square Meters
        }
    }
}
