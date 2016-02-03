package com.prueba.rubel.pruebamapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa2 extends AppCompatActivity {

    ImageButton btn_home;
    private GoogleMap mMap;

    double latitude = -25.347809;
    double longitude = -57.481297;

    double latitude2 = -25.323605;
    double longitude2 = -57.520157;

    double latLng = -25.342844;
    double latLng1 =   -57.486812;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa2);

        mMap = (((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap());


//establecer los diferentes tipos de mapas o Viualizaciones
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("First Location");
        markerOptions.snippet("This Is Test Location");

        LatLng latlng = new LatLng(latitude, longitude);
        LatLng latlng2 = new LatLng(latitude2, longitude2);

        CameraPosition posicion = new CameraPosition.Builder().target(latlng)
                .zoom(12).
                        bearing(40).
                        tilt(10)
                .build();

        CameraUpdate camera2 = CameraUpdateFactory.newCameraPosition(posicion);
        mMap.moveCamera(camera2);

        markerOptions.position(latlng);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = getLayoutInflater().inflate(
                        R.layout.custommarker, null);
                TextView tvTitle = ((TextView) myContentView
                        .findViewById(R.id.title));
                tvTitle.setText(marker.getTitle());
                TextView tvSnippet = ((TextView) myContentView
                        .findViewById(R.id.snippet));
                tvSnippet.setText(marker.getSnippet());
                return myContentView;
            }
        });




        mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("This is Sabarmati Ashram")
                .snippet("Ahmedabad")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        mMap.addMarker(new MarkerOptions()
                .position(latlng2)
                .title("This is Sabarmati Ashram")
                .snippet("Ahmedabad")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getBaseContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });






    }
}
