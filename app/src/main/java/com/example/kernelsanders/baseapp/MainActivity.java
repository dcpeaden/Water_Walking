package com.example.kernelsanders.baseapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener
{
    private TextView steps;
    private Button start;
    private Button stop;
    private Algorithm alg;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Steps: ";
    private int numSteps;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        alg = new Algorithm(getPreferences(MODE_PRIVATE));



        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("steps"))
        {
            numSteps = 0;
            editor.putString("steps", numSteps+"");
            editor.commit();
        }
        else
            numSteps = Integer.parseInt(sharedPreferences.getString("steps",""));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
        ((TextView)findViewById(R.id.PrintSteps)).setText("Steps: " + numSteps);

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

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        steps = (TextView) findViewById(R.id.PrintSteps);
        start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });

        stop = (Button) findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);

            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {}

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs)
    {
        numSteps = Integer.parseInt(sharedPreferences.getString("steps",""));
        if(numSteps < 1)
            new CountDownTimer(60000*30, 60000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    numSteps = 0;
                    editor.putString("steps", numSteps+"");
                    editor.commit();
                }
            }.start();
        numSteps++;
        editor.putString("steps", numSteps+"");
        editor.commit();
        steps.setText(TEXT_NUM_STEPS + numSteps);
        alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));;
    }
}
