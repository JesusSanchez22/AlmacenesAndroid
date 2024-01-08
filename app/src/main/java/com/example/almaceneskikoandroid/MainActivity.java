package com.example.almaceneskikoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        ListView lista = findViewById(R.id.listaNotificaciones);

        ArrayList<String> pedidos = new ArrayList<String>();

        pedidos.add("Bar Kiko");
        pedidos.add("Mesón Paco");
        pedidos.add("Restaurante Guillermo");
        pedidos.add("Bar La Brava");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pedidos);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetallesPedidos.class);
                startActivity(intent);
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

            Toast.makeText(this, "asdasd", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}