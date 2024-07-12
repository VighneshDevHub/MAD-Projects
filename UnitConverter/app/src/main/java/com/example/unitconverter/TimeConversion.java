package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TimeConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Seconds";
    private String toUnit = "Minutes";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Seconds", "Minutes", "Hours",
            "Days", "Weeks", "Years",
            "Milliseconds", "Microseconds", "Nanoseconds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.time_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[1]);

        cv_convert.setOnClickListener(v -> convertTime());

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

    private void convertTime() {
        String timeInput = et_fromUnit.getText().toString();
        if (timeInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(timeInput);
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
        double seconds = toSeconds(value, from);
        return fromSeconds(seconds, to);
    }

    private double toSeconds(double value, String from) {
        switch (from) {
            case "Minutes":
                return value * 60;
            case "Hours":
                return value * 3600;
            case "Days":
                return value * 86400;
            case "Weeks":
                return value * 604800;
            case "Years":
                return value * 31536000;
            case "Milliseconds":
                return value / 1000;
            case "Microseconds":
                return value / 1e6;
            case "Nanoseconds":
                return value / 1e9;
            default:
                return value; // Seconds
        }
    }

    private double fromSeconds(double seconds, String to) {
        switch (to) {
            case "Minutes":
                return seconds / 60;
            case "Hours":
                return seconds / 3600;
            case "Days":
                return seconds / 86400;
            case "Weeks":
                return seconds / 604800;
            case "Years":
                return seconds / 31536000;
            case "Milliseconds":
                return seconds * 1000;
            case "Microseconds":
                return seconds * 1e6;
            case "Nanoseconds":
                return seconds * 1e9;
            default:
                return seconds; // Seconds
        }
    }
}
