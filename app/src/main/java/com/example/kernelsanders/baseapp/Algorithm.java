package com.example.kernelsanders.baseapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Algorithm extends AppCompatActivity
{
    private double hLevel;
    public UserInfo userInfo;

    public Algorithm(SharedPreferences sharedPreferences)
    {
        hLevel = 0;
        userInfo = new UserInfo(sharedPreferences);
    }

    public double calculateHydration()
    {
        //do calculation
        return 0;
    }

    public void updateHydrationLevel(TextView v)
    {
        v.setText("Hydration Level: " + hLevel);
    }
}
