package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PressureConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Pascals";
    private String toUnit = "Kilopascals";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Pascals", "Kilopascals", "Megapascals", "Bar", "Millibar",
            "Atmospheres", "Torr", "Psi", "Millimeters of Mercury", "Inches of Mercury"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.pressure_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertPressure());

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

    private void convertPressure() {
        String pressureInput = et_fromUnit.getText().toString();
        if (pressureInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(pressureInput);
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
        double pascals = toPascals(value, from);
        return fromPascals(pascals, to);
    }

    private double toPascals(double value, String from) {
        switch (from) {
            case "Kilopascals":
                return value * 1e3;
            case "Megapascals":
                return value * 1e6;
            case "Bar":
                return value * 1e5;
            case "Millibar":
                return value * 100;
            case "Atmospheres":
                return value * 1.01325e5;
            case "Torr":
                return value * 133.322;
            case "Psi":
                return value * 6894.757;
            case "Millimeters of Mercury":
                return value * 133.322;
            case "Inches of Mercury":
                return value * 3386.39;
            default:
                return value; // Pascals
        }
    }

    private double fromPascals(double pascals, String to) {
        switch (to) {
            case "Kilopascals":
                return pascals / 1e3;
            case "Megapascals":
                return pascals / 1e6;
            case "Bar":
                return pascals / 1e5;
            case "Millibar":
                return pascals / 100;
            case "Atmospheres":
                return pascals / 1.01325e5;
            case "Torr":
                return pascals / 133.322;
            case "Psi":
                return pascals / 6894.757;
            case "Millimeters of Mercury":
                return pascals / 133.322;
            case "Inches of Mercury":
                return pascals / 3386.39;
            default:
                return pascals; // Pascals
        }
    }
}
