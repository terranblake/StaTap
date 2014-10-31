package com.example.statapalpha;

import com.example.statapalpha.HomeTable.HomeTableInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOperations extends SQLiteOpenHelper {

	public static final int database_version = 1;
	public String CREATE_QUERY = "CREATE TABLE" + HomeTableInfo.TABLE_NAME+ "(" + HomeTableInfo.GAME_NAME + "TEXT,";
	
	public DatabaseOperations(Context context) {
		super(context, HomeTableInfo.DATABASE_NAME, null, database_version);
		Log.d("Database operations", "Datavase created");//Message that make sure it's made
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) {
		// TODO Auto-generated method stub
		sdb.execSQL(CREATE_QUERY);//Creates table
		Log.d("Database operations","Table created");//Message that make sure it's made also

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	public void putInformation(DatabaseOperations dop,String(colum_name),String (colum_name)//Information where it is put
	{
		SQLiteDatabase SQ = dop.getWriteableDatabase();//Object that inserts the data
		ContentValues cv = new ContentValues();
		cv.put(HomeTableInfo.column_name,(name);//Puts the data into the column and also names it
		long k = SQ.insert(HomeTableInfo.TABLE_NAME,null, cv);//inserts the data into the table
		Log.d("Database operations", "One row inserted");//Makes sure the row got into the table
	}
	}

}
