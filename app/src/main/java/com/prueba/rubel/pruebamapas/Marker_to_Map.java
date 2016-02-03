package com.prueba.rubel.pruebamapas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Marker_to_Map extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener, View.OnClickListener {


private double longitud;
private double latitud;
private GoogleMap mMap;
        double latLng =-25.342844;
        double latLng1 =   -57.486812;


    int selected = 0;
    int	selecttipomapa=0;
    int buffKey = 0;



//Google ApiClient
private GoogleApiClient googleApiClient;
                Button buttonCalcDistancemm;

    Button btcambiartipomap;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_to__map);


        SupportMapFragment mapFrgment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapcaldistancia);
        mapFrgment.getMapAsync(this);


        //Inicializamos googleapiclient
        googleApiClient = new GoogleApiClient.Builder(this).
        addConnectionCallbacks(this).
        addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();


        buttonCalcDistancemm = (Button) findViewById(R.id.buttonCalcDistancemm);
        buttonCalcDistancemm.setOnClickListener(this);

    btcambiartipomap = (Button) findViewById(R.id.btcambiartipomap);
    btcambiartipomap.setOnClickListener(this);



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
            getDirection();
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
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryicon)));

   // mMap. setTrafficEnabled(true);



    mMap.setMyLocationEnabled(true);

    LatLng latLng2 = new LatLng(Datos_Estaticos.latitud_static, Datos_Estaticos.longitud_static);

        //Agregamos markador al mapa
        mMap.addMarker(new MarkerOptions()
        .position(latLng2)
        .draggable(true)
        .title("Marcador que se agrego en el mapa anterior")
        );

        //Moviiendo camera mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera zoom
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //Mostramos las coordenas actuales en latitud y longitud
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        MyInfoWindowAdapter myInfoWindowAdapter = new MyInfoWindowAdapter();

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());



        }
                public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
                        StringBuilder urlString = new StringBuilder();
                        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
                        urlString.append("?origin=");// from
                        urlString.append(Double.toString(sourcelat));
                        urlString.append(",");
                        urlString
                                .append(Double.toString(sourcelog));
                        urlString.append("&destination=");// to
                        urlString
                                .append(Double.toString(destlat));
                        urlString.append(",");
                        urlString.append(Double.toString(destlog));
                        urlString.append("&sensor=false&mode=driving&alternatives=true");
                        urlString.append("&key=AIzaSyCJVpM7-ayGMraxFRzq4U8Dt1uRNsmiaws");
                        return urlString.toString();
                }

                private void getDirection(){
                        //Getting the URL
                        double latitudehomefrom = -25.345908;
                        double longitudehomefrom =  -57.479559;

                        double lato =-25.342844;
                        double loto =   -57.486812;
                        ;


                        String url = makeURL(latitud, longitud, Datos_Estaticos.latitud_static, Datos_Estaticos.longitud_static);
                        // String url = makeURL(latitudehomefrom, longitudehomefrom, lato, loto);

                        //Showing a dialog till we get the route
                        final ProgressDialog loading = ProgressDialog.show(this, "Calculando Ruta", "Espere por favor...", false, false);

                        //Creating a string request
                        StringRequest stringRequest = new StringRequest(url,
                                new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                                loading.dismiss();
                                                //Calling the method drawPath to draw the path
                                                drawPath(response);
                                        }
                                },
                                new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                                loading.dismiss();
                                        }
                                });

                        //Adding the request to request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                }

                //The parameter is the server response
                public void drawPath(String  result) {
                        //Getting both the coordinates
                        LatLng from = new LatLng(latitud,longitud);
                        LatLng to = new LatLng(Datos_Estaticos.latitud_static,Datos_Estaticos.longitud_static);




                        //Calculating the distance in meters
                        Double distance = SphericalUtil.computeDistanceBetween(from, to);

                        //Displaying the distance
                        Toast.makeText(this,String.valueOf(distance+" Meters"),Toast.LENGTH_SHORT).show();


                        try {
                                //Parsing json
                                final JSONObject json = new JSONObject(result);
                                JSONArray routeArray = json.getJSONArray("routes");
                                JSONObject routes = routeArray.getJSONObject(0);
                                JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                                String encodedString = overviewPolylines.getString("points");
                                List<LatLng> list = decodePoly(encodedString);
                                Polyline line = mMap.addPolyline(new PolylineOptions()
                                                .addAll(list)
                                                .width(8)
                                                .color(Color.RED)
                                                .geodesic(true)
                                );


                        }
                        catch (JSONException e) {

                        }
                }

                private List<LatLng> decodePoly(String encoded) {
                        List<LatLng> poly = new ArrayList<LatLng>();
                        int index = 0, len = encoded.length();
                        int lat = 0, lng = 0;

                        while (index < len) {
                                int b, shift = 0, result = 0;
                                do {
                                        b = encoded.charAt(index++) - 63;
                                        result |= (b & 0x1f) << shift;
                                        shift += 5;
                                } while (b >= 0x20);
                                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lat += dlat;

                                shift = 0;
                                result = 0;
                                do {
                                        b = encoded.charAt(index++) - 63;
                                        result |= (b & 0x1f) << shift;
                                        shift += 5;
                                } while (b >= 0x20);
                                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lng += dlng;

                                LatLng p = new LatLng( (((double) lat / 1E5)),
                                        (((double) lng / 1E5) ));
                                poly.add(p);
                        }

                        return poly;
                }





@Override
public void onConnected(Bundle bundle) {
        getCurrentLocation();

        }

@Override
public void onConnectionSuspended(int i) {

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

        @Override
        public void onClick(View v) {
                switch (v.getId()){
                        case R.id.buttonCalcDistancemm:
                                getDirection();
                                break;
                    case R.id.btcambiartipomap:
                       cambiarTipoMapa();
                        break;


                }
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


    public void cambiarTipoMapa(){


        AlertDialog.Builder builder =
                new AlertDialog.Builder(Marker_to_Map.this);
        builder.setTitle("Tipos Mapas");

        //final CharSequence[] choiceList =  habitacion.choiceListadultos(habitacion.getTipo_hab());

			    		final CharSequence[] choiceList =
			    		{"Normal", "SATELLITE" , "TERRAIN" , "HYBRID" };


        builder.setSingleChoiceItems(
                choiceList,
                selecttipomapa,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        //set to buffKey instead of selected
                        //(when cancel not save to selected)
                        buffKey = which;
                    }
                })
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                selecttipomapa = buffKey;

                                if(selecttipomapa ==0){
                                   // btrcantadultos.setText("1");
                                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                }
                                if(selecttipomapa ==1){
                                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                }
                                if(selecttipomapa ==2){
                                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                }
                                if(selecttipomapa ==3){
                                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                }





                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
					/*Toast.makeText(
	                       getActivity(),
	                       "",
	                       Toast.LENGTH_SHORT
	                       )
	                       .show();*/
                            }
                        }
                );

        AlertDialog alert = builder.create();
        alert.show();






    }
}
