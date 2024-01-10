package com.example.almaceneskikoandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.almaceneskikoandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URL;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    TextView txtTitleMap;
    private String nombreCliente, urlIr, calle, ciudad;
    private int num, cp;

    private double latitud, longitud;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        txtTitleMap = findViewById(R.id.txtTitleMap);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Se recogerá de la base de datos
        nombreCliente = "Bar Manolo";

        //Text titulo del map, "Ubicación de : " + nombreCliente
        txtTitleMap.setText(getString(R.string.ubicaci_n_de) + nombreCliente);

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
}