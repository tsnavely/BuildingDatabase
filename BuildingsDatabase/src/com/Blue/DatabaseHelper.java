package com.Blue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static String DB_PATH = "/data/data/com.Blue/databases/";
	private static String DB_NAME = "Buildings.db";
	private SQLiteDatabase myDatabase;
	private final Context myContext;
	
	public DatabaseHelper(Context context){
		super(context, DB_NAME, null, 1);
		myContext = context;
	}
	
	public void createDatabase() throws IOException{
		boolean dbExist = checkDatabase();
		
		if(dbExist){}
		else{
			getReadableDatabase();
		}
		
		try{
			copyDatabase();
		}catch(IOException e){
			throw new Error("Error copying database");
		}
	}
	
	private boolean checkDatabase(){
		SQLiteDatabase checkDB = null;
		
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){}
		
		if(checkDB != null){
			checkDB.close();
		}
		
		return checkDB != null ? true : false;
	}
	
	private void copyDatabase() throws IOException{
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		byte[] buffer = new byte[1024];
		int length;
		while((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}
		
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	public void openDatabase() throws SQLException{
		String myPath = DB_PATH + DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	@Override
	public synchronized void close(){
		if(myDatabase != null){
			myDatabase.close();
			super.close();
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
	}
	
	public Cursor getGPS(String buildingName){
		Cursor myCursor = myDatabase.query(false, "Buildings", new String[] {"BuildingName", "LAT", "LON"}, "BuildingName='"+buildingName+"'", null, null, null, null, null);
		if(myCursor != null){
			myCursor.moveToFirst();
		}
		return myCursor;
	}
}
