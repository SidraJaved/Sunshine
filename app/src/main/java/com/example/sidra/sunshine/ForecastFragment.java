package com.example.sidra.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    GPSTracker gps;
    private static SharedPreferences sharedPreferences ;
    private static final int PREFERENCE_MODE = 0;
    public static ArrayAdapter<String> arrayAdapter;
    private static double latitude;
    private static double longitude;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, new ArrayList<String>());
        ListView listView = (ListView) rootView.findViewById(R.id.list_item_forecast);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = arrayAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("Forecast",forecast);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                weatherUpdate();
                return true;
            }
            default:
                break;
        }

        return false;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        weatherUpdate();
    }
    private void weatherUpdate() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();

        GPSTracker gps = new GPSTracker(getActivity());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        ArrayList<String> passing = new ArrayList<String>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String postCode = sharedPref.getString("postCode", "");
        String city = sharedPref.getString("city", "");
        String tempUnits = sharedPref.getString("temperature","");
        Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> address = null;
        if(city == "" || postCode == ""){
            if (geoCoder != null) {
                try {
                    address = geoCoder.getFromLocation(latitude, longitude, 5);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (address.size() > 0) {
                    postCode = address.get(0).getPostalCode();
                    city = address.get(0).getLocality();
                    passing.add(postCode); //now using from shared prefs
                    passing.add(city);
                    passing.add(tempUnits);
                    fetchWeatherTask.execute(passing);

                }

            }}
        else
        {
            passing.add(postCode); //now using from shared prefs
            passing.add(city);
            passing.add(tempUnits);
            fetchWeatherTask.execute(passing);
        }
    }
}

