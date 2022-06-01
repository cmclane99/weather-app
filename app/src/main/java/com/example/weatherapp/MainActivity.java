/**
 * Chris McLane
 */

package com.example.weatherapp;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyFragmentDataPassListener {
    SharedPreferences sharedPreferences;
    FragmentCityList fragCityList;
    FragmentWeatherInfo fragWeatherInfo;
    FragmentApiKey fragApiKey;
    ArrayList<MyRecyclerViewData> lv_data;
    ArrayList<String> lv_Zip;
    HttpHandlerThread myJSONThread;
    Thread httpInThread;
    String json;
    String apiKey;
    int counter = 0;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String API_KEY = "apiKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        apiKey = sharedPreferences.getString(API_KEY, "");

        lv_data = new ArrayList<>();
        lv_Zip = new ArrayList<>();

        if(!apiKey.equals("")) {
            fetchLiveData();
        }
        else {
            fragApiKey = new FragmentApiKey();
            setContentFragment(0);
        }

    }

    public void fetchLiveData() {

        lv_Zip.add("48197");
        lv_Zip.add("85365");
        lv_Zip.add("99703");
        lv_Zip.add("48116");
        lv_Zip.add("10451");
        lv_Zip.add("33610");
        lv_Zip.add("48103");

        for (int i = 0; i < lv_Zip.size(); i++) {
            String openWeatherURL = "https://api.openweathermap.org/data/2.5/weather?zip="+lv_Zip.get(i)+",us&units=imperial&APPID="+apiKey;

            // Create new worker thread to handle HTTP requests and fetch live weather data in background
            myJSONThread = new HttpHandlerThread(openWeatherURL);
            httpInThread = new Thread(myJSONThread);
            httpInThread.start();   // Start worker thread
            try {
                httpInThread.join();     // Join worker thread with main thread
                json = myJSONThread.getResult();    // Get results and parse json data
                parseJson(json);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        fragCityList = new FragmentCityList(lv_data);
        fragWeatherInfo = new FragmentWeatherInfo();
        setContentFragment(1);
    }

    public void parseJson(String input) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonRootObject = (JSONObject) parser.parse(input);
            JSONArray jsonWeatherArray = (JSONArray) jsonRootObject.get("weather");
            String cond = ((JSONObject) jsonWeatherArray.get(0)).get("main").toString();

            JSONObject jsonMainObject = (JSONObject) jsonRootObject.get("main");
            double raw_temp = Double.parseDouble(jsonMainObject.get("temp").toString());
            int temp = Integer.parseInt(String.valueOf(Math.round(raw_temp)));
            String city = jsonRootObject.get("name").toString();

            lv_data.add(new MyRecyclerViewData(city, lv_Zip.get(counter), temp, cond));
            counter++;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void loadFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {

        FragmentTransaction fragManager = activity.getSupportFragmentManager().beginTransaction();
        switch (tag) {
            case "fragment1":
                fragManager.setCustomAnimations(
                        R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right
                );
                break;
            case "fragment2":
                fragManager.setCustomAnimations(
                        R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left
                );
                break;
        }
        fragManager.replace(containerId, fragment, tag).commitAllowingStateLoss();
    }

    public void setContentFragment(int id) {
        switch (id) {
            case 0:
                loadFragment(this, R.id.vv_fragContainer, fragApiKey, "requestKey");
                break;
            case 1:
                loadFragment(this, R.id.vv_fragContainer, fragCityList, "fragment1");
                break;
            case 2:
                loadFragment(this, R.id.vv_fragContainer, fragWeatherInfo, "fragment2");
                break;
        }
    }

    @Override
    public void cf_firedByFragment(MyRecyclerViewData data) {

        setContentFragment(2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setContentFragment(1);
                getSupportFragmentManager().executePendingTransactions();
                getSupportActionBar().setTitle("AA01WeatherAppver03");
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // this will wait until frag02 onCreateView() done, otherwise null
        getSupportFragmentManager().executePendingTransactions();
        fragWeatherInfo.cf_fetchData(data);
    }

    @Override
    public void saveApiKey(String key) {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(API_KEY, key);
        editor.apply();
        apiKey = key;

        fragApiKey = new FragmentApiKey();
        fetchLiveData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}





