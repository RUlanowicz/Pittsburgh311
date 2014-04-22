package edu.pitt.cs1635.pittsburgh311;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.pitt.cs1635.pittsburgh311.model.ProfileManager;


public class MainActivity extends ActionBarActivity {
    ProfileManager myManager;
    Button guestButton,infoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_choice);
        infoButton = (Button)findViewById(R.id.information_button);
        myManager = ProfileManager.getInstance();
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page = new Intent(getApplicationContext(),Information.class);
                startActivity(page);
            }
        });

        guestButton = (Button)findViewById(R.id.guest_button);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(getApplicationContext(),Comments.class);
                startActivity(image);
            }
        });

        if(myManager.getRegistered(getApplicationContext()) == null || myManager.getRegistered(getApplicationContext()).contains("false")){
            guestButton.setVisibility(View.GONE);
            infoButton.setVisibility(View.VISIBLE);
        }
        else{
            guestButton.setVisibility(View.VISIBLE);
            infoButton.setText("Change Information");
        }
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myManager.getRegistered(getApplicationContext()) == null || myManager.getRegistered(getApplicationContext()).contains("false")){
            guestButton.setVisibility(View.GONE);
            infoButton.setVisibility(View.VISIBLE);
        }
        else{
            guestButton.setVisibility(View.VISIBLE);
            infoButton.setText("Change Information");
        }
    }
}
