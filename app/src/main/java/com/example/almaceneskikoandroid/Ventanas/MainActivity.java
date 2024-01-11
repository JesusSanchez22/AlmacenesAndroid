package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Pedido;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Pedido pedidoSeleccionado;

    private FloatingActionButton flActionButton;

    MiDBHelper miDBHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flActionButton = findViewById(R.id.floatingActionButtonPedidos);

        flActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarPedido.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(findViewById(R.id.toolbar));

        miDBHelper = new MiDBHelper(this);

        ListView lista = findViewById(R.id.listaNotificaciones);

        List<Pedido> datosPedidos = miDBHelper.obtenerDatosPedidos();

        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosPedidos);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetallesPedidos.class);
                startActivity(intent);

                pedidoSeleccionado = (Pedido) lista.getItemAtPosition(position);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuopciones,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.opcionProductos){

            Intent intent = new Intent(MainActivity.this, ProductosActivity.class);
            startActivity(intent);
        }

        if(id==R.id.opcionClientes){


        }

        if(id==R.id.opcionConfiguracion){



        }

        return super.onOptionsItemSelected(item);
    }
}