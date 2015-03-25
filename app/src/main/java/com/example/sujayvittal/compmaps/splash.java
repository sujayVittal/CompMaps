package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by sujayvittal on 25/03/15.
 */
public class splash extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(splash.this,MyActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}


