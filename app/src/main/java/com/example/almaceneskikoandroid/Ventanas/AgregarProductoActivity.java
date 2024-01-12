package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Producto;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

@RequiresApi(api = Build.VERSION_CODES.P)
public class AgregarProductoActivity extends AppCompatActivity {

    MiDBHelper dbHelper = new MiDBHelper(this);
    byte[] imagen;

    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sonido);

        ImageButton button = findViewById(R.id.imageButton);

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etPrecio = findViewById(R.id.etPrecio);
        EditText etCantidad = findViewById(R.id.etCantidad);
        EditText etDescripcion = findViewById(R.id.etDescripción);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, 100);
            }
        });

        setSupportActionBar(findViewById(R.id.toolbar3));
        getSupportActionBar().setTitle("Agregar producto");


        Toolbar toolbar3 = findViewById(R.id.toolbar3);

        addBackButtonInToolbar(toolbar3, AgregarProductoActivity.this);


        FloatingActionButton fbConfirmar = findViewById(R.id.fbConfirmar);

        fbConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AgregarProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                String nombre = String.valueOf(etNombre.getText());
                String precio_string = String.valueOf(etPrecio.getText());
                String cantidad_string = String.valueOf(etCantidad.getText());
                String descripcion = String.valueOf(etDescripcion.getText());

                double precio = Double.parseDouble(precio_string);

                int cantidad = Integer.parseInt(cantidad_string);

                dbHelper.insertarProductos(nombre, cantidad, precio, descripcion, imagen);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagen = stream.toByteArray();

        }


    }
}
