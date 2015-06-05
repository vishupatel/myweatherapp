package com.myweather.vishakha14.myweatherapp.Classes;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    private String[] projection = {
                "dt",
                "maxTmp",
                "minTmp",
                "dayTmp",
                "nightTmp",
                "morningTmp",
                "eveningTmp"};
    private static final String DATABASE_CREATE =
            "CREATE TABLE LocationTemp (" +
                    "dt TEXT NOT NULL, " +
                    "maxTmp TEXT NOT NULL, " +
                    "minTmp TEXT NOT NULL, " +
                    "dayTmp TEXT NOT NULL, " +
                    "nightTmp TEXT NOT NULL, " +
                    "morningTmp INTEGER NOT NULL, " +
                    "eveningTmp INTEGER NOT NULL " +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS LocationTemp";

    public DataBaseHelper(Context context) {
        super(context, "weather", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void insertWeather(Weather mWeather) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("dt", mWeather.getDt());
        cv.put("maxTmp", mWeather.getMaximumTmp());
        cv.put("minTmp", mWeather.getMinimumTmp());
        cv.put("dayTmp", mWeather.getDayTmp());
        cv.put("nightTmp", mWeather.getNightTmp());
        cv.put("morningTmp", mWeather.getMorningTmp());
        cv.put("eveningTmp", mWeather.getEveningTmp());
        db.insert("LocationTemp", null, cv);
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("LocationTemp", projection, null, null, null, null, null);
    }

    /*public Cursor getRowByID(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] ids = {String.valueOf(id)};
        return db.query("LocationTemp", projection, Contract.PhotoEntry._ID + "==?", ids, null, null, null);
    }*/

    /*public void deleteRow(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(id)};
        db.delete(Contract.PhotoEntry.TABLE_NAME, Contract.PhotoEntry._ID + "==?", ids);
    }*/

    public void addRows(List<Weather> weathers) {
        for (Weather weather : weathers) {
            insertWeather(weather);
        }
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from LocationTemp");
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}