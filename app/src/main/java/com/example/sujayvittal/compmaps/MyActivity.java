package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    LinkedQueue lq = new LinkedQueue();

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    TextView tvHeading;

    Button record, done, exit, directions;

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
         mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
        //Added for counting the number of steps
         mSensorManager.registerListener(this, mStepCounterSensor,SensorManager.SENSOR_DELAY_FASTEST);
         mSensorManager.registerListener(this, mStepDetectorSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;
        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            Toast.makeText(getApplicationContext(), "Step Count : " + value, Toast.LENGTH_LONG).show();

        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            Toast.makeText(getApplicationContext(), "Step Detector : "+ value, Toast.LENGTH_LONG).show();
        }


        // get the angle around the z-axis rotated
        final float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
        record = (Button) findViewById(R.id.button);

        //queue = new GenQueue<Directions>();
        //final DynamicQueueImpl queue = new DynamicQueueImpl();

        record.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    Thread.sleep(3000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                lq.insert((int)degree);
                lq.display();
                Toast.makeText(getApplicationContext(), "Success! The current destination has been added to the route!", Toast.LENGTH_LONG).show();
                done = (Button) findViewById(R.id.button2);
                done.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        try {
                            Thread.sleep(3000);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        lq.remove();
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        exit = (Button) findViewById(R.id.button5);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Thank you for using CompMaps!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        directions = (Button) findViewById(R.id.button4);
        directions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "No stored directions!", Toast.LENGTH_LONG).show();

            }
        });





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }


}