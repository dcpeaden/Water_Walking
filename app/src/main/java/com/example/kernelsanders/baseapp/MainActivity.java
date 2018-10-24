package com.example.kernelsanders.baseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private Algorithm alg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        alg = new Algorithm(getPreferences(MODE_PRIVATE));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));

        findViewById(R.id.hydrationButton).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
            }
        });

        findViewById(R.id.userSettings).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, UserSettings.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.helpButton).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(MainActivity.this, HelpPage.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.concernsButton).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(MainActivity.this, ConcernsPage.class);
                startActivity(intent);
            }
        });
    }
}
