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

    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtener referencia a la barra de herramientas
        Toolbar toolbarMap = findViewById(R.id.toolbarMap);
        // Agregar botón de retroceso a la barra de herramientas
        addBackButtonInToolbar(toolbarMap, MapsActivity.this);

        // Obtener referencia al fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Recoger datos del cliente desde la base de datos
        nombreCliente = clientePedido.getNombreEmpresa();

        // Recoger datos del cliente desde la base de datos
        calle = clientePedido.getCalle();
        num = clientePedido.getNumero();
        cp = clientePedido.getCp();
        ciudad = clientePedido.getCiudad();

        // URL para abrir Google Maps y navegar a la ubicación del cliente
        urlIr = "https://www.google.es/maps/dir//" + calle + ",+" + num + ",+" + cp + "+" + ciudad + "/";

        // Usar Geocoder para obtener la ubicación del cliente por su dirección
        Geocoder geo = new Geocoder(this);
        int maxResultados = 1;
        List<Address> adress = null;
        try {
            adress = geo.getFromLocationName("Calle " + calle + " " + num + ", " + ciudad, maxResultados);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Crear LatLng con la ubicación obtenida del Geocoder
        latLng = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
    }

    // Método para abrir Google Maps y navegar a la ubicación del cliente
    public void irUbicacion(View v){
        Uri link = Uri.parse(urlIr);
        Intent i = new Intent(Intent.ACTION_VIEW, link);
        startActivity(i);
    }

    // Método llamado cuando el mapa está listo para ser utilizado
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Añadir un marcador en la ubicación del cliente y mover la cámara
        mMap.addMarker(new MarkerOptions().position(latLng).title(nombreCliente));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    // Método para agregar un botón de retroceso a la barra de herramientas
    public void addBackButtonInToolbar(Toolbar toolbar, AppCompatActivity appCompatActivity){
        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad DetallesPedidos al hacer clic en el botón de retroceso
                Intent intent = new Intent(appCompatActivity, DetallesPedidos.class);
                startActivity(intent);
            }
        });
    }
}