package com.example.sujayvittal.compmaps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
* Created on April 4, 2015 by Sujay */

public class FileOperations extends Activity {
    //LinkedQueue lq = new LinkedQueue();

    public FileOperations() {
    }
    /*public Boolean write(String fname){
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
            while(ref.lq.getSize()!=0) {
                bw.write(ref.lq.remove());
            }

            ref.lq.display();
            bw.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/
    public String read(String fname){

        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/"+fname+".txt";
            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    EditText fname,fnameread;
    Button write,read;
    TextView filecon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operations);
        fname = (EditText)findViewById(R.id.fname);

        fnameread = (EditText)findViewById(R.id.fnameread);
        write = (Button)findViewById(R.id.btnwrite);
        read = (Button)findViewById(R.id.btnread);
        filecon = (TextView)findViewById(R.id.filecon);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String filename = fname.getText().toString();
                MyActivity fop = new MyActivity();
                //Loop begins now

                /*int [] filecontent = new int[100];
                for(int p = 0; p <lq.size; p++) {
                    filecontent[p] = lq.remove();
                }*/

                fop.write(filename);
                if(fop.write(filename)){
                    Toast.makeText(getApplicationContext(), filename + ".txt created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String readfilename = fnameread.getText().toString();
                FileOperations fop = new FileOperations();
                String text = fop.read(readfilename);
                if(text != null){
                    filecon.setText(text);
                }
                else {
                    Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
                    filecon.setText(null);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_operations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
