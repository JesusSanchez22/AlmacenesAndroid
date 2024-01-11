package com.example.almaceneskikoandroid.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almaceneskikoandroid.R;

public class SignIn extends AppCompatActivity {

    private CheckBox checkBoxPasswordSignIn1, checkBoxPasswordSignIn2;
    private EditText etUserSignIn, etPasswordSignIn1, etPasswordSignIn2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUserSignIn = findViewById(R.id.etUserSignIn);
        etPasswordSignIn1 = findViewById(R.id.etPasswordSignIn1);
        etPasswordSignIn2 = findViewById(R.id.etPasswordSignIn2);

        checkBoxPasswordSignIn1 = findViewById(R.id.checkBoxPasswordSignIn1);
        checkBoxPasswordSignIn2 = findViewById(R.id.checkBoxPasswordSignIn2);


        //etPasswordSignIn1.setInputType(129);
        //etPasswordSignIn2.setInputType(129);

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