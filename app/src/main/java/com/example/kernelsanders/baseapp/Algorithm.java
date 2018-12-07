package com.example.kernelsanders.baseapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Algorithm extends AppCompatActivity
{
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private double hLevel;
    public UserInfo userInfo;
    private int steps;
    private double temp;

    public Algorithm(SharedPreferences sharedPreferences)
    {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hLevel = 0;
        userInfo = new UserInfo(sharedPreferences);
    }

    public void calculateHydration()
    {

        steps = Integer.parseInt(sharedPreferences.getString("steps",""));
        if(steps < 3000)
        {
            if(temp < 85)
                hLevel = 8;
            else if(temp < 90)
                hLevel = 12;
            else
                hLevel = 16;
        }
        else if (steps < 6360)
        {
            if(temp < 90)
                hLevel = 12;
            else
                hLevel = 16;
        }
        else
            hLevel = 16;
    }

    public void updateHydrationLevel(TextView v)
    {
        v.setText("Hydration Level: " + hLevel);
        calculateHydration();
    }
}
