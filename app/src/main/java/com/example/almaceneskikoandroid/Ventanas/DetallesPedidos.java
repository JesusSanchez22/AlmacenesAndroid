package com.example.almaceneskikoandroid.Ventanas;

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

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Pedido;
import com.example.almaceneskikoandroid.R;

import java.util.List;

public class DetallesPedidos extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedidos);

        MiDBHelper miDBHelper = new MiDBHelper(this);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);

        addBackButtonInToolbar(toolbar2, DetallesPedidos.this);

        Button buttonMapa = findViewById(R.id.buttonMap);

        List<Pedido> tablaPedidos = miDBHelper.obtenerDatosPedidos();

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        TextView idPedidoTV = findViewById(R.id.id_pedidoVacio);
        TextView idClienteTV = findViewById(R.id.id_clienteVacio);
        TextView idProductoTV = findViewById(R.id.id_productoVacio);
        TextView cantidadTV = findViewById(R.id.cantidadVacio);

        for(Pedido pedido : tablaPedidos){

            TableRow row = new TableRow(this);


            idPedidoTV.setText(String.valueOf(pedido.getId_pedido()));



            idClienteTV.setText(String.valueOf(pedido.getId_cliente()));



            idProductoTV.setText(String.valueOf(pedido.getId_producto()));



            cantidadTV.setText(String.valueOf(pedido.getCantidad()));


            tableLayout.addView(row);

        }

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesPedidos.this, MapsActivity.class);
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
                Intent intent = new Intent(DetallesPedidos.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}