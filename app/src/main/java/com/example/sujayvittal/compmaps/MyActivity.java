package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/* Main activity*/

public class MyActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    LinkedQueue lq = new LinkedQueue();
    TimeQueue cq = new TimeQueue();
    int data[] = new int[100];


    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private static final String TAG = "DIRECTIONS";


    TextView tv;
    public float degree;

    TextView tvHeading;
    ArrayList<String> collection = new ArrayList<String>();
    ArrayList<String> collection2 = new ArrayList<String>();
    Button record, done, exit, directions, check, check_done;


    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
        //Added for counting the number of steps
        mSensorManager.registerListener(this, mStepCounterSensor,SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
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


    /** Method to write ascii text characters to file on SD card. Note that you must add a
     WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
     a FileNotFound Exception because you won't have write permission. */

    private void writeToSDFile(ArrayList<String> data,String filename){

        // Find the root of the external storage.

        File root = android.os.Environment.getExternalStorageDirectory();
        tv.append("\nExternal file system root: "+root);
        tv.setEnabled(false);
        tv.setClickable(false);

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(data);
            //pw.println("Here is a second line.");
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
    public static double timeElapsed(double min, double max) {

        long time = System.currentTimeMillis();
        long time2= SystemClock.currentThreadTimeMillis();

        long timeNo = time-time2;

        // nextInt is normally exclusive of the top value,
        Random rand = new Random();
        // so add 1 to make it inclusive
        double timeNum = rand.nextInt((int) ((max - min) + 1.0)) + min;

        return timeNum;
    }


    private void readRaw() throws IOException{
        FileInputStream f1 =  new FileInputStream("directions.txt");
        FileInputStream f2 = new FileInputStream("time.txt");
        FileInputStream f3 = new FileInputStream("check_directions.txt");
        FileInputStream f4 = new FileInputStream("check_time.txt");
        String directions1, time1, time_test, directions_test;

        //recorded directions are opened
        BufferedReader myInput = new BufferedReader
                (new InputStreamReader(f1));
        directions1 = myInput.readLine();
        String[] sb = new String[1000];
        for(int d=0; d<directions1.length(); d++) {
            sb[d]=directions1;
        }

        //recorded time is opened
        BufferedReader myInput2 = new BufferedReader
                (new InputStreamReader(f2));
        time1 = myInput2.readLine();
        String[] sb2 = new String[1000];
        for(int d=0; d<time1.length(); d++) {
            sb2[d]=time1;
        }

        //check_directions file is read
        BufferedReader myInput3 = new BufferedReader
                (new InputStreamReader(f3));
        directions_test = myInput3.readLine();
        String[] sb3 = new String[1000];
        for(int d=0; d<directions_test.length(); d++) {
            sb3[d]=directions_test;
        }

        //Check_time file is read
        BufferedReader myInput4 = new BufferedReader
                (new InputStreamReader(f4));
        time_test = myInput4.readLine();
        String[] sb4 = new String[1000];
        for(int d=0; d<time_test.length(); d++) {
            sb4[d]=time_test;
        }
        if(sb.length == sb3.length){
            int count=0, sum=0;
            for(int g=1 ; g<sb.length; g++){
                if(sb[g]==sb3[g]) count++;
                else {
                    for(int k=count; k<sb2.length; k++){
                        sum+=Integer.parseInt(sb2[k]);
                    }
                }
            }
        }
        if(sb.length>sb3.length){
            tv.append("\n\nRE-ROUTE! You are taking a long path.\n");
        }
        if(sb.length<sb3.length){
            int sum=0;
            for(int g=sb.length ; g<sb3.length; g++){
                sum+=Integer.parseInt(sb3[g]);
            }
            tv.append("\n\n You will reach your destination in "+sum+"seconds\n\n");
        }




    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        // get the angle around the z-axis rotated
        degree = Math.round(event.values[0]);

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

            long time_sub = System.currentTimeMillis();
            public void onClick(View v) {

                final Timer t = new Timer();
    //Set the schedule function and rate
                t.scheduleAtFixedRate(new TimerTask() {

                                          @Override
                                          public void run() {
                                              long time_current = System.currentTimeMillis();
                                              long time1 = (time_current-time_sub)%1000000000;
                                              //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                              lq.insert((int) degree);
                                              cq.insert(time1);
                                              cq.display();
                                          }

                                      },
    //Set how long before to start calling the TimerTask (in milliseconds)
                        0,
    //Set the amount of time between each execution (in milliseconds)
                        3000);

                //fname1 = (EditText)findViewById(R.id.fname1);

                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                done = (Button) findViewById(R.id.button2);
                done.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(t!= null){
                            t.cancel();
                        }
                        collection.add(lq.getValues());
                        String filename = "directions.txt";
                        writeToSDFile(collection, filename);
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                    }
                });
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
                Intent i = new Intent(MyActivity.this, MainActivity_TableLayout.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "This intent demonstrates the use of table layout!", Toast.LENGTH_LONG).show();

            }
        });
        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(MyActivity.this, MainActivity_ContentProvider.class);
                startActivity(i);
            }
        });

        check_done = (Button) findViewById(R.id.donecheck);
        check_done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Toast.makeText(getApplicationContext(), "Feature will be made available in the next version", Toast.LENGTH_LONG).show();
                collection2.add(cq.getValues());
                String filename = "time.txt";
                writeToSDFile(collection2, filename);
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

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