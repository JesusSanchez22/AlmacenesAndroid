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

    // Declaración de variables para los elementos de la interfaz de usuario
    private CheckBox checkBoxPasswordSignIn1, checkBoxPasswordSignIn2;
    private EditText etUserSignIn, etPasswordSignIn1, etPasswordSignIn2, etIdClienteSignIn;

    private MiDBHelper dbHelper;

    // Método onCreate, se ejecuta al iniciar la actividad
    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Inicialización de la instancia de MiDBHelper para interactuar con la base de datos
        dbHelper = new MiDBHelper(this);

        // Obtener referencias a los elementos de la interfaz de usuario
        etUserSignIn = findViewById(R.id.etUserSignIn);
        etPasswordSignIn1 = findViewById(R.id.etPasswordSignIn1);
        etPasswordSignIn2 = findViewById(R.id.etPasswordSignIn2);
        etIdClienteSignIn = findViewById(R.id.etIdClienteSignIn);

        checkBoxPasswordSignIn1 = findViewById(R.id.checkBoxPasswordSignIn1);
        checkBoxPasswordSignIn2 = findViewById(R.id.checkBoxPasswordSignIn2);

        Button btnRegistro = findViewById(R.id.btnSignIn);

        // Configurar el listener para el botón de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de entrada
                String idUsuario = String.valueOf(etUserSignIn.getText());
                String contrasena1 = String.valueOf(etPasswordSignIn1.getText());
                String contrasena2 = String.valueOf(etPasswordSignIn2.getText());
                String idCliente_string = String.valueOf(etIdClienteSignIn.getText());

                // Validar si algún campo está vacío
                if (idUsuario.isEmpty() || contrasena1.isEmpty() || contrasena2.isEmpty() || idCliente_string.isEmpty()) {
                    Funciones.mostrarToastCorto(SignIn.this, "Falta algún campo");
                } else if (!contrasena1.equals(contrasena2)){
                    Funciones.mostrarToastCorto(SignIn.this, "Las contraseñas no coinciden");
                } else{
                    // Convertir el identificador del cliente a un número
                    int idCliente = Integer.parseInt(idCliente_string);

                    try {
                        // Verificar si el usuario ya existe en la base de datos
                        if (dbHelper.existeUsuario(idUsuario)){
                            Funciones.mostrarToastCorto(SignIn.this, "El usuario ya existe");
                        } else {
                            // Verificar si el identificador de cliente existe en la base de datos
                            if (dbHelper.existeCliente(idCliente)){
                                // Realizar el registro en la base de datos
                                dbHelper.registro(idUsuario, contrasena2, idCliente);
                                Funciones.mostrarToastCorto(SignIn.this, "Registro completado correctamente");
                                // Redirigir a la pantalla de inicio de sesión
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

    // Método para volver a la pantalla de inicio de sesión
    public void volverLogIn(View v){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    // Método para mostrar/ocultar las contraseñas al hacer clic en el CheckBox
    public void ShowPasswordSignIn(View v) {
        boolean checked1 = checkBoxPasswordSignIn1.isChecked();
        boolean checked2 = checkBoxPasswordSignIn2.isChecked();

        // Configurar la visibilidad del texto de la contraseña según el estado del CheckBox
        if(checked1) {
            etPasswordSignIn1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordSignIn1.setInputType(129);  // 129 es la constante para ocultar texto en un EditText
        }

        if(checked2) {
            etPasswordSignIn2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordSignIn2.setInputType(129);
        }
    }
}
