package edu.pitt.cs1635.pittsburgh311;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import java.util.List;
import java.util.Locale;

import edu.pitt.cs1635.pittsburgh311.model.Incident;

/**
 * Created by ulanowicz on 3/26/14.
 */
public class Position extends FragmentActivity {
    String city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);
        GoogleMap map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        final Button submit = (Button) findViewById(R.id.position_submit_button);

        LatLng pgh = new LatLng(40.441814, -80.012794);

        map.getUiSettings().setMyLocationButtonEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh, 13));

        Marker issueLoc = map.addMarker(new MarkerOptions()
                .title("Issue Location")
                .snippet("Drag to Problem Location")
                .position(pgh)
                .draggable(true));

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;
                Incident myIncident = Incident.getInstance();
                myIncident.setLat(Double.toString(latitude));
                myIncident.setLon(Double.toString(longitude));
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    myIncident.setAddress(addresses.get(0).getAddressLine(0));
                    city = addresses.get(0).getAddressLine(1);
                    if(!city.contains("Pittsburgh")){
                        submit.setEnabled(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(getApplicationContext(),SubmissionComplete.class);
                startActivity(image);


            }
        });

        class uploadReport extends AsyncTask<Void,Void,Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                HttpPost toServer = new HttpPost("http://stevenbauer.net/Pittsburgh311/PHP/create_report.php");
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
    }

}
