package com.prueba.rubel.pruebamapas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa_GetLatitut_Longitud extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    //Nuestro mapa
    private GoogleMap mMap;

    //Las latitud y Longitud a almacenar
    private double longitud;
    private double latitud;

    //Botones
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;


    //Google ApiClient
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa__get_latitut__longitud);


        SupportMapFragment mapFrgment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maplaton);
        mapFrgment.getMapAsync(this);


        //Inicializamos googleapiclient
        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();




        //Inicializamos los View y agregamos los listener
        buttonSave = (ImageButton) findViewById(R.id.buttonSave);
        buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);
        buttonView = (ImageButton) findViewById(R.id.buttonView);

        buttonSave.setOnClickListener(this);
        buttonCurrent.setOnClickListener(this);
        buttonView.setOnClickListener(this);




    }


    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void getCurrentLocation() {
        mMap.clear();


        //Creating a location object
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitud = location.getLongitude();
            latitud = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }

    private void moveMap() {
        //String para mostrar la latitud y longitud
        String msg = latitud + ", "+ longitud;

        //Creating  LagLng Object para guardar Coordenadas
        LatLng latLng = new LatLng(latitud, longitud);

        //Agregamos markador al mapa
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Mi Ubicacion actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

        //Moviiendo camera mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera zoom
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        //Mostramos las coordenas actuales en latitud y longitud
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        MyInfoWindowAdapter myInfoWindowAdapter = new MyInfoWindowAdapter();

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());




    }


    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
    if(v == buttonCurrent){
        getCurrentLocation();
        moveMap();

    }
     if(v == buttonView){
         startActivity(new Intent(Mapa_GetLatitut_Longitud.this,Marker_to_Map.class));
     }


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
    mMap.clear();

        //Agregamos un nuevo Marcador en el lugar presionado
        mMap.addMarker(new MarkerOptions()
        .position(latLng).
        draggable(true));

        Toast.makeText(this,"Agregaste un  nuevo marcador: "+"Latitud" + latLng.latitude +" Longitud" +latLng.longitude, Toast.LENGTH_LONG).show();
    Datos_Estaticos.latitud_static =latLng.latitude;
        Datos_Estaticos.longitud_static = latLng.longitude;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Obteniendo las coordenadas
        latitud = marker.getPosition().latitude;
        longitud = marker.getPosition().latitude;

        //Moviendo el mapa
        moveMap();


        Toast.makeText(this, "Agregando un nuevo marcador:-> " + "onMarkerDragEnd", Toast.LENGTH_LONG).show();

    }





    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.formulario_location, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.tvlongitud2));
            tvTitle.setText(longitud+"");
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.tvlatitud2));
            tvSnippet.setText(latitud +"");

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
