package com.example.sujayvittal.compmaps;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity_TableLayout extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }
}
