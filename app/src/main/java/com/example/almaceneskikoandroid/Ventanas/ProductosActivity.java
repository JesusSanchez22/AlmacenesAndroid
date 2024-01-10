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
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Producto;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductosActivity extends AppCompatActivity {

    public static Integer id_producto_static;
    public static String nombre_producto_static;
    public static Double precio_producto_static;
    public static Integer cantidad_producto_static;
    public static String descripcion_producto_static;
    public static byte[] imagen_producto_static;
    MiDBHelper miDBHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        miDBHelper = new MiDBHelper(this);

        setSupportActionBar(findViewById(R.id.toolbar2));
        getSupportActionBar().setTitle("Productos");


        FloatingActionButton fbAgregar = findViewById(R.id.fbAgregar);

        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductosActivity.this, AgregarProductoActivity.class);
                startActivity(intent);
            }
        });


        ListView lista = findViewById(R.id.ListView);

        List<Producto> datosProductos = miDBHelper.obtenerDatosProductos();

        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosProductos);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ProductosActivity.this, DetallesProductosActivity.class);
                startActivity(intent);

                Producto productoSeleccionado = (Producto)lista.getItemAtPosition(position);

                id_producto_static = productoSeleccionado.getId_producto();
                nombre_producto_static = productoSeleccionado.getNombre();
                precio_producto_static = productoSeleccionado.getPrecio();
                cantidad_producto_static = productoSeleccionado.getCantidad();
                descripcion_producto_static = productoSeleccionado.getDescripcion();
                imagen_producto_static = productoSeleccionado.getImagen();



            }
        });

        Toolbar toolbar2 = findViewById(R.id.toolbar2);

        addBackButtonInToolbar(toolbar2, ProductosActivity.this);


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
                Intent intent = new Intent(ProductosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}