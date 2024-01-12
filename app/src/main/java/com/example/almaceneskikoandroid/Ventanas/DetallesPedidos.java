package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.MainActivity.pedidoSeleccionado;
import static com.example.almaceneskikoandroid.Ventanas.login.usuarioLogIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.Cliente;
import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;


public class DetallesPedidos extends AppCompatActivity {

    public static Cliente clientePedido;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedidos);

        MiDBHelper miDBHelper = new MiDBHelper(this);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);

        addBackButtonInToolbar(toolbar2, DetallesPedidos.this);

        Button buttonMapa = findViewById(R.id.buttonMap);
        Button buttonCompletado = findViewById(R.id.buttonCompletado);

        if (!usuarioLogIn.isEmpleado()){
            buttonMapa.setVisibility(View.INVISIBLE);
            buttonCompletado.setVisibility(View.INVISIBLE);
        }


        if(pedidoSeleccionado != null){


            clientePedido = miDBHelper.obtenerCliente(pedidoSeleccionado.getId_cliente());

            TableLayout tableLayout = findViewById(R.id.tableLayout);

            TextView idPedidoTV = findViewById(R.id.id_pedidoVacio);
            TextView idClienteTV = findViewById(R.id.id_clienteVacio);
            TextView idProductoTV = findViewById(R.id.id_productoVacio);
            TextView cantidadTV = findViewById(R.id.cantidadVacio);



            TableRow row = new TableRow(this);


            idPedidoTV.setText(String.valueOf(pedidoSeleccionado.getId_pedido()));



            idClienteTV.setText(String.valueOf(pedidoSeleccionado.getId_cliente()));



            idProductoTV.setText(String.valueOf(pedidoSeleccionado.getId_producto()));



            cantidadTV.setText(String.valueOf(pedidoSeleccionado.getCantidad()));


            tableLayout.addView(row);



            buttonMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetallesPedidos.this, MapsActivity.class);
                    startActivity(intent);
                }
            });

            buttonCompletado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    miDBHelper.eliminarPedido(pedidoSeleccionado.getId_pedido());

                    Toast.makeText(DetallesPedidos.this, "Pedido completado con éxito", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DetallesPedidos.this, MainActivity.class);
                    startActivity(intent);

                }
            });

        }else{
            Toast.makeText(this, "asdas", Toast.LENGTH_SHORT).show();
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
                if (usuarioLogIn.isEmpleado()){
                    Intent i = new Intent(DetallesPedidos.this, MainActivity.class);
                    startActivity(i);
                } else{
                    Intent i = new Intent(DetallesPedidos.this, MainClientesActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}