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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.Pedido;
import com.example.almaceneskikoandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainClientesActivity extends AppCompatActivity {

    public static Pedido pedidoSeleccionado;

    private FloatingActionButton flActionButton;

    MiDBHelper miDBHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        flActionButton = findViewById(R.id.floatingActionButtonPedidosCliente);

        flActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainClientesActivity.this, AgregarPedido.class);
                startActivity(intent);
            }
        });

        Toolbar toolbarMainClientes = findViewById(R.id.toolbarInicio);

        setSupportActionBar(toolbarMainClientes);
        getSupportActionBar().setTitle("Inicio  |   " + usuarioLogIn.getIdUsuario());

        miDBHelper = new MiDBHelper(this);

        ListView listaCliente = findViewById(R.id.listaNotificacionesCliente);

        List<Pedido> datosPedidos = miDBHelper.obtenerDatosPedidosUsuario(usuarioLogIn.getIdUsuario());

        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datosPedidos);

        listaCliente.setAdapter(adapter);

        listaCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainClientesActivity.this, DetallesPedidos.class);
                startActivity(intent);

                pedidoSeleccionado = (Pedido) listaCliente.getItemAtPosition(position);

            }
        });

    }

}