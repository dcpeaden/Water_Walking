package com.example.kernelsanders.baseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserSettings extends AppCompatActivity
{
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        userInfo = new UserInfo(getPreferences(MODE_PRIVATE));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ((TextView)findViewById(R.id.heightView)).setText(userInfo.getHeight()+" Ft");
        ((TextView)findViewById(R.id.weightView)).setText(userInfo.getWeight()+" Lbs");
        ((TextView)findViewById(R.id.ageView)).setText(userInfo.getAge()+" y/o");

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(UserSettings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.setInfo).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String text = ((TextView)findViewById(R.id.setHeightText)).getText().toString();
                try
                {
                    double d = Double.parseDouble(text);
                    userInfo.updateHeight(d);
                    ((TextView)findViewById(R.id.heightView)).setText(userInfo.getHeight() +" Ft");
                    ((TextView)findViewById(R.id.setHeightText)).setText("");
                }
                catch(Exception e) {

                }

                text = ((TextView)findViewById(R.id.setWeightText)).getText().toString();
                try
                {
                    double d = Double.parseDouble(text);
                    userInfo.updateWeight(d);
                    ((TextView)findViewById(R.id.weightView)).setText(userInfo.getWeight() +" Lbs");
                    ((TextView)findViewById(R.id.setWeightText)).setText("");
                }
                catch(Exception e)
                {

                }

                text = ((TextView)findViewById(R.id.setAgeText)).getText().toString();
                try
                {
                    int i = Integer.parseInt(text);
                    userInfo.updateAge(i);
                    ((TextView)findViewById(R.id.ageView)).setText(userInfo.getAge() +" y/o");
                    ((TextView)findViewById(R.id.setAgeText)).setText("");
                }
                catch(Exception e)
                {

                }
            }
        });
    }
}
