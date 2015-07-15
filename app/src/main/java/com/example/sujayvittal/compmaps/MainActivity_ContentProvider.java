package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_ContentProvider extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();
        EditText text_name = (EditText)findViewById(R.id.txtName);
        EditText text_grade = (EditText)findViewById(R.id.txtGrade);
        values.put(MyProvider.NAME,
                text_name.getText().toString());

        values.put(MyProvider.GRADE,
                text_grade.getText().toString());

        Uri uri = getContentResolver().insert(
                MyProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {

        // Retrieve student records
        String URL = "content://com.example.provider.College/students";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(MyProvider._ID)) +
                                ", " +  c.getString(c.getColumnIndex( MyProvider.NAME)) +
                                ", " + c.getString(c.getColumnIndex( MyProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}