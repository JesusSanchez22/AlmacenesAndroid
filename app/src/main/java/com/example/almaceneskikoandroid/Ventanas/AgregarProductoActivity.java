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
import com.example.almaceneskikoandroid.functions.Funciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

@RequiresApi(api = Build.VERSION_CODES.P)
public class AgregarProductoActivity extends AppCompatActivity {

    // Instancia de la clase DBHelper para interactuar con la base de datos
    MiDBHelper dbHelper = new MiDBHelper(this);

    // Variable para almacenar la imagen seleccionada
    byte[] imagen;

    // Variable para representar un producto
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        // Crear un reproductor de sonido para reproducir un sonido al agregar un producto
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sonido);

        // Obtener referencia al botón de la cámara en la interfaz de usuario
        ImageButton button = findViewById(R.id.imageButton);

        // Obtener referencias a los campos de texto en la interfaz de usuario
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etPrecio = findViewById(R.id.etPrecio);
        EditText etCantidad = findViewById(R.id.etCantidad);
        EditText etDescripcion = findViewById(R.id.etDescripción);

        // Configurar el listener para el botón de la cámara
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la cámara para capturar una imagen
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, 100);
            }
        });

        // Configurar la barra de herramientas (Toolbar) y establecer un título
        setSupportActionBar(findViewById(R.id.toolbar3));
        getSupportActionBar().setTitle("Agregar producto");

        // Obtener referencia a la barra de herramientas
        Toolbar toolbar3 = findViewById(R.id.toolbar3);

        // Agregar un botón de retroceso a la barra de herramientas
        addBackButtonInToolbar(toolbar3, AgregarProductoActivity.this);

        // Obtener referencia al botón flotante de confirmación
        FloatingActionButton fbConfirmar = findViewById(R.id.fbConfirmar);

        // Configurar el listener para el botón de confirmación
        fbConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String nombre = String.valueOf(etNombre.getText());
                String precio_string = String.valueOf(etPrecio.getText());
                String cantidad_string = String.valueOf(etCantidad.getText());
                String descripcion = String.valueOf(etDescripcion.getText());

                try {
                    // Convertir el precio y la cantidad a números
                    double precio = Double.parseDouble(precio_string);
                    int cantidad = Integer.parseInt(cantidad_string);

                    // Insertar el producto en la base de datos
                    dbHelper.insertarProductos(nombre, cantidad, precio, descripcion, imagen);

                    // Reproducir un sonido al agregar el producto
                    mediaPlayer.start();

                } catch(NumberFormatException e){
                    // Mostrar un mensaje de error si el precio o la cantidad no son números
                    Funciones.mostrarToastCorto(AgregarProductoActivity.this, "Precio y cantidad deben ser números");
                }

                // Mostrar un mensaje de éxito
                Toast.makeText(AgregarProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                // Ir a la actividad de productos
                Intent intent = new Intent(AgregarProductoActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para agregar un botón de retroceso a la barra de herramientas
    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){

        // Configurar la barra de herramientas
        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Configurar el listener para el botón de retroceso
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de productos al hacer clic en el botón de retroceso
                Intent intent = new Intent(AgregarProductoActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método llamado después de que se capture una imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar que la solicitud sea de captura de imagen y que haya sido exitosa
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Obtener la imagen capturada como un objeto Bitmap
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // Convertir la imagen a un formato de bytes para almacenarla en la base de datos
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagen = stream.toByteArray();
        }
    }
}