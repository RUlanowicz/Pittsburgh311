package edu.pitt.cs1635.pittsburgh311;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.util.Random;

import edu.pitt.cs1635.pittsburgh311.model.Incident;
import twitter4j.GeoLocation;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

// Zach Sadler - 3/26/14

public class SubmissionComplete extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_page);
        ImageButton twitterButton = (ImageButton)findViewById(R.id.twitter_button);
        Button goBackHome = (Button)findViewById(R.id.main_menu_button);

        new TwitterAuthenticateTask().execute();


        twitterButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int rng = new Random().nextInt();
                        Incident myIncident = Incident.getInstance();

                        String tweetBody = "I reported to Pittsburgh 311! \n" +
                                myIncident.getCategory() + " at " + myIncident.getAddress() + ": " + myIncident.getComment();

                        new TwitterUpdateStatusTask().execute(tweetBody);

                    }
                }

        );


        goBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(image);
            }
        });

    }

    private void initControl() {
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith("oauth://Pitt")) {
            String verifier = uri.getQueryParameter("oauth_verifier");
            new TwitterGetAccessTokenTask().execute(verifier);
        }
        else {
            new TwitterGetAccessTokenTask().execute("");
        }
    }


    private class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {

        @Override
        protected void onPostExecute(RequestToken requestToken) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
            startActivity(intent);

            initControl();
        }

        @Override
        protected RequestToken doInBackground(String... params) {
            return TwitterUtil.getInstance().getRequestToken();
        }
    }

    private class TwitterUpdateStatusTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                Toast.makeText(getApplication(), "Tweet posted successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplication(), "Tweet failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                String accessTokenString = sharedPreferences.getString("TWITTER_OAUTH_TOKEN", "");
                String accessTokenSecret = sharedPreferences.getString("TWITTER_OAUTH_TOKEN_SECRET", "");

                if (accessTokenString != null && accessTokenSecret != null) {
                    AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);

                    Incident myIncident = Incident.getInstance();
                    System.out.println("LAT: " + myIncident.getLat());
                    System.out.println("LON: " + myIncident.getLat());

                    twitter4j.StatusUpdate status = new twitter4j.StatusUpdate(params[0]);
                    status.setLocation(new GeoLocation(Double.parseDouble(myIncident.getLat()), Double.parseDouble(myIncident.getLon())));
                    status.setMedia(myIncident.getPhoto());
                    twitter4j.Status hooray = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).updateStatus(status);


                    return true;
                }

            } catch (TwitterException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return false;  //To change body of implemented methods use File | Settings | File Templates.

        }
    }



    class TwitterGetAccessTokenTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String userName) {
        }

        @Override
        protected String doInBackground(String... params) {

            Twitter twitter = TwitterUtil.getInstance().getTwitter();
            RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
            if (params[0] != null && !params[0].equals("")) {
                try {

                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TWITTER_OAUTH_TOKEN", accessToken.getToken());
                    editor.putString("TWITTER_OAUTH_TOKEN_SECRET", accessToken.getTokenSecret());
                    editor.putBoolean("TWITTER_IS_LOGGED_IN", true);
                    editor.commit();
                    return twitter.showUser(accessToken.getUserId()).getName();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            } else {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String accessTokenString = sharedPreferences.getString("TWITTER_OAUTH_TOKEN", "");
                String accessTokenSecret = sharedPreferences.getString("TWITTER_OAUTH_TOKEN_SECRET", "");
                AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
                try {
                    TwitterUtil.getInstance().setTwitterFactory(accessToken);
                    return TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }

            return null;  //To change body of implemented methods use File | Settings | File Templates.
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

}
