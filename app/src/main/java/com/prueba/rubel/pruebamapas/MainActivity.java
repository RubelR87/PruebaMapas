package com.prueba.rubel.pruebamapas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Google Map
    private GoogleMap googleMap;
    // latitude and longitude
    double latitude = -25.345908;
    double longitude =  -57.479559;

    double latitude2 = -25.323605;
    double longitude2 = -57.520157;

    double latLng =-25.342844;
    double latLng1 =   -57.486812;

    Button btnormal, btterrestre, bthybrid, btsatellite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleMap = (((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap());


        btnormal = (Button) findViewById(R.id.btmapnormal);
        btterrestre =(Button) findViewById(R.id.btmapterrain);
        bthybrid =(Button) findViewById(R.id.btmapHybrid);
        btsatellite =(Button) findViewById(R.id.btmapsatellite);

        btnormal.setOnClickListener(this);
        btterrestre.setOnClickListener(this);
        bthybrid.setOnClickListener(this);
        btsatellite.setOnClickListener(this);


//establecer los diferentes tipos de mapas o Viualizaciones
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng LL_hotel = new LatLng(latitude, longitude);
        CameraPosition posicion = new CameraPosition.Builder().target(LL_hotel)
                .zoom(12).
                        bearing(40).
                        tilt(10)
                .build();

        CameraUpdate camera2 = CameraUpdateFactory.newCameraPosition(posicion);
        googleMap.moveCamera(camera2);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


/*
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude,longitude))
                .title("Hotel Terere").snippet("Llamar:(+595921) 2198000")).showInfoWindow();
                */

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude2,longitude2))
                .title("Hotel Terere2").snippet("Llamar:(+595921) 2198000"))
                .showInfoWindow();





        MarkerOptions markercasa = new MarkerOptions().position(new LatLng( -25.347449, -57.471459)).title("Casa");
// Changing marker icon
        markercasa.icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryicon));


        markercasa.snippet("Llamar: 0971300660");


// adding marker
        googleMap.addMarker(markercasa).showInfoWindow();

        googleMap. setTrafficEnabled(true);





        //CAmbiando icoco my location
        //googleMap.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location l = null;

        for (int i = 0; i < providers.size(); i++) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) {
                latitude = l.getLatitude();
                longitude = l.getLongitude();
               // strAdd = getCompleteAddressString(latitude, longitude);
               // tvAddress.setText("Complete Address : " + strAdd);
                break;
            }
        }


        MarkerOptions markermylocatiion = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps").snippet("Discription");

        markermylocatiion.icon(BitmapDescriptorFactory.fromResource(R.drawable.location));
        googleMap.addMarker( markermylocatiion).showInfoWindow();





        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            public View getInfoWindow(Marker arg0) {
                View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                return v;
            }

            public View getInfoContents(Marker arg0) {

                //View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                return null;

            }
        });





        googleMap.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    public void onInfoWindowClick(Marker marker) {
                        // Intent nextScreen = new Intent(Mapa_munipalidad.this,EventActivity.class);

                        // startActivityForResult(nextScreen, 0);
                        if (marker.getTitle().equals("Hotel Terere")) {
                            Intent intent;
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+5959212198000"));
                            startActivity(intent);
                        }

                        if (marker.getTitle().equals("Hotel Terere2")) {
                            Intent intent;
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+5959212198002"));
                            startActivity(intent);
                        }
                    }
                });








    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btmapnormal:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btmapterrain:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                break;
            case R.id.btmapHybrid:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                break;
            case R.id.btmapsatellite:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                break;

        }
    }


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                startActivity(new Intent(MainActivity.this, Mapa2.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
