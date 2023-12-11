package com.example.almaceneskikoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

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

        if(id==R.id.opcionMapa){
            Toast.makeText(this, "Se seleccionó la tercera opción", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}