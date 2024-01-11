package com.example.almaceneskikoandroid.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.functions.Funciones;



public class login extends AppCompatActivity {

    private CheckBox checkBox;
    private EditText etUserLogin, etPasswordLogin;
    private TextView txtRegistro;

    //Perfil 1
    private String user1 = "raul";
    private String passwordUser1 = "123";

    //Perfil 2
    private String user2 = "jesus";
    private String passwordUser2 = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

        checkBox = findViewById(R.id.checkBox);

        checkBox.setChecked(false);

        //txtRegistro = findViewById(R.id.txtRegistro);

    }

    public void ShowPasswordLogIn(View v) {
        boolean checked = checkBox.isChecked();

        if(checked) {
            etPasswordLogin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordLogin.setInputType(129);
        }
    }

    public void verificar(View v){

        String usuarioIn = etUserLogin.getText().toString();
        String contrasenaIn = etPasswordLogin.getText().toString();

        if (usuarioIn.isEmpty()) {
            Funciones.mostrarToastCorto(this,"No has metido ningún usuario");
        } else if ((contrasenaIn.isEmpty())) {
            Funciones.mostrarToastCorto(this,"La contraseña no puede estar vacía");
        } else if (usuarioIn.equals(user1) && contrasenaIn.equals(passwordUser1)){

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        } else if (usuarioIn.equals(user2) && contrasenaIn.equals(passwordUser2)){

            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);

        } else {
            Funciones.mostrarToastCorto(this, "Usuario o Contraseña Incorrectos");
        }
    }

    public void irRegistro(View v){
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}