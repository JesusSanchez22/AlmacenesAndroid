package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.Usuario;
import com.example.almaceneskikoandroid.functions.Funciones;



public class login extends AppCompatActivity {

    protected static Usuario usuarioLogIn;
    private CheckBox checkBox;
    private EditText etUserLogin, etPasswordLogin;
    private TextView txtRegistro;

    //Perfil 1
    private String user1 = "jesusm";
    private String passwordUser1 = "123";

    MiDBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MiDBHelper(this);

        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

        checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(false);

        etUserLogin.setText(user1);
        etPasswordLogin.setText(passwordUser1);

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
            Funciones.mostrarToastCorto(this,"No has introducido ningún usuario");
        } else if ((contrasenaIn.isEmpty())) {
            Funciones.mostrarToastCorto(this,"La contraseña no puede estar vacía");
        } else if (dbHelper.existeUsuario(usuarioIn) && dbHelper.comprobarContrasena(usuarioIn, contrasenaIn)){

            usuarioLogIn = dbHelper.obtenerUsuario(usuarioIn);

            if (usuarioLogIn.isEmpleado()){
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else{
                Intent i = new Intent(this, MainClientesActivity.class);
                startActivity(i);
            }

        } else {
            Funciones.mostrarToastCorto(this, "Usuario o Contraseña Incorrectos");
        }
    }

    public void irRegistro(View v){
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}