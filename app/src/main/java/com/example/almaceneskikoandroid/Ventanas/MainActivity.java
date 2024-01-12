package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.login.usuarioLogIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Pedido;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables que vamos a utilizar
    protected static Pedido pedidoSeleccionado;

    private FloatingActionButton flActionButton;

    MiDBHelper miDBHelper;

    //metodo onCreate que se ejecutará cuando se inicie la App
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flActionButton = findViewById(R.id.floatingActionButtonPedidosCliente);

        //metodo que nos llevará a la pantalla de agregar pedidos cuando pulsemos el boton flotante
        flActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarPedido.class);
                startActivity(intent);
            }
        });

        //configuración del toolbar
        Toolbar toolbarMain = findViewById(R.id.toolbarInicio);

        setSupportActionBar(toolbarMain);

        //codigo para que salga "Inicio NombreUsuario" en el toolbar
        getSupportActionBar().setTitle("Inicio  |   " + usuarioLogIn.getIdUsuario());

        miDBHelper = new MiDBHelper(this);

        //creamos una lista y le añadimos los datos de los pedidos
        ListView lista = findViewById(R.id.listaNotificacionesCliente);

        List<Pedido> datosPedidos = miDBHelper.obtenerDatosPedidos();

        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosPedidos);

        lista.setAdapter(adapter);

        //metodo para que al pulsar en uno de los registros de la lista, nos lleve a la ventana de detalles pedidos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetallesPedidos.class);
                startActivity(intent);

                pedidoSeleccionado = (Pedido) lista.getItemAtPosition(position);

            }
        });

    }

    //metodo para crear el menu de opciones en el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuopciones,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //metodo para que al pulsar en una de las opciones, nos cambie de pestaña
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.opcionProductos){
            Intent intent = new Intent(MainActivity.this, ProductosActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}