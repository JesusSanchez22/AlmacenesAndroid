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

    // Variable estática para almacenar el usuario que ha iniciado sesión
    protected static Usuario usuarioLogIn;

    // Elementos de la interfaz de usuario
    private CheckBox checkBox;
    private EditText etUserLogin, etPasswordLogin;
    private TextView txtRegistro;

    // Datos predefinidos para un usuario de ejemplo (puedes cambiarlos según tus necesidades)
    private String user1 = "raulp";
    private String passwordUser1 = "123";

    // Instancia de la clase DBHelper para interactuar con la base de datos
    MiDBHelper dbHelper;

    // Método onCreate, se ejecuta al iniciar la actividad
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialización de la instancia de MiDBHelper para interactuar con la base de datos
        dbHelper = new MiDBHelper(this);

        // Obtener referencias a los elementos de la interfaz de usuario
        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

        checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(false);

        // Establecer valores predeterminados para el usuario de ejemplo
        etUserLogin.setText(user1);
        etPasswordLogin.setText(passwordUser1);
    }

    // Método para mostrar/ocultar la contraseña al hacer clic en el CheckBox
    public void ShowPasswordLogIn(View v) {
        boolean checked = checkBox.isChecked();

        // Configurar la visibilidad del texto de la contraseña según el estado del CheckBox
        if(checked) {
            etPasswordLogin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etPasswordLogin.setInputType(129);
        }
    }

    // Método para verificar las credenciales y redirigir a la actividad correspondiente
    public void verificar(View v){
        // Obtener los valores del usuario y la contraseña
        String usuarioIn = etUserLogin.getText().toString();
        String contrasenaIn = etPasswordLogin.getText().toString();

        // Validar si el campo de usuario está vacío
        if (usuarioIn.isEmpty()) {
            Funciones.mostrarToastCorto(this,"No has introducido ningún usuario");
        } else if ((contrasenaIn.isEmpty())) {
            Funciones.mostrarToastCorto(this,"La contraseña no puede estar vacía");
        } else if (dbHelper.existeUsuario(usuarioIn) && dbHelper.comprobarContrasena(usuarioIn, contrasenaIn)){

            // Obtener el objeto Usuario correspondiente al usuario que ha iniciado sesión
            usuarioLogIn = dbHelper.obtenerUsuario(usuarioIn);

            // Redirigir a la actividad correspondiente según el tipo de usuario (empleado o cliente)
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

    // Método para ir a la actividad de registro
    public void irRegistro(View v){
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}
