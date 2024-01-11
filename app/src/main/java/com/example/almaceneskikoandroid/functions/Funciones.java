package com.example.almaceneskikoandroid.functions;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.almaceneskikoandroid.Ventanas.DetallesPedidos;
import com.example.almaceneskikoandroid.Ventanas.MainActivity;

public class Funciones extends AppCompatActivity {

    public static void mostrarToastCorto(Context context, String texto){

        Toast notificacion = Toast.makeText(context, texto, Toast.LENGTH_SHORT);

        notificacion.show();
    }
    public static void mostrarToastLargo(Context context, String texto){

        Toast notificacion = Toast.makeText(context, texto, Toast.LENGTH_LONG);

        notificacion.show();
    }

    /*public static void cambiarActivity(Context context, Class c){
        Intent intent = new Intent(context, c);
        startActivity(intent);
    }*/

    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity, Context context){

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
