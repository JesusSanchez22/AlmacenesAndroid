package com.example.almaceneskikoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgregarProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        setSupportActionBar(findViewById(R.id.toolbar3));
        getSupportActionBar().setTitle("Agregar producto");


        Toolbar toolbar3 = findViewById(R.id.toolbar3);

        addBackButtonInToolbar(toolbar3, AgregarProductoActivity.this);


        FloatingActionButton fbConfirmar = findViewById(R.id.fbConfirmar);

        fbConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AgregarProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarProductoActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });

    }
}

