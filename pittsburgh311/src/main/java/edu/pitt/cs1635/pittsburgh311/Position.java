package edu.pitt.cs1635.pittsburgh311;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import edu.pitt.cs1635.pittsburgh311.model.Incident;
import edu.pitt.cs1635.pittsburgh311.model.ProfileManager;

/**
 * Created by ulanowicz on 3/26/14.
 */
public class Position extends FragmentActivity implements LocationListener, LocationSource{
    String city = null;
    Incident myIncident = Incident.getInstance();
    ProfileManager userProfile;
    private LocationManager locationManager;

    public static String getContent(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String body = "";
        String content = "";

        while ((body = rd.readLine()) != null)
        {
            content += body + "\n";
        }
        return content.trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);
        GoogleMap map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        final Button submit = (Button) findViewById(R.id.position_submit_button);
        submit.setEnabled(false);
        userProfile = ProfileManager.getInstance();

        Toast.makeText(getApplicationContext(),"Press and Hold Pin to Move",Toast.LENGTH_LONG).show();

        LatLng pgh = new LatLng(40.441814, -80.012794);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh, 13));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(locationManager != null)
        {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(gpsIsEnabled)
            {
                Log.d("GPS Enabled", "GPS Enabled");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10F, this);
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        pgh = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh, 16));
                myIncident.setLat(Double.toString(pgh.latitude));
                myIncident.setLon(Double.toString(pgh.longitude));
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(pgh.latitude, pgh.longitude, 1);
                    myIncident.setAddress(addresses.get(0).getAddressLine(0));
                    city = addresses.get(0).getAddressLine(1);
                    if(!city.contains("Pittsburgh")){
                        submit.setEnabled(false);
                    }
                    else{
                        submit.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                if (networkIsEnabled) {
                    Log.d("Network Position Enabled", "Network Position");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        pgh = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pgh, 15));
                    myIncident.setLat(Double.toString(pgh.latitude));
                    myIncident.setLon(Double.toString(pgh.longitude));
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(pgh.latitude, pgh.longitude, 1);
                        myIncident.setAddress(addresses.get(0).getAddressLine(0));
                        city = addresses.get(0).getAddressLine(1);
                        if(!city.contains("Pittsburgh")){
                            submit.setEnabled(false);
                        }
                        else{
                            submit.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        else
        {

        }


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
                    else{
                        submit.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadReport().execute();

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    class uploadReport extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpResponse response = null;
            File file = myIncident.getPhoto();
            if(!file.exists()){
                return "broken";
            }
            FileBody fb = new FileBody(file);
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://pittsburgh311.stevenbauer.net/create_report.php");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file_contents",fb);
            builder.addTextBody("address", myIncident.getAddress());
            builder.addTextBody("email",userProfile.getEmail(getApplicationContext()));
            builder.addTextBody("comment",myIncident.getComment());
            builder.addTextBody("lat",myIncident.getLat());
            builder.addTextBody("lon",myIncident.getLon());
            builder.addTextBody("status","Open");
            builder.addTextBody("category",myIncident.getCategory());
            builder.addTextBody("photo",file.getName());
            final HttpEntity yourEntity = builder.build();
            post.setEntity(yourEntity);
            try {
                response = client.execute(post);
                return getContent(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Log.i("Position Response: ",aVoid);

            Intent image = new Intent(getApplicationContext(),SubmissionComplete.class);
            startActivity(image);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
