package com.example.eksamensprojekt;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.eksamensprojekt.model.Marker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private final static String collectionRef = "eksamensprojekt1";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String latKey = "LAT_KEY";
    public static final String lonKey = "LON_KEY";

    private static double lat, lon;
    private static String id;
    private static boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            id = intent.getExtras().getString(Edit_Log.idKey);
            isEditing = intent.getExtras().getBoolean(Edit_Log.editingKey);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (isEditing){
            placeMarker();
        }
        Log.i("MapsFragment", "Map has been loaded");
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //Detects when there's a long press on the map
                final Double longtitude = latLng.longitude;
                final Double latitude = latLng.latitude;
                Marker markerToFirebase = new Marker(longtitude, latitude);
                addMarker(markerToFirebase);
            }
        });
    }

    public static void addMarker(Marker marker) {
        lat = Double.valueOf(marker.getLatitude());
        lon = Double.valueOf(marker.getLongtitude());
        if (isEditing) { //Updates latitude & longtitude attributes in firestore - Adds the fields if not already present
            db.collection(collectionRef).document(id).update("lat", String.valueOf(lat), "lon", String.valueOf(lon));
        }else{ //If new log, then add the marker based on given parameters
            LatLng position = new LatLng(lat, lon);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(position));
        }
    }

    //Places marker in the map, based on the data on in Firestore
    private static void placeMarker(){
        db.collection(collectionRef).document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot values, @Nullable FirebaseFirestoreException e) {
                if (values.get("lat") != null){
                    //If lat value isn't null, then clear map of markers, then add the new marker (Clearing map each time to avoid multiple markers present at same time, as Log only allows 1 at a time)
                    String strLat = String.valueOf(values.get("lat"));
                    String strLon = String.valueOf(values.get("lon"));
                    double latitude = Double.valueOf(strLat);
                    double longtitude = Double.valueOf(strLon);
                    LatLng position = new LatLng(latitude, longtitude);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(position));
                }
            }
        });
    }

    //Overrides the default method onBackPressed - If isEditing is false, then start new intent and send over lat & lon details to be used in the Edit_Log class
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isEditing){
            Intent intent = new Intent(this, Edit_Log.class);
            intent.putExtra(latKey, lat);
            intent.putExtra(lonKey, lon);
            startActivity(intent);
        }

    }
}
