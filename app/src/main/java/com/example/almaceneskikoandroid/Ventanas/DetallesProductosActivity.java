package com.example.almaceneskikoandroid.Ventanas;

import static android.content.ContentValues.TAG;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.cantidad_producto_static;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.descripcion_producto_static;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.id_producto_static;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.imagen_producto_static;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.nombre_producto_static;
import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.precio_producto_static;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;

public class DetallesProductosActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_productos);

        setSupportActionBar(findViewById(R.id.toolbarDetallesProductos));
        getSupportActionBar().setTitle("Detalle del producto");

        Toolbar toolbar = findViewById(R.id.toolbarDetallesProductos);
        addBackButtonInToolbar(toolbar, DetallesProductosActivity.this);


        EditText etID_Detalles = findViewById(R.id.etID_Detalles);
        EditText etNombre_Detalles = findViewById(R.id.etNombre_Detalles);
        EditText etPrecio_Detalles = findViewById(R.id.etPrecio_Detalles);
        EditText etCantidad_Detalles = findViewById(R.id.etCantidad_Detalles);
        EditText etDescripcion_Detalles = findViewById(R.id.etDescripcion_Detalles);
        ImageView imageView = findViewById(R.id.imageView);

        MiDBHelper miDBHelper = new MiDBHelper(this);


        String idString = String.valueOf(id_producto_static);
        String precioString = String.valueOf(precio_producto_static);
        String cantidadString = String.valueOf(cantidad_producto_static);


        etID_Detalles.setText(idString);
        etNombre_Detalles.setText(nombre_producto_static);
        etPrecio_Detalles.setText(precioString);
        etCantidad_Detalles.setText(cantidadString);
        etDescripcion_Detalles.setText(descripcion_producto_static);

        if (imagen_producto_static != null && imagen_producto_static.length > 0) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagen_producto_static, 0, imagen_producto_static.length);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "Error al decodificar la imagen");
                    Toast.makeText(this, "Error al decodificar la imagen", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Excepción al decodificar la imagen: " + e.getMessage());
                Toast.makeText(this, "Excepción al decodificar la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "Datos de imagen vacíos");
            Toast.makeText(this, "Datos de imagen vacíos", Toast.LENGTH_SHORT).show();
        }


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
                Intent intent = new Intent(DetallesProductosActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });

    }

}