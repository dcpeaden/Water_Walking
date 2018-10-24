package com.example.kernelsanders.baseapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class UserInfo //extends AppCompatActivity
{
    private double height;
    private double weight;
    //SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public UserInfo(SharedPreferences sharedPreferences)
    {
        //sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        height = 0;
        weight = 0;

        if(!sharedPreferences.contains("height"))
        {
            height = 0;
            editor.putString("height", height+"");
            editor.commit();
        }
        else
            height = Double.parseDouble(sharedPreferences.getString("height",""));

        if(!sharedPreferences.contains("weight"))
        {
            weight = 0;
            editor.putString("weight", weight+"");
            editor.commit();
        }
        else
            weight = Double.parseDouble(sharedPreferences.getString("weight",""));
    }

    public void updateHeight(double height)
    {
        this.height = height;
        editor.putString("height", height+"");
        editor.commit();
    }

    public void updateWeight(double weight)
    {
        this.weight = weight;
        editor.putString("weight", weight+"");
        editor.commit();
    }

    public double getHeight()
    {
        return height;
    }

    public double getWeight()
    {
        return weight;
    }
}
