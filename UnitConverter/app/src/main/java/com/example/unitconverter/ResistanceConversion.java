package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ResistanceConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Ohms";
    private String toUnit = "Kiloohms";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Ohms", "Kiloohms", "Megaohms", "Gigaohms", "Milli-ohms", "Micro-ohms"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistance_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.resistance_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertResistance());

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

    private void convertResistance() {
        String resistanceInput = et_fromUnit.getText().toString();
        if (resistanceInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(resistanceInput);
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
        double ohms = toOhms(value, from);
        return fromOhms(ohms, to);
    }

    private double toOhms(double value, String from) {
        switch (from) {
            case "Kiloohms":
                return value * 1e3;
            case "Megaohms":
                return value * 1e6;
            case "Gigaohms":
                return value * 1e9;
            case "Milli-ohms":
                return value * 1e-3;
            case "Micro-ohms":
                return value * 1e-6;
            default:
                return value; // Ohms
        }
    }

    private double fromOhms(double ohms, String to) {
        switch (to) {
            case "Kiloohms":
                return ohms / 1e3;
            case "Megaohms":
                return ohms / 1e6;
            case "Gigaohms":
                return ohms / 1e9;
            case "Milli-ohms":
                return ohms / 1e-3;
            case "Micro-ohms":
                return ohms / 1e-6;
            default:
                return ohms; // Ohms
        }
    }
}
