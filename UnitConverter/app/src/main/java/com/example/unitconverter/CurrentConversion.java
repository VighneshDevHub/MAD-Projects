package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CurrentConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Amperes";
    private String toUnit = "Milliamperes";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Amperes", "Milliamperes", "Microamperes",
            "Kiloamperes", "Megaamperes", "Gigaamperes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.current_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertCurrent());

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

    private void convertCurrent() {
        String currentInput = et_fromUnit.getText().toString();
        if (currentInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(currentInput);
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
        double amperes = toAmperes(value, from);
        return fromAmperes(amperes, to);
    }

    private double toAmperes(double value, String from) {
        switch (from) {
            case "Milliamperes":
                return value / 1000;
            case "Microamperes":
                return value / 1_000_000;
            case "Kiloamperes":
                return value * 1_000;
            case "Megaamperes":
                return value * 1_000_000;
            case "Gigaamperes":
                return value * 1_000_000_000;
            default:
                return value; // Amperes
        }
    }

    private double fromAmperes(double value, String to) {
        switch (to) {
            case "Milliamperes":
                return value * 1000;
            case "Microamperes":
                return value * 1_000_000;
            case "Kiloamperes":
                return value / 1_000;
            case "Megaamperes":
                return value / 1_000_000;
            case "Gigaamperes":
                return value / 1_000_000_000;
            default:
                return value; // Amperes
        }
    }
}
