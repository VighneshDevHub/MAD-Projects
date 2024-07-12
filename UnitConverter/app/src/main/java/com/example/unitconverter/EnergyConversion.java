package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EnergyConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Joules";
    private String toUnit = "Calories";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Joules", "Kilojoules", "Megajoules", "Gigajoules",
            "Electron Volts", "Calories", "Kilocalories", "BTUs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.energy_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[5]);

        cv_convert.setOnClickListener(v -> convertEnergy());

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

    private void convertEnergy() {
        String energyInput = et_fromUnit.getText().toString();
        if (energyInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(energyInput);
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
        double joules = toJoules(value, from);
        return fromJoules(joules, to);
    }

    private double toJoules(double value, String from) {
        switch (from) {
            case "Kilojoules":
                return value * 1000;
            case "Megajoules":
                return value * 1e6;
            case "Gigajoules":
                return value * 1e9;
            case "Electron Volts":
                return value * 1.60218e-19;
            case "Calories":
                return value * 4.184;
            case "Kilocalories":
                return value * 4184;
            case "BTUs":
                return value * 1055.06;
            default:
                return value; // Joules
        }
    }

    private double fromJoules(double value, String to) {
        switch (to) {
            case "Kilojoules":
                return value / 1000;
            case "Megajoules":
                return value / 1e6;
            case "Gigajoules":
                return value / 1e9;
            case "Electron Volts":
                return value / 1.60218e-19;
            case "Calories":
                return value / 4.184;
            case "Kilocalories":
                return value / 4184;
            case "BTUs":
                return value / 1055.06;
            default:
                return value; // Joules
        }
    }
}
