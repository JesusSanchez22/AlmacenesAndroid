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

    //variables
    public static Cliente clientePedido;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedidos);

        MiDBHelper miDBHelper = new MiDBHelper(this);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);

        //boton para volver a la ventana anterior en el toolbar
        addBackButtonInToolbar(toolbar2, DetallesPedidos.this);

        Button buttonMapa = findViewById(R.id.buttonMap);
        Button buttonCompletado = findViewById(R.id.buttonCompletado);

        //creamos un if para que si no somos empleados, no nos salgan los botones ni de mapa ni el boton completado
        if (!usuarioLogIn.isEmpleado()){
            buttonMapa.setVisibility(View.INVISIBLE);
            buttonCompletado.setVisibility(View.INVISIBLE);
        }

        //if para asegurarnos de que el pedido en el que hemos clickado no sea nulo
        if(pedidoSeleccionado != null){

            //obtenemos el cliente dado el id, y lo almacenamos en una variable
            clientePedido = miDBHelper.obtenerCliente(pedidoSeleccionado.getId_cliente());


            //creamos un tablelayout para visualizar los datos del pedido
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

            //el boton mapa nos llevara a la ventana de mapas
            buttonMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetallesPedidos.this, MapsActivity.class);
                    startActivity(intent);
                }
            });

            //el boton completado nos eliminará el pedido en la lista de pedidos pendientes
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

    //hacemos los distintos botones del toolbar
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