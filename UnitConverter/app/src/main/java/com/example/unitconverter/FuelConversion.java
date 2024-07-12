package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FuelConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Kilometers per Liter";
    private String toUnit = "Miles per Gallon (US)";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Kilometers per Liter", "Liters per 100 Kilometers", "Miles per Gallon (US)",
            "Miles per Gallon (UK)", "Liters per 100 Miles", "Gallons per 100 Miles",
            "Kilometers per Gallon (US)", "Kilometers per Gallon (UK)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.fuel_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[2]);

        cv_convert.setOnClickListener(v -> convertFuel());

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

    private void convertFuel() {
        String fuelInput = et_fromUnit.getText().toString();
        if (fuelInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(fuelInput);
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
        double fromKilometersPerLiter = toKilometersPerLiter(value, from);
        return fromKilometersPerLiter(fromKilometersPerLiter, to);
    }

    private double toKilometersPerLiter(double value, String from) {
        switch (from) {
            case "Liters per 100 Kilometers":
                return 100 / value;
            case "Miles per Gallon (US)":
                return value * 0.425144;
            case "Miles per Gallon (UK)":
                return value * 0.354006;
            case "Liters per 100 Miles":
                return 62.1371 / value;
            case "Gallons per 100 Miles":
                return 235.215 / value;
            case "Kilometers per Gallon (US)":
                return value * 0.621371;
            case "Kilometers per Gallon (UK)":
                return value * 0.832674;
            default:
                return value; // Kilometers per Liter
        }
    }

    private double fromKilometersPerLiter(double value, String to) {
        switch (to) {
            case "Liters per 100 Kilometers":
                return 100 / value;
            case "Miles per Gallon (US)":
                return value * 2.35215;
            case "Miles per Gallon (UK)":
                return value * 2.82481;
            case "Liters per 100 Miles":
                return 62.1371 / value;
            case "Gallons per 100 Miles":
                return 235.215 / value;
            case "Kilometers per Gallon (US)":
                return value * 1.60934;
            case "Kilometers per Gallon (UK)":
                return value * 1.20095;
            default:
                return value; // Kilometers per Liter
        }
    }
}
