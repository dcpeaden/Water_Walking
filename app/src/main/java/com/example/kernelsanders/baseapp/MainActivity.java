package com.example.kernelsanders.baseapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    public static final String channel_id = "personal_notifications";

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

        //Push Notification Button
        Button createNotificationButton = findViewById(R.id.NotificationButton);
        createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
            }
        });




        alg.updateHydrationLevel(((TextView) findViewById(R.id.PrintHydration)));
        ((TextView) findViewById(R.id.PrintSteps)).setText("Steps: " + numSteps);

        findViewById(R.id.hydrationButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alg.updateHydrationLevel(((TextView) findViewById(R.id.PrintHydration)));
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


    //Creates and displays a notification
    private void addNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                //.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Hello from Dr40")
                .setContentText("This is a test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = new NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(001, builder.build());

        /*//Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());*/
    }

}