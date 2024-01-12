package com.example.almaceneskikoandroid.Ventanas;

import static android.content.ContentValues.TAG;

import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.productoSeleccionado;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;

import java.io.ByteArrayOutputStream;

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

        Button buttonModificar = findViewById(R.id.buttonModificar);
        Button buttonEliminar = findViewById(R.id.buttonEliminar);

        EditText etID_Detalles = findViewById(R.id.etID_Detalles);
        EditText etNombre_Detalles = findViewById(R.id.etNombre_Detalles);
        EditText etPrecio_Detalles = findViewById(R.id.etPrecio_Detalles);
        EditText etCantidad_Detalles = findViewById(R.id.etCantidad_Detalles);
        EditText etDescripcion_Detalles = findViewById(R.id.etDescripcion_Detalles);
        ImageView imageView = findViewById(R.id.imageView);

        MiDBHelper miDBHelper = new MiDBHelper(this);


        etID_Detalles.setText(String.valueOf(productoSeleccionado.getId_producto()));
        etNombre_Detalles.setText(productoSeleccionado.getNombre());
        etPrecio_Detalles.setText(String.valueOf(productoSeleccionado.getPrecio()));
        etCantidad_Detalles.setText(String.valueOf(productoSeleccionado.getCantidad()));
        etDescripcion_Detalles.setText(productoSeleccionado.getDescripcion());


        if(productoSeleccionado.getImagen()!=null && productoSeleccionado.getImagen().length > 0){

            Bitmap bitmap = BitmapFactory.decodeByteArray(productoSeleccionado.getImagen(), 0, productoSeleccionado.getImagen().length);
            imageView.setImageBitmap(bitmap);

        }




        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = Integer.parseInt(String.valueOf(etID_Detalles.getText()));
                String nombre = String.valueOf(etNombre_Detalles.getText());
                double precio = Double.parseDouble(String.valueOf(etPrecio_Detalles.getText()));
                int cantidad = Integer.parseInt(String.valueOf(etCantidad_Detalles.getText()));
                String descripcion = String.valueOf(etDescripcion_Detalles.getText());
                //byte[] imagen = imageView.

                miDBHelper.modificarProducto(id, nombre, precio, cantidad, descripcion, productoSeleccionado.getImagen());

            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(buttonEliminar.getContext());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Estás seguro de eliminar este producto?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        miDBHelper.eliminarProducto(productoSeleccionado.getId_producto());

                        Toast.makeText(DetallesProductosActivity.this, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DetallesProductosActivity.this, ProductosActivity.class);
                        startActivity(intent);

                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                dialogo1.show();

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
                Intent intent = new Intent(DetallesProductosActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });

    }

}