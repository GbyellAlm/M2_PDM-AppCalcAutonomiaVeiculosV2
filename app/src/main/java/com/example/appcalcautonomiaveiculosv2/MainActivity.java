package com.example.appcalcautonomiaveiculosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int resultadoContaAutonomia = getIntent().getIntExtra("resultado", -1);
        TextView resultAutonomia = findViewById(R.id.tvResultAutonomia);
        resultAutonomia.setText(String.valueOf(resultadoContaAutonomia));
    }

    public void btnAbastecimentos (View v) {
        Intent intecaoAbrirRV = new Intent(this, activity_recycler_view.class);
        startActivity(intecaoAbrirRV);
    }
}
