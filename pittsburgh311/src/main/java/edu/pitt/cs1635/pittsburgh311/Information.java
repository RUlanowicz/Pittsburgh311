package edu.pitt.cs1635.pittsburgh311;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.pitt.cs1635.pittsburgh311.model.ProfileManager;


public class Information extends ActionBarActivity {
    TextView firstName, lastName, homeAddress, emailAddress, phoneNumber;
    ProfileManager userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);
        firstName = (TextView)findViewById(R.id.first_name);
        lastName = (TextView)findViewById(R.id.last_name);
        homeAddress = (TextView)findViewById(R.id.address);
        emailAddress = (TextView)findViewById(R.id.email_address);
        phoneNumber = (TextView)findViewById(R.id.phone_number);
        userProfile = ProfileManager.getInstance();
        returnUserData();

        Button createButton = (Button)findViewById(R.id.setup_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
                Intent moveOn = new Intent(getApplicationContext(),Comments.class);
                startActivity(moveOn);
            }
        });
    }

    private void returnUserData() {
        firstName.setText(userProfile.getFirstName(getApplicationContext()));
        lastName.setText(userProfile.getLastName(getApplicationContext()));
        emailAddress.setText(userProfile.getEmail(getApplicationContext()));
        homeAddress.setText(userProfile.getHomeAddress(getApplicationContext()));
        phoneNumber.setText(userProfile.getPhoneNumber(getApplicationContext()));
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return null;
    }

    private void saveProfileData() {
        userProfile.setFirstName(getApplicationContext(),firstName.getText().toString());
        userProfile.setLastName(getApplicationContext(),lastName.getText().toString());
        userProfile.setEmail(getApplicationContext(),emailAddress.getText().toString());
        userProfile.setHomeAddress(getApplicationContext(),homeAddress.getText().toString());
        userProfile.setPhoneNumber(getApplicationContext(),phoneNumber.getText().toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.information, menu);
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

}
