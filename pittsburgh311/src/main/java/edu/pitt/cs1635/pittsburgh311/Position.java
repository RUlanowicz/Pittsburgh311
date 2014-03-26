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

        LatLng pgh = new LatLng(40.4417, -80.0000);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh,13));

        map.addMarker(new MarkerOptions()
                .title("Pittsburgh")
                .snippet("The best city")
                .position(pgh));
    }
}
