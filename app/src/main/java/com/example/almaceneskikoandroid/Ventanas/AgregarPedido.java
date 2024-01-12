package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.login.usuarioLogIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Producto;
import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.functions.Funciones;

import java.util.ArrayList;
import java.util.List;

public class AgregarPedido extends AppCompatActivity {

    // Declaración de variables
    EditText etCantidadPedido, etIdCliente;
    TextView tvIdCliente;
    Button btnAgregarPedido;

    private int id_cliente;

    MiDBHelper miDBHelper;

    // Método onCreate, se ejecuta al iniciar la actividad
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedido);

        // Inicialización de la instancia de MediaPlayer para reproducir un sonido
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sonido);

        // Inicialización de la instancia de MiDBHelper para interactuar con la base de datos
        miDBHelper = new MiDBHelper(this);

        // Obtener referencias a elementos de la interfaz de usuario
        etCantidadPedido = findViewById(R.id.etCantidadPedido);
        etIdCliente = findViewById(R.id.etIdCliente);
        tvIdCliente = findViewById(R.id.tvIdCliente);
        btnAgregarPedido = findViewById(R.id.btnAgregarPedido);

        // Obtener referencia a la barra de herramientas y agregar botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarAddPedido);
        addBackButtonInToolbar(toolbar, AgregarPedido.this);

        // Verificar si el usuario es un empleado y ajustar la visibilidad del campo de ID del cliente
        if (!usuarioLogIn.isEmpleado()){
            etIdCliente.setVisibility(View.INVISIBLE);
            tvIdCliente.setVisibility(View.INVISIBLE);
            id_cliente = usuarioLogIn.getIdCliente();
        }

        // Configurar el spinner para mostrar los productos disponibles
        Spinner spinner = findViewById(R.id.spinnerProducto);

        List<Producto> spinnerProducto = miDBHelper.obtenerDatosProductos();
        List<String> spinnerStrings = new ArrayList<>();

        // Crear una lista de cadenas con los nombres de los productos
        for (Producto producto : spinnerProducto) {
            spinnerStrings.add(producto.toString());
        }

        // Crear un adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerStrings);

        // Configurar el adaptador para el spinner
        spinner.setAdapter(adapter);

        // Configurar el listener para el botón de agregar pedido
        btnAgregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un mensaje de éxito
                Toast.makeText(AgregarPedido.this, "Pedido agregado correctamente", Toast.LENGTH_SHORT).show();

                // Obtener el nombre del producto seleccionado en el spinner
                String nombre_producto_string = spinner.getSelectedItem().toString();
                // Obtener la cantidad ingresada por el usuario
                String cantidad_string = String.valueOf(etCantidadPedido.getText());

                // Obtener información del producto desde la base de datos
                Producto producto = miDBHelper.obtenerProductoPorNombre(nombre_producto_string);
                int id_producto_añadir = producto.getId_producto();

                // Si el usuario es un empleado, obtener el ID del cliente ingresado por el usuario
                if (usuarioLogIn.isEmpleado()) {
                    try {
                        id_cliente = Integer.parseInt(String.valueOf(etIdCliente.getText()));
                    } catch (NumberFormatException e){
                        // Mostrar un mensaje de error si el ID del cliente no es un número
                        Funciones.mostrarToastCorto(AgregarPedido.this, "El ID del cliente tiene que ser un número");
                    }
                }

                // Obtener la cantidad ingresada por el usuario como un número
                int cantidad = Integer.parseInt(cantidad_string);

                // Insertar el pedido en la base de datos
                miDBHelper.insertarPedidos(id_producto_añadir, cantidad, id_cliente);

                // Reproducir un sonido al agregar el pedido
                mediaPlayer.start();

                // Redirigir a la actividad correspondiente según el tipo de usuario
                if (usuarioLogIn.isEmpleado()){
                    Intent i = new Intent(AgregarPedido.this, MainActivity.class);
                    startActivity(i);
                } else{
                    Intent i = new Intent(AgregarPedido.this, MainClientesActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    // Método para agregar un botón de retroceso a la barra de herramientas
    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){
        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad correspondiente según el tipo de usuario al hacer clic en el botón de retroceso
                if (usuarioLogIn.isEmpleado()){
                    Intent i = new Intent(AgregarPedido.this, MainActivity.class);
                    startActivity(i);
                } else{
                    Intent i = new Intent(AgregarPedido.this, MainClientesActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    // Método para cancelar y volver a la actividad principal
    public void cancelar(View v){
        if (usuarioLogIn.isEmpleado()){
            Intent i = new Intent(AgregarPedido.this, MainActivity.class);
            startActivity(i);
        } else{
            Intent i = new Intent(AgregarPedido.this, MainClientesActivity.class);
            startActivity(i);
        }
    }
}
