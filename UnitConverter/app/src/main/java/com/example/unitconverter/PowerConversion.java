package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PowerConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Watts";
    private String toUnit = "Kilowatts";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Watts", "Kilowatts", "Megawatts", "Gigawatts",
            "Horsepower", "Kilocalories per Hour", "BTU per Hour",
            "Foot-pounds per Minute", "Joules per Second"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.power_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertPower());

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

    private void convertPower() {
        String powerInput = et_fromUnit.getText().toString();
        if (powerInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(powerInput);
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
        double watts = toWatts(value, from);
        return fromWatts(watts, to);
    }

    private double toWatts(double value, String from) {
        switch (from) {
            case "Kilowatts":
                return value * 1e3;
            case "Megawatts":
                return value * 1e6;
            case "Gigawatts":
                return value * 1e9;
            case "Horsepower":
                return value * 745.699872;
            case "Kilocalories per Hour":
                return value * 1.16222;
            case "BTU per Hour":
                return value * 0.293071;
            case "Foot-pounds per Minute":
                return value * 0.022597;
            case "Joules per Second":
                return value; // Same as Watts
            default:
                return value; // Watts
        }
    }

    private double fromWatts(double watts, String to) {
        switch (to) {
            case "Kilowatts":
                return watts / 1e3;
            case "Megawatts":
                return watts / 1e6;
            case "Gigawatts":
                return watts / 1e9;
            case "Horsepower":
                return watts / 745.699872;
            case "Kilocalories per Hour":
                return watts / 1.16222;
            case "BTU per Hour":
                return watts / 0.293071;
            case "Foot-pounds per Minute":
                return watts / 0.022597;
            case "Joules per Second":
                return watts; // Same as Watts
            default:
                return watts; // Watts
        }
    }
}
