package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapas extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap elmapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Mapas.this);

        Button mercadona = findViewById(R.id.btn1);
        Button eroski = findViewById(R.id.btn2);

        mercadona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { marcasMercadona(); }
        });
    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        elmapa = googleMap;
        elmapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    //Realiza un zoom a bilbao y marca los mercadonas de alrededor
    public void marcasMercadona(){
        LatLng bilbao = new LatLng(43.297747770683664, -2.9772891073379504);
        LatLng mercadonaDeusto = new LatLng(43.27001507282561, -2.949563567303818);
        LatLng mercadonaLeioa = new LatLng(43.32673389405726, -3.007623184554216);
        LatLng mercadonaPortu = new LatLng(43.32498522765916, -3.0342562859572806);
        LatLng mercadonaBrk = new LatLng(43.30136800828086, -3.0006698828443112);
        LatLng mercadonaZorroza = new LatLng(43.26370229625726, -2.9472720727074484);
        LatLng mercadonaMoyua = new LatLng(43.26225166761952, -2.9358711940711046);
        LatLng mercadonaMiribilla = new LatLng(43.24968423835988, -2.928579989633055);
        LatLng mercadonaBolueta = new LatLng(43.24856307389647, -2.907840563277058);
        elmapa.addMarker(new MarkerOptions().position(mercadonaDeusto).title("Mercadona Deusto"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaLeioa).title("Mercadona Leioa"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaPortu).title("Mercadona Portu"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaBrk).title("Mercadona Barakaldo"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaZorroza).title("Mercadona Zorroza"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaMoyua).title("Mercadona Moyua"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaMiribilla).title("Mercadona Miribilla"));
        elmapa.addMarker(new MarkerOptions().position(mercadonaBolueta).title("Mercadona Bolueta"));
        elmapa.moveCamera(CameraUpdateFactory.newLatLngZoom(bilbao,11));
    }

}