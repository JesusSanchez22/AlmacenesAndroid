package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.ProductosActivity.productoSeleccionado;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almaceneskikoandroid.MiDBHelper;
import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.functions.Funciones;

public class SignIn extends AppCompatActivity {

    private CheckBox checkBoxPasswordSignIn1, checkBoxPasswordSignIn2;
    private EditText etUserSignIn, etPasswordSignIn1, etPasswordSignIn2, etIdClienteSignIn;

    private MiDBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbHelper = new MiDBHelper(this);

        etUserSignIn = findViewById(R.id.etUserSignIn);
        etPasswordSignIn1 = findViewById(R.id.etPasswordSignIn1);
        etPasswordSignIn2 = findViewById(R.id.etPasswordSignIn2);
        etIdClienteSignIn = findViewById(R.id.etIdClienteSignIn);

        checkBoxPasswordSignIn1 = findViewById(R.id.checkBoxPasswordSignIn1);
        checkBoxPasswordSignIn2 = findViewById(R.id.checkBoxPasswordSignIn2);

        Button btnRegistro = findViewById(R.id.btnSignIn);


        //etPasswordSignIn1.setInputType(129);
        //etPasswordSignIn2.setInputType(129);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUsuario = String.valueOf(etUserSignIn.getText());
                String contrasena1 = String.valueOf(etPasswordSignIn1.getText());
                String contrasena2 = String.valueOf(etPasswordSignIn2.getText());
                String idCliente_string = String.valueOf(etIdClienteSignIn.getText());

                if (idUsuario.isEmpty() || contrasena1.isEmpty() || contrasena2.isEmpty() || idCliente_string.isEmpty()) {
                    Funciones.mostrarToastCorto(SignIn.this, "Falta algún campo");
                } else if (!contrasena1.equals(contrasena2)){
                    Funciones.mostrarToastCorto(SignIn.this, "Las contraseñas no coinciden");
                } else{
                    int idCliente = Integer.parseInt(idCliente_string);

                    try {
                        if (dbHelper.existeUsuario(idUsuario)){
                            Funciones.mostrarToastCorto(SignIn.this, "El usuario ya existe");
                        } else {
                            if (dbHelper.existeCliente(idCliente)){
                                dbHelper.registro(idUsuario, contrasena2, idCliente);
                                Funciones.mostrarToastCorto(SignIn.this, "Registro completado correctamente");
                                Intent intent = new Intent(SignIn.this, login.class);
                                startActivity(intent);
                            } else {
                                Funciones.mostrarToastCorto(SignIn.this, "El identificador de cliente no existe");
                            }
                        }
                    } catch (NumberFormatException e){
                        Funciones.mostrarToastCorto(SignIn.this, "El identificador debe ser un número");
                    }
                }
            }
        });

    }

    public void volverLogIn(View v){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void ShowPasswordSignIn(View v) {

        boolean checked1 = checkBoxPasswordSignIn1.isChecked();
        boolean checked2 = checkBoxPasswordSignIn2.isChecked();

        if(checked1) {
            etPasswordSignIn1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordSignIn1.setInputType(129);
        }

        if(checked2) {
            etPasswordSignIn2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordSignIn2.setInputType(129);
        }
    }
}