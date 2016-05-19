package com.example.sidra.sunshine;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    GPSTracker gps;
    double latitude;
    double longitude;
    public static ArrayAdapter<String> arrayAdapter;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Today-Sunny-88/63");
        list.add("Tomorrow-Sunny-75/45");
        list.add("Thur-Sunny-67/65");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, list);
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
                FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
                GPSTracker gps = new GPSTracker(getActivity());
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                String receivePostCode = "";
                String country = "";
                ArrayList<String> passing = new ArrayList<String>();

                Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                List<Address> address = null;
                if (geoCoder != null) {
                    try {
                        address = geoCoder.getFromLocation(latitude, longitude, 5);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if (address.size() > 0) {
                        receivePostCode = address.get(0).getPostalCode();
                      //  Log.v("Address",address.get(0).getAddressLine(0));
                      //  Log.v("Address",address.get(1).getAddressLine(0));
                      //  Log.v("Address",address.get(2).getAddressLine(0));
                      //  Log.v("Address",address.get(3).getAddressLine(0));
                      //  Log.v("Address",address.get(4).getAddressLine(0));


                        country = address.get(0).getCountryName();
                        passing.add(receivePostCode);
                        passing.add(country);
                        fetchWeatherTask.execute(passing);

                    }

                }
                return true;
            }
            default:
                break;
        }

        return false;
    }
}

