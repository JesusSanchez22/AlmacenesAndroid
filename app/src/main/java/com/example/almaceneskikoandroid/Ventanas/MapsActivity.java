package com.example.almaceneskikoandroid.Ventanas;

import static com.example.almaceneskikoandroid.Ventanas.DetallesPedidos.clientePedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.almaceneskikoandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String nombreCliente, urlIr, calle, ciudad;
    private int num, cp;

    //private double latitud, longitud;

    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbarMap = findViewById(R.id.toolbarMap);
        addBackButtonInToolbar(toolbarMap, MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Se recogerá de la base de datos
        nombreCliente = clientePedido.getNombreEmpresa();

        setSupportActionBar(toolbarMap);
        getSupportActionBar().setTitle("Ubicacion de: " + nombreCliente);

        //Se recogerá de la base de datos
        calle = clientePedido.getCalle();
        num = clientePedido.getNumero();
        cp = clientePedido.getCp();
        ciudad = clientePedido.getCiudad();

        //url para ir desde el botón
        urlIr = "https://www.google.es/maps/dir//" + calle + ",+" + num + ",+" + cp + "+" + ciudad + "/";

        //Permite buscar por nombre en el mapa
        Geocoder geo = new Geocoder(this);
        int maxResultados = 1;
        List<Address> adress = null;
        try {
            adress = geo.getFromLocationName("Calle " + calle + " " + num + ", " + ciudad, maxResultados);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        latLng = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());

    }

    public void irUbicacion(View v){
        Uri link = Uri.parse(urlIr);
        Intent i = new Intent(Intent.ACTION_VIEW, link);
        startActivity(i);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        //LatLng mapClient = new LatLng(latitud, longitud);

        mMap.addMarker(new MarkerOptions().position(latLng).title(nombreCliente));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

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
                Intent intent = new Intent(MapsActivity.this, DetallesPedidos.class);
                startActivity(intent);
            }
        });

    }
}