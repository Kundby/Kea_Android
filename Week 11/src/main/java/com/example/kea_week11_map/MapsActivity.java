package com.example.kea_week11_map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.kea_week11_map.model.Marker;
import com.google.android.gms.maps.CameraUpdate;
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
    private final static String collectionRef = "markers";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static List<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        downloadMarker();
        Log.i("ALL", "Map has been loaded");
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                final Double longtitude = latLng.longitude;
                final Double latitude = latLng.latitude;
                final LatLng marker = new LatLng(latitude,longtitude);
                AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
                alert.setTitle("Choose title of marker");
                final EditText input = new EditText(MapsActivity.this);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();
                        Marker markerToFirebase = new Marker(longtitude, latitude, text);
                        addMarker(markerToFirebase);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }

    public static void addMarker(Marker marker) {
        DocumentReference docRef = db.collection(collectionRef).document();
        Map<String, String> map = new HashMap<>();
        map.put("title", marker.getTitle());
        map.put("latitude", String.valueOf(marker.getLatitude()));
        map.put("longtitude", String.valueOf(marker.getLongtitude()));
        docRef.set(map);
    }

    public static void downloadMarker(){
        db.collection(collectionRef).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                 markerList.clear();
                for (DocumentSnapshot snap : values.getDocuments()){
                    Log.i("MainActivity", "QuerySnapshot, Read from FireStore: " + snap.get("latitude") + " " + snap.get("longtitude") + " " + snap.getId());
                    String strLat = String.valueOf(snap.get("latitude"));
                    String strLon = String.valueOf(snap.get("longtitude"));
                    double latitude = Double.valueOf(strLat);
                    double longtitude = Double.valueOf(strLon);
                    System.out.println(latitude);
                    System.out.println(longtitude);

                    markerList.add(new Marker(latitude, longtitude, snap.get("title").toString()));

                }
                for (Marker markers : markerList){
                    LatLng position = new LatLng(markers.getLongtitude(), markers.getLatitude());
                    mMap.addMarker(new MarkerOptions().position(position).title(markers.getTitle()));
                }

            }
        });



    }

}
