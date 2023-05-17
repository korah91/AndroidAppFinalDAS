package com.example.proyectofinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapas extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap elmapa;
    Marker deust,leio,port,brk,zorr,bil,miri,bolu;
    Boolean borrar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);

        //Permisos
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );


        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

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

        eroski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { marcasEroski(); }
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
        if(borrar){borrarMarcas();}
        LatLng bilbao = new LatLng(43.297747770683664, -2.9772891073379504);
        LatLng mercadonaDeusto = new LatLng(43.27001507282561, -2.949563567303818);
        LatLng mercadonaLeioa = new LatLng(43.32673389405726, -3.007623184554216);
        LatLng mercadonaPortu = new LatLng(43.32498522765916, -3.0342562859572806);
        LatLng mercadonaBrk = new LatLng(43.30136800828086, -3.0006698828443112);
        LatLng mercadonaZorroza = new LatLng(43.26370229625726, -2.9472720727074484);
        LatLng mercadonaBilbao = new LatLng(43.26225166761952, -2.9358711940711046);
        LatLng mercadonaMiribilla = new LatLng(43.24968423835988, -2.928579989633055);
        LatLng mercadonaBolueta = new LatLng(43.24856307389647, -2.907840563277058);
        deust = elmapa.addMarker(new MarkerOptions().position(mercadonaDeusto).title("Mercadona Deusto"));
        leio = elmapa.addMarker(new MarkerOptions().position(mercadonaLeioa).title("Mercadona Leioa"));
        port = elmapa.addMarker(new MarkerOptions().position(mercadonaPortu).title("Mercadona Portu"));
        brk = elmapa.addMarker(new MarkerOptions().position(mercadonaBrk).title("Mercadona Barakaldo"));
        zorr = elmapa.addMarker(new MarkerOptions().position(mercadonaZorroza).title("Mercadona Zorroza"));
        bil = elmapa.addMarker(new MarkerOptions().position(mercadonaBilbao).title("Mercadona Bilbao"));
        miri = elmapa.addMarker(new MarkerOptions().position(mercadonaMiribilla).title("Mercadona Miribilla"));
        bolu = elmapa.addMarker(new MarkerOptions().position(mercadonaBolueta).title("Mercadona Bolueta"));
        elmapa.moveCamera(CameraUpdateFactory.newLatLngZoom(bilbao,11));
        borrar = true;
    }
    //Realiza un zoom a bilbao y marca los eroskis de alrededor
    public void marcasEroski(){
        if(borrar){borrarMarcas();}
        LatLng bilbao = new LatLng(43.297747770683664, -2.9772891073379504);
        LatLng eroskiDeusto = new LatLng(43.27053513192074, -2.9450035298245494);
        LatLng eroskiLeioa = new LatLng(43.31902306434917, -2.9743889600076443);
        LatLng eroskiPortu = new LatLng(43.322832871793096, -3.0225623063048785);
        LatLng eroskiBarakaldo = new LatLng(43.29741919952933, -2.9907401218167307);
        LatLng eroskiZorroza = new LatLng(43.27526079870694, -2.9727520574196906);
        LatLng eroskiBilbao = new LatLng(43.265193833745876, -2.931006492733586);
        LatLng eroskiMiribilla = new LatLng(43.25083462629271, -2.931071912471076);
        LatLng eroskiBolueta = new LatLng(43.24832786506839, -2.905100736943466);
        deust = elmapa.addMarker(new MarkerOptions().position(eroskiDeusto).title("Eroski Deusto"));
        leio = elmapa.addMarker(new MarkerOptions().position(eroskiLeioa).title("Eroski Leioa"));
        port = elmapa.addMarker(new MarkerOptions().position(eroskiPortu).title("Eroski Portu"));
        brk = elmapa.addMarker(new MarkerOptions().position(eroskiBarakaldo).title("Eroski Barakaldo"));
        zorr = elmapa.addMarker(new MarkerOptions().position(eroskiZorroza).title("Eroski Zorroza"));
        bil = elmapa.addMarker(new MarkerOptions().position(eroskiBilbao).title("Eroski Bilbao"));
        miri = elmapa.addMarker(new MarkerOptions().position(eroskiMiribilla).title("Eroski Miribilla"));
        bolu = elmapa.addMarker(new MarkerOptions().position(eroskiBolueta).title("Eroski Bolueta"));
        elmapa.moveCamera(CameraUpdateFactory.newLatLngZoom(bilbao,11));
        borrar = true;
    }
    //Borra las marcas anteriores puestas en el mapa
    public void borrarMarcas(){
        deust.remove();
        leio.remove();
        port.remove();
        brk.remove();
        zorr.remove();
        bil.remove();
        miri.remove();
        bolu.remove();
    }



}