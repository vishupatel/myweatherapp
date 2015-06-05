package com.myweather.vishakha14.myweatherapp.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myweather.vishakha14.myweatherapp.Classes.Weather;
import com.myweather.vishakha14.myweatherapp.R;

import java.io.InputStream;
import java.net.URL;

public class WeatherDetailFragment extends Fragment {

    Weather mWeather;
    TextView tvMorning, tvEvening, tvNight, tvMax, tvMin;
    ImageView imageView;

    public WeatherDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);

        tvMorning = (TextView) view.findViewById(R.id.TVMorningTemp);
        tvEvening = (TextView) view.findViewById(R.id.TVNightTemp);
        tvNight = (TextView) view.findViewById(R.id.TVEveningTemp);
        tvMax = (TextView) view.findViewById(R.id.TVMaxTemp);
        tvMin = (TextView) view.findViewById(R.id.TVMinTemp);

        tvMorning.setText(String.valueOf(mWeather.getMorningTmp()));
        tvEvening.setText(String.valueOf(mWeather.getEveningTmp()));
        tvNight.setText(String.valueOf(mWeather.getNightTmp()));
        tvMin.setText(String.valueOf(mWeather.getMinimumTmp()));
        tvMax.setText(String.valueOf(mWeather.getMaximumTmp()));
        imageView = (ImageView) view.findViewById(R.id.imageView);

        try {

            String imageURL = "http://openweathermap.org/img/w/" + mWeather.getImage() + ".png";
            DownloadImageTask setImage = new DownloadImageTask();
            setImage.execute(imageURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    public void setmWeather(Weather mWeather) {
        this.mWeather = mWeather;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
       // ImageView bmImage;

        public DownloadImageTask() {
          //  this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
