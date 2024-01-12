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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.functions.Funciones;

public class AgregarPedido extends AppCompatActivity {

    EditText etIdProductoPedido, etCantidadPedido, etIdCliente;
    TextView tvIdCliente;
    Button btnAgregarPedido;

    private int id_cliente;

    private MiDBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedido);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sonido);

        dbHelper = new MiDBHelper(this);

        etIdProductoPedido = findViewById(R.id.etIdProductoPedido);
        etCantidadPedido = findViewById(R.id.etCantidadPedido);
        etIdCliente = findViewById(R.id.etIdCliente);
        tvIdCliente = findViewById(R.id.tvIdCliente);
        btnAgregarPedido = findViewById(R.id.btnAgregarPedido);

        Toolbar toolbar = findViewById(R.id.toolbarAddPedido);
        addBackButtonInToolbar(toolbar, AgregarPedido.this);

        if (!usuarioLogIn.isEmpleado()){
            etIdCliente.setVisibility(View.INVISIBLE);
            tvIdCliente.setVisibility(View.INVISIBLE);
            id_cliente = usuarioLogIn.getIdCliente();
        }

        btnAgregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AgregarPedido.this, "Pedido agregado correctamente", Toast.LENGTH_SHORT).show();

                String id_producto_string = String.valueOf(etIdProductoPedido.getText());
                String cantidad_string = String.valueOf(etCantidadPedido.getText());

                if (usuarioLogIn.isEmpleado()) {
                    try {
                        id_cliente = Integer.parseInt(String.valueOf(etIdCliente.getText()));
                    } catch (NumberFormatException e){
                        Funciones.mostrarToastCorto(AgregarPedido.this, "El id del cliente tiene que ser un número");
                    }
                }

                int id_producto = Integer.parseInt(id_producto_string);
                int cantidad = Integer.parseInt(cantidad_string);

                dbHelper.insertarPedidos(id_producto, cantidad, id_cliente);

                mediaPlayer.start();

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

    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}