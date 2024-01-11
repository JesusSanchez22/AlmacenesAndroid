package com.example.almaceneskikoandroid.Ventanas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.almaceneskikoandroid.R;
import com.example.almaceneskikoandroid.functions.Funciones;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URL;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String nombreCliente, urlIr, calle, ciudad;
    private int num, cp;

    private double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbarMap = findViewById(R.id.toolbarMap);
        addBackButtonInToolbar(toolbarMap, MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Se recogerá de la base de datos
        nombreCliente = "Bar Manolo";

        setSupportActionBar(toolbarMap);
        getSupportActionBar().setTitle("Ubicacion de: " + nombreCliente);

        //Se recogerá de la base de datos
        calle = "Mirabel";
        num = 25;
        cp = 47010;
        ciudad = "Valladolid";

        urlIr = "https://www.google.es/maps/dir//" + calle + ",+" + num + ",+" + cp + "+" + ciudad + "/";

        //Se recogerá de la base de datos
        latitud = 41.664665;
        longitud = -4.722739;

    }

    public void irUbicacion(View v){
        Uri link = Uri.parse(urlIr);
        Intent i = new Intent(Intent.ACTION_VIEW, link);
        startActivity(i);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        LatLng mapClient = new LatLng(latitud, longitud);

        mMap.addMarker(new MarkerOptions().position(mapClient).title(nombreCliente));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapClient));

    }

    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

    }
}