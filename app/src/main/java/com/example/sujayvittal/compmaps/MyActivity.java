package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;


/* Main activity*/

public class MyActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    LinkedQueue lq = new LinkedQueue();
    int data[] = new int[100];

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private static final String TAG = "DIRECTIONS";

    EditText fname1;

    TextView tv;

    TextView tvHeading;
    ArrayList<String> collection = new ArrayList<String>();
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
    private void printCollection(Collection collection) {

        for (Object obj : collection) {
            System.out.println(obj);
        }
    }
    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        tv.append("\n\nExternal Media: readable="
                + mExternalStorageAvailable + " writable=" + mExternalStorageWriteable);
        tv.setEnabled(false);
        tv.setClickable(false);
    }

    /** Method to write ascii text characters to file on SD card. Note that you must add a
     WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
     a FileNotFound Exception because you won't have write permission. */

    private void writeToSDFile(){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-storage.html#filesExternal

        File root = android.os.Environment.getExternalStorageDirectory();
        tv.append("\nExternal file system root: "+root);
        tv.setEnabled(false);
        tv.setClickable(false);

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "directions.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(collection);
            pw.println("Here is a second line.");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.append("\n\nFile written to:\n"+file);
        tv.setEnabled(false);
        tv.setClickable(false);
    }
    /** Method to read in a text file placed in the res/raw directory of the application. The
     method reads in all lines of the file sequentially. */

    private void readRaw(){
        tv.append("\n\nData read from res/raw/textfile.txt:\n");
        tv.setEnabled(false);
        tv.setClickable(false);
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);    // 2nd arg is buffer size

        // More efficient (less readable) implementation of above is the composite expression
                  /*BufferedReader br = new BufferedReader(new InputStreamReader(
                           this.getResources().openRawResource(R.raw.textfile)), 8192);*/

        try {
            String test;
            while (true){
                test = br.readLine();
                // readLine() returns null if no more lines in the file
                if(test == null) break;
                tv.append("\n"+"    "+test);
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.append("\n\nThat is all");
        tv.setEnabled(false);
        tv.setClickable(false);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {


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

        tv = (TextView) findViewById(R.id.tv);

        record.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long time_sub = SystemClock.uptimeMillis();
                fname1 = (EditText)findViewById(R.id.fname1);

                long time_current = System.currentTimeMillis();
                long time = time_sub-time_current;
                lq.insert((int)degree, (int)time);
                //lq.display();

                collection.add(lq.display());
                writeToSDFile();
                //Toast.makeText(getApplicationContext(), "Success! The current destination has been added to the route!", Toast.LENGTH_LONG).show();
                //printCollection(collection);

                fname1 = (EditText)findViewById(R.id.fname1);
                done = (Button) findViewById(R.id.button2);
                done.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        lq.display();
                        try {

                            String fpath = "/sdcard/"+fname1+".txt";
                            File file = new File(fpath);
                            // If file does not exists, then create it
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileWriter fw = new FileWriter(file.getAbsoluteFile());
                            BufferedWriter bw = new BufferedWriter(fw);
                            //bw.write();
                            while(lq.getSize()!=0) {
                                //bw.write(lq.remove());
                            }
                            lq.display();
                            bw.close();
                            Log.d("Suceess", "Sucess");

                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        System.out.println("This is before intent");
                        lq.display();
                        Intent i = new Intent(MyActivity.this, FileOperations.class);
                        startActivity(i);
            }
        });


                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
            }
        });


        exit = (Button) findViewById(R.id.button5);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                Intent intent = new Intent(MyActivity.this, confirmation.class);
                startActivity(intent);
            }
        });
        directions = (Button) findViewById(R.id.button4);
        directions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MyActivity.this, ListFileActivity.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "directions.txt is the Route File available", Toast.LENGTH_LONG).show();

            }
        });





    }
    public Boolean write(String fname){
        try {
            MyActivity ref = new MyActivity();
            String fpath = "/sdcard/"+fname+".txt";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            //bw.write();
            while(lq.getSize()!=0) {
                //bw.write(ref.lq.remove());
            }
            lq.display();
            bw.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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