package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Producto;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductosActivity extends AppCompatActivity {

    //variables
    protected static Producto productoSeleccionado;
    MiDBHelper miDBHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        miDBHelper = new MiDBHelper(this);

        //configuramos el toolbar
        setSupportActionBar(findViewById(R.id.toolbar2));
        getSupportActionBar().setTitle("Productos");

        //creamos un floating button en el que al hacer click te llevará a la ventana de agregar producto
        FloatingActionButton fbAgregar = findViewById(R.id.fbAgregar);

        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductosActivity.this, AgregarProductoActivity.class);
                startActivity(intent);
            }
        });

        //creamos una lista y le añadimos los datos de los productos
        ListView lista = findViewById(R.id.ListView);

        List<Producto> datosProductos = miDBHelper.obtenerDatosProductos();

        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosProductos);

        lista.setAdapter(adapter);

        //haremos que al clickar en alguno de los registros de la lista, se guarde dicho producto en una variable,
        // y nos lleve a DetallesProductosActivity
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                productoSeleccionado = (Producto)lista.getItemAtPosition(position);

                Intent intent = new Intent(ProductosActivity.this, DetallesProductosActivity.class);
                startActivity(intent);

            }
        });

        Toolbar toolbar2 = findViewById(R.id.toolbar2);

        addBackButtonInToolbar(toolbar2, ProductosActivity.this);


    }


    //metodo para poner un boton para volver atrás en el toolbar
    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}