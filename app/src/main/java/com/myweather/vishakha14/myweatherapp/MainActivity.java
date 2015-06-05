package com.myweather.vishakha14.myweatherapp;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.myweather.vishakha14.myweatherapp.Classes.GPS;


public class MainActivity extends Activity {

    boolean isLoadedFragment;
    GPS gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLoadedFragment = false;
        setContentView(R.layout.activity_main);
        gps = new GPS(MainActivity.this);

        if (gps.locationServiceAvailable()) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            MainActivityFragment mfrg = new MainActivityFragment();
            ft.replace(R.id.mainFragment, mfrg);
            ft.addToBackStack("weather");
            ft.commit();

        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void showList() {

        if(isLoadedFragment){

           /* FragmentTransaction ft = getFragmentManager().beginTransaction();
            MainActivityFragment mfrg = new MainActivityFragment();
            ft.replace(R.id.mainFragment, mfrg);
            ft.addToBackStack("weather");
            ft.commit();*/

        }else{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            MainActivityFragment mfrg = new MainActivityFragment();
            ft.add(R.id.mainFragment,mfrg, "");
            ft.commit();
        }
        isLoadedFragment = true;

    }



}
