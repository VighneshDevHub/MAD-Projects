package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ForceConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Newtons";
    private String toUnit = "Pounds-Force";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Newtons", "Kilonewtons", "Pounds-Force",
            "Poundals", "Dynes", "Kilograms-Force",
            "Tonnes-Force", "Grams-Force", "Millinewtons",
            "Micronewtons", "Ounces-Force", "Stones-Force"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.force_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[2]);

        cv_convert.setOnClickListener(v -> convertForce());

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

    private void convertForce() {
        String forceInput = et_fromUnit.getText().toString();
        if (forceInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(forceInput);
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
        double newtons = toNewtons(value, from);
        return fromNewtons(newtons, to);
    }

    private double toNewtons(double value, String from) {
        switch (from) {
            case "Kilonewtons":
                return value * 1_000;
            case "Pounds-Force":
                return value * 4.44822;
            case "Poundals":
                return value * 0.138255;
            case "Dynes":
                return value * 1e-5;
            case "Kilograms-Force":
                return value * 9.80665;
            case "Tonnes-Force":
                return value * 9_806.65;
            case "Grams-Force":
                return value * 9.80665e-3;
            case "Millinewtons":
                return value * 1e-3;
            case "Micronewtons":
                return value * 1e-6;
            case "Ounces-Force":
                return value * 0.278014;
            case "Stones-Force":
                return value * 62.275;
            default:
                return value; // Newtons
        }
    }

    private double fromNewtons(double value, String to) {
        switch (to) {
            case "Kilonewtons":
                return value / 1_000;
            case "Pounds-Force":
                return value / 4.44822;
            case "Poundals":
                return value / 0.138255;
            case "Dynes":
                return value / 1e-5;
            case "Kilograms-Force":
                return value / 9.80665;
            case "Tonnes-Force":
                return value / 9_806.65;
            case "Grams-Force":
                return value / 9.80665e-3;
            case "Millinewtons":
                return value / 1e-3;
            case "Micronewtons":
                return value / 1e-6;
            case "Ounces-Force":
                return value / 0.278014;
            case "Stones-Force":
                return value / 62.275;
            default:
                return value; // Newtons
        }
    }
}
