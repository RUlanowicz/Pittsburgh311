package edu.pitt.cs1635.pittsburgh311;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * Created by ulanowicz on 3/26/14.
 */
public class Position extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);
        GoogleMap map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng pgh = new LatLng(40.441814, -80.012794);

        map.getUiSettings().setMyLocationButtonEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh,13));

        Marker issueLoc = map.addMarker(new MarkerOptions()
                .title("Issue Location")
                .snippet("Drag to Problem Location")
                .position(pgh)
                .draggable(true));
    }
}
