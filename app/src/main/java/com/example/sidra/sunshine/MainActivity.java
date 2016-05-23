package com.example.sidra.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i,1);
            return true;
        }
        if(id == R.id.action_map_location) {
            locationOnMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void locationOnMap() {

        GPSTracker gps = new GPSTracker(getApplicationContext());
        long latitude = (long) gps.getLatitude();
        long longitude = (long) gps.getLongitude();

        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+latitude+","+longitude);//.buildUpon().appendQueryParameter("latitude",postCode).build();
        //q="+latitude+","+longitude+"(Your location)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(gmmIntentUri);

        //   mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager())!= null) {
            startActivity(mapIntent);
        }
        else{
            Log.v("Intent resolution error","No application supporting map view");
        }
    }
}
