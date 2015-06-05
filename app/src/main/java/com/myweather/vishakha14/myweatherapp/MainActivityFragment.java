package com.myweather.vishakha14.myweatherapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.*;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.myweather.vishakha14.myweatherapp.Classes.DataBaseHelper;
import com.myweather.vishakha14.myweatherapp.Classes.GPS;
import com.myweather.vishakha14.myweatherapp.Classes.Weather;
import com.myweather.vishakha14.myweatherapp.fragments.WeatherDetailFragment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    String[] days;

    LocationAsyncTask locationAsyncTask;
    ArrayList<Weather> mWeatherArrayList;
    ListView dateListView;
    TextView locationName;
    String location;
    DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        dateListView =(ListView) view.findViewById(R.id.weatherListView);
        dateListView.setOnItemClickListener(this);
        locationName = (TextView) view.findViewById(R.id.locationName);
        getLocation();
        dbHelper = new DataBaseHelper(getActivity());
        Log.d("Fragment Loaded " , "111");
        return view;
    }
    public  void getLocation(){
        GPS gps = new GPS(getActivity());
        double lat = gps.getLatitude();
        double longt = gps.getLongitude();
        locationAsyncTask = new LocationAsyncTask(lat, longt);
        locationAsyncTask.execute();
    }



    public String timeStampToDate(long dt){

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(dt*1000);
        String date = DateFormat.format("ccc, dd-MMM-yyyy ", cal).toString();
        return date;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WeatherDetailFragment pf = new WeatherDetailFragment();
        pf.setmWeather(mWeatherArrayList.get(position));
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFragment, pf);
        ft.addToBackStack("weather");
        ft.commit();

    }
    public void showListView(){
        locationName.setText(location);
        days = new String[mWeatherArrayList.size()];
        for(int i = 0; i< days.length; i++){
            days[i] =  timeStampToDate(mWeatherArrayList.get(i).getDt());
        }
        dateListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, days));
    }

    public class LocationAsyncTask extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;
        double lat;
        double lon;
        public LocationAsyncTask(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Long doInBackground(String... strings) {
            String dataString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon + "&cnt=10&mode=json";

            try {
                URL dataUrl = new URL(dataString);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String responseFinalString = sb.toString();
                    setWeatherList(responseFinalString);
                    return 0l;
                } else {
                    return 1l;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (Exception e) {
                e.printStackTrace();
                return 1l;
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

        }

        private void setWeatherList(String responseFinalString) {

            mWeatherArrayList = new ArrayList<Weather>();

            try {

                JSONObject jsonResponseObject = new JSONObject(responseFinalString);
                JSONArray daysList = jsonResponseObject.optJSONArray("list");
                JSONObject locaionObject = jsonResponseObject.optJSONObject("city");

                location =locaionObject.optString("name");

                for (int i = 0; i < daysList.length(); i++) {
                    String weatherType, description, image;
                    double dayTmp, morningTmp, minimumTmp, maximumTmp, nightTmp, eveningTmp, pressure, speed;
                    int humidity, deg, clouds;
                    long dt;

                    JSONObject dayJsonObject = (JSONObject) daysList.get(i);
                    Weather weatherObject = new Weather();
                    dt = (long) dayJsonObject.optLong("dt");
                    JSONObject temp = dayJsonObject.optJSONObject("temp");
                    dayTmp = (float) temp.optDouble("day");
                    morningTmp = (float) temp.optDouble("morn");
                    minimumTmp = (float) temp.optDouble("min");
                    maximumTmp = (float) temp.optDouble("max");
                    nightTmp = (float) temp.optDouble("night");
                    eveningTmp = (float) temp.optDouble("eve");
                    pressure = (float) dayJsonObject.optDouble("pressure");
                    humidity = (int) dayJsonObject.optInt("humidity");
                    JSONArray mWeatherArray = dayJsonObject.optJSONArray("weather");
                    JSONObject mWeatherJSON = (JSONObject) mWeatherArray.get(0);
                    weatherType = (String) mWeatherJSON.optString("main");
                    description = (String) mWeatherJSON.optString("description");
                    image = (String) mWeatherJSON.optString("icon");
                    speed = (float) dayJsonObject.optDouble("speed");
                    deg = (int) dayJsonObject.optDouble("deg");
                    clouds = (int) dayJsonObject.optDouble("clouds");

                    weatherObject.setDt(dt);
                    weatherObject.setClouds(clouds);
                    weatherObject.setDayTmp(dayTmp);
                    weatherObject.setDeg(deg);
                    weatherObject.setDescription(description);
                    weatherObject.setEveningTmp(eveningTmp);
                    weatherObject.setHumidity(humidity);
                    weatherObject.setImage(image);
                    weatherObject.setMaximumTmp(maximumTmp);
                    weatherObject.setMinimumTmp(minimumTmp);
                    weatherObject.setMorningTmp(morningTmp);
                    weatherObject.setNightTmp(nightTmp);
                    weatherObject.setPressure(pressure);
                    weatherObject.setWeatherType(weatherType);
                    weatherObject.setSpeed(speed);

                    mWeatherArrayList.add(weatherObject);
                }

            } catch (Exception e) {

            }
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                dbHelper.addRows(mWeatherArrayList);
                showListView();
            }
        }

    }
}
