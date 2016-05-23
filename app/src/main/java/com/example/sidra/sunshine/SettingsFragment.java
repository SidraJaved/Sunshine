package com.example.sidra.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("temperature")){
//                        Toast.makeText(getActivity(),"Temperature unit changing...",
//                                Toast.LENGTH_SHORT).show();
       }
        else if(key.equals("city")){
//            Toast.makeText(getActivity(),"City changing...",
//                    Toast.LENGTH_SHORT).show();
//            EditTextPreference editTextPreference = (EditTextPreference)  getPreferenceScreen().findPreference("city");
//            editTextPreference.setSummary("Cityyyyyyyyyyyyyyyyyyyyyyyyy");
        }
    }
}
