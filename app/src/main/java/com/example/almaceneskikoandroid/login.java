package com.example.almaceneskikoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.almaceneskikoandroid.functions.Funciones;



public class login extends AppCompatActivity {

    private EditText etUserLogin, etPasswordLogin;

    //Perfil 1
    private String user1 = "profesorIES";
    private String user2 = "123";

    //Perfil 2
    private String usuarioAlumno = "alumnoIES";
    private String contrasenaAlumno = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

    }

    public void verificar(View v){

        String usuarioIn = etUserLogin.getText().toString();
        String contrasenaIn = etPasswordLogin.getText().toString();

        if (usuarioIn.isEmpty()) {
            Funciones.mostrarToastCorto(this,"No has metido ningún usuario");
        } else if ((contrasenaIn.isEmpty())) {
            Funciones.mostrarToastCorto(this,"La contraseña no puede estar vacía");
        } else if (usuarioIn.equals(user1) && contrasenaIn.equals(user2)){
            //cambiarActivity(.class);
        } else if (usuarioIn.equals(usuarioAlumno) && contrasenaIn.equals(contrasenaAlumno)){
            //cambiarActivity(.class);
        } else {
            Funciones.mostrarToastCorto(this, "Usuario o Contraseña Incorrectos");
        }
    }
}