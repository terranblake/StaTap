package com.example.statapalpha;
 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class SqliteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StaTap";
 
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE IF NOT EXISTS teams(Team_Num INTEGER PRIMARY KEY AUTOINCREMENT,Team_Names TEXT UNIQUE);");
        // create books table
    }
 public Cursor getTeams() {
	 SQLiteDatabase db = this.getWritableDatabase();
	 String[] projection = { "Team_Names" };
	 return db.query("teams",projection, null, null, null, null, null, null);
	 
 }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
 
        // create fresh books table
        this.onCreate(db);
    }
 
}