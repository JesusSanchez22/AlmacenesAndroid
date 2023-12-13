package com.example.almaceneskikoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.almaceneskikoandroid.functions.Funciones;



public class login extends AppCompatActivity {

    private CheckBox checkBox;
    private EditText etUserLogin, etPasswordLogin;

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

    }

    public void ShowPassword(View v) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    etPasswordLogin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    etPasswordLogin.setInputType(129);
                }
            }
        });
    }



    public void verificar(View v){

        String usuarioIn = etUserLogin.getText().toString();
        String contrasenaIn = etPasswordLogin.getText().toString();

        if (usuarioIn.isEmpty()) {
            Funciones.mostrarToastCorto(this,"No has metido ningún usuario");
        } else if ((contrasenaIn.isEmpty())) {
            Funciones.mostrarToastCorto(this,"La contraseña no puede estar vacía");
        } else if (usuarioIn.equals(user1) && contrasenaIn.equals(passwordUser1)){
            Funciones.mostrarToastCorto(this,"Contraseña correcta user1");
            //cambiarActivity(.class);
        } else if (usuarioIn.equals(user2) && contrasenaIn.equals(passwordUser2)){
            Funciones.mostrarToastCorto(this,"Contraseña correcta user2");
            //cambiarActivity(.class);
        } else {
            Funciones.mostrarToastCorto(this, "Usuario o Contraseña Incorrectos");
        }
    }
}