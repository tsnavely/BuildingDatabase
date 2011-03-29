package com.Blue;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener{
	Button MUButton, GerdinButton;
	TextView Lat, Lon;
	DatabaseHelper myDBHelper;
	double latValue, lonValue;
	Cursor c;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myDBHelper = new DatabaseHelper(this);
        
        try{
        	myDBHelper.createDatabase();
        }catch (IOException ioe){
        	throw new Error("Unable to create database");
        }
        
        try{
        	myDBHelper.openDatabase();
        }catch (SQLException sqle){
        	throw sqle;
        }
        
        MUButton = (Button) findViewById(R.id.button1);
        GerdinButton = (Button) findViewById(R.id.button2);
        Lat = (TextView) findViewById(R.id.textView1);
        Lon = (TextView) findViewById(R.id.textView2);
        
        MUButton.setOnClickListener(this);
        GerdinButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.button1:
			c = myDBHelper.getGPS("Memorial Union");
			latValue = c.getDouble(1);
			lonValue = c.getDouble(2);
			Lat.setText(Double.toString(latValue));
			Lon.setText(Double.toString(lonValue));
			c.close();
			break;
		case R.id.button2:
			c = myDBHelper.getGPS("Gerdin Business Building");
			latValue = c.getDouble(1);
			lonValue = c.getDouble(2);
			Lat.setText(Double.toString(latValue));
			Lon.setText(Double.toString(lonValue));
			c.close();
			break;
		}
		
	}
}