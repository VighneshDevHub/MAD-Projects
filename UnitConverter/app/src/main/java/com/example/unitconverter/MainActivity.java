package com.example.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView cv_tmp, cv_weight, cv_length, cv_speed, cv_volume, cv_time, cv_area, cv_fuel,
            cv_pressure, cv_energy, cv_storage, cv_current, cv_force, cv_freq, cv_resistance,
            cv_power, cv_torque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cv_tmp = findViewById(R.id.cv_tmp);
        cv_weight = findViewById(R.id.cv_weight);
        cv_length = findViewById(R.id.cv_length);
        cv_speed = findViewById(R.id.cv_speed);
        cv_volume = findViewById(R.id.cv_volume);
        cv_time = findViewById(R.id.cv_time);
        cv_area = findViewById(R.id.cv_area);
        cv_fuel = findViewById(R.id.cv_fuel);
        cv_pressure = findViewById(R.id.cv_pressure);
        cv_energy = findViewById(R.id.cv_energy);
        cv_storage = findViewById(R.id.cv_storage);
        cv_current = findViewById(R.id.cv_current);
        cv_force = findViewById(R.id.cv_force);
        cv_freq = findViewById(R.id.cv_frequency);
        cv_resistance = findViewById(R.id.cv_resistence);
        cv_power = findViewById(R.id.cv_power);
        cv_torque = findViewById(R.id.cv_torque);

        // Set onClick listeners for each CardView
        cv_tmp.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureConversion.class)));
        cv_weight.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WeightConversion.class)));
        cv_length.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LengthConversion.class)));
        cv_speed.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SpeedConversion.class)));
        cv_volume.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, VolumeConversion.class)));
        cv_time.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TimeConversion.class)));
        cv_area.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AreaConversion.class)));
        cv_fuel.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FuelConversion.class)));
        cv_pressure.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PressureConversion.class)));
        cv_energy.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EnergyConversion.class)));
        cv_storage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StorageConversion.class)));
        cv_current.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CurrentConversion.class)));
        cv_force.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ForceConversion.class)));
        cv_freq.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FrequencyConversion.class)));
        cv_resistance.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ResistanceConversion.class)));
        cv_power.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PowerConversion.class)));
        cv_torque.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TorqueConversion.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterCards(newText);
                    return true;
                }
            });
        } else {
            Log.e("MainActivity", "SearchView is null");
        }

        return true;
    }

    private void filterCards(String query) {
        query = query.toLowerCase().trim();
        CardView[] cards = {cv_tmp, cv_weight, cv_length, cv_speed, cv_volume, cv_time,
                cv_area, cv_fuel, cv_pressure, cv_energy, cv_storage, cv_current,
                cv_force, cv_freq, cv_resistance, cv_power, cv_torque};

        for (CardView card : cards) {
            String cardName = card.getContentDescription() != null ? card.getContentDescription().toString().toLowerCase() : "";
            card.setVisibility(cardName.contains(query) ? View.VISIBLE : View.GONE);
        }
    }
}
