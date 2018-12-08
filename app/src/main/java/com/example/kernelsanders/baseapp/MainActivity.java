package com.example.kernelsanders.baseapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    public static final String channel_id = "personal_notifications";
    public static TextView temperature;
    private TextView steps;
    private Button start;
    private Button stop;
    private Algorithm alg;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Steps: ";
    private int numSteps;
    //private PushNotification pn;
    Button notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alg = new Algorithm(getPreferences(MODE_PRIVATE));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*notify = findViewById(R.id.NotificationButton);
        notify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



            }
        });*/

        //Gets Temperature
        getTemp();





        alg.updateHydrationLevel(((TextView) findViewById(R.id.PrintHydration)));
        ((TextView) findViewById(R.id.PrintSteps)).setText("Steps: " + numSteps);

        findViewById(R.id.hydrationButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alg.updateHydrationLevel(((TextView) findViewById(R.id.PrintHydration)));
                getTemp();
            }
        });




        findViewById(R.id.userSettings).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserSettings.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.helpButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(MainActivity.this, HelpPage.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.concernsButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(MainActivity.this, ConcernsPage.class);
                startActivity(intent);
            }
        });


     /*  findViewById(R.id.NotificationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
               // Intent intent = new Intent(MainActivity.this, PushNotification.class);
                //startActivity(intent);
                //pn.notificationcall();
                //addNotification();
                //NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                  //      .setSmallIcon(R.mipmap.ic_launcher_round)
                    //    .setContentTitle("Hello from Dr40")
                      //  .setContentText("This is a test");

                Intent notificationIntent = new Intent(MainActivity.this, PushNotification.class);
                startActivity(notificationIntent);
                //PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //builder.setContentIntent(contentIntent);

                //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //manager.notify(0, builder.build());
            }
        });*/



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        steps = (TextView) findViewById(R.id.PrintSteps);
        start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });

        stop = (Button) findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);

            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        steps.setText(TEXT_NUM_STEPS + numSteps);
    }



    public void getTemp(){

        temperature = findViewById(R.id.printTemperature);

        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locMan.getBestProvider(new Criteria(), false);

        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && )
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = locMan.getLastKnownLocation(provider);

        GetTemperature task = new GetTemperature();

        task.execute("api.openweathermap.org/data/2.5/weather?lat=" + loc.getLatitude() + "&lon=" + loc.getLongitude() + "type=accurate&units=imperial&APPID=db8a864457e43bcb117f89892e102e88");

    }

}