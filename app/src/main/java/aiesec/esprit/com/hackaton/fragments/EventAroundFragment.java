package aiesec.esprit.com.hackaton.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.R;
import aiesec.esprit.com.hackaton.utils.City;

/**
 * Created by maher on 06/12/2016.
 */
public class EventAroundFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    //  private List<Event> events=null;
    private String json = "";
    //   private GPSTracker mGPS ;
    private LatLng currentPosition;
    private Double redius = 10000D;
    public DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    public List<Evenement> evenements = new ArrayList<>();


    public EventAroundFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // mGPS = new GPSTracker(getActivity());
        City city = new City();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        currentPosition = new LatLng(35.03543859999999, 9.483939200000009);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        evenements.clear();
        mDatabase.child("Evenement").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Evenement evenement = dataSnapshot.getValue(Evenement.class);
                evenements.add(evenement);
                currentPosition = new LatLng(City.Tunisia.get(evenement.getGouvernerat()).Lat,
                        City.Tunisia.get(evenement.getGouvernerat()).Lang);
                mMap.addMarker(new MarkerOptions().position(currentPosition).title(evenement.getTitle() +" " + evenement.getAdresse()));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 7));

        //  mMap.addMarker(new MarkerOptions().position(currentPosition).title("Sidi BouZaid"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // handle the events on marker click..
                return false;
            }
        });
    }


}
