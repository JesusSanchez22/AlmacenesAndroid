package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.login.usuarioLogIn;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Pedido;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainClientesActivity extends AppCompatActivity {

    // Variable para almacenar el pedido seleccionado (podría ser usado para futuras implementaciones)
    public static Pedido pedidoSeleccionado;

    // Botón flotante para agregar pedidos
    private FloatingActionButton flActionButton;

    // Instancia de la clase DBHelper para interactuar con la base de datos
    MiDBHelper miDBHelper;

    // Método onCreate, se ejecuta al iniciar la actividad
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        // Obtener referencia al botón flotante de agregar pedidos
        flActionButton = findViewById(R.id.floatingActionButtonPedidosCliente);

        // Configurar el listener para el botón flotante
        flActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad para agregar un nuevo pedido
                Intent intent = new Intent(MainClientesActivity.this, AgregarPedido.class);
                startActivity(intent);
            }
        });

        // Obtener referencia a la barra de herramientas
        Toolbar toolbarMainClientes = findViewById(R.id.toolbarInicio);

        // Configurar la barra de herramientas y establecer el título
        setSupportActionBar(toolbarMainClientes);
        getSupportActionBar().setTitle("Inicio  |   " + usuarioLogIn.getIdUsuario());

        // Inicialización de la instancia de MiDBHelper para interactuar con la base de datos
        miDBHelper = new MiDBHelper(this);

        // Obtener referencia a la lista de notificaciones del cliente en la interfaz de usuario
        ListView listaCliente = findViewById(R.id.listaNotificacionesCliente);

        // Obtener la lista de pedidos para el usuario actual
        List<Pedido> datosPedidos = miDBHelper.obtenerDatosPedidosUsuario(usuarioLogIn.getIdUsuario());

        // Crear un adaptador para la lista de pedidos
        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosPedidos);

        // Configurar el adaptador para la lista de pedidos
        listaCliente.setAdapter(adapter);

        // Configurar el listener para los elementos de la lista de pedidos
        listaCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // En un futuro, se agregarán detalles del pedido seleccionado
            }
        });
    }

    // Método para inflar el menú de opciones en la barra de herramientas
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuopciones_cliente,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Método para manejar las opciones del menú de la barra de herramientas
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        // Verificar la opción seleccionada en el menú
        if(id==R.id.opcionInfo){
            // Abrir la actividad de perfil del cliente
            Intent intent = new Intent(MainClientesActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
