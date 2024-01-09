package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

@RequiresApi(api = Build.VERSION_CODES.P)
public class AgregarProductoActivity extends AppCompatActivity {

    MiDBHelper dbHelper = new MiDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sonido);


        EditText etId = findViewById(R.id.etID);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etPrecio = findViewById(R.id.etPrecio);
        EditText etCantidad = findViewById(R.id.etCantidad);
        EditText etDescripcion = findViewById(R.id.etDescripción);

        setSupportActionBar(findViewById(R.id.toolbar3));
        getSupportActionBar().setTitle("Agregar producto");


        Toolbar toolbar3 = findViewById(R.id.toolbar3);

        addBackButtonInToolbar(toolbar3, AgregarProductoActivity.this);


        FloatingActionButton fbConfirmar = findViewById(R.id.fbConfirmar);

        fbConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AgregarProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                String id_string = String.valueOf(etId.getText());
                String nombre = String.valueOf(etNombre.getText());
                String precio_string = String.valueOf(etPrecio.getText());
                String cantidad_string = String.valueOf(etCantidad.getText());
                String descripcion = String.valueOf(etDescripcion.getText());

                int id = Integer.parseInt(id_string);
                double precio = Double.parseDouble(precio_string);
                int cantidad = Integer.parseInt(cantidad_string);

                dbHelper.insertarProductos(id, nombre, precio, cantidad, descripcion);

                mediaPlayer.start();

                Intent intent = new Intent(AgregarProductoActivity.this, ProductosActivity.class);
                startActivity(intent);

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

