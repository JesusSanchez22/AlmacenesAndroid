package com.example.almaceneskikoandroid.functions;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Funciones extends AppCompatActivity {

    public static void mostrarToastCorto(Context context, String texto){

        Toast notificacion = Toast.makeText(context, texto, Toast.LENGTH_SHORT);

        notificacion.show();
    }
    public void mostrarToastLargo(String texto){

        Toast notificacion = Toast.makeText(this, texto, Toast.LENGTH_LONG);

        notificacion.show();
    }

    /*public static void cambiarActivity(Context context, Class c){
        Intent intent = new Intent(context, c);
        startActivity(intent);
    }*/


}
