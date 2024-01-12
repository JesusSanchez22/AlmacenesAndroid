package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.login.usuarioLogIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.Cliente;
import com.example.almaceneskikoandroid.Usuario;

public class ProfileActivity extends AppCompatActivity {

    private EditText etNombreCliente;
    private EditText etNombreUsuario;
    private MiDBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        addBackButtonInToolbar(toolbar, ProfileActivity.this);


        // Obtener referencias a los elementos de la interfaz de usuario
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etNombreUsuario = findViewById(R.id.etNombreCliente2);

        // Inicializar el dbHelper

        dbHelper = new MiDBHelper(this);



        // Obtener la información del cliente y usuario desde la base de datos

        Cliente id = dbHelper.obtenerCliente(usuarioLogIn.getIdCliente());


        etNombreCliente.setText(id.toString());
        etNombreUsuario.setText(usuarioLogIn.getIdUsuario());



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
                // Redirigir a la actividad correspondiente según el tipo de usuario al hacer clic en el botón de retroceso

                    Intent i = new Intent(ProfileActivity.this, MainClientesActivity.class);
                    startActivity(i);

            }
        });
    }

}
