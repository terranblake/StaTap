package com.example.statapalpha;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SqliteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StaTap";
    private static final String KEY_ID = "id";
    private static final String TEAM_NAMES = "Team_Names";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE IF NOT EXISTS teamlist(id INTEGER PRIMARY KEY AUTOINCREMENT,Team_Names TEXT UNIQUE);");
        // create books table
    }
 public Cursor getTeams() {
	 SQLiteDatabase db = this.getWritableDatabase();
	 String[] projection = { "Team_Names" };
	 return db.query("teams",projection, null, null, null, null, null, null);
	 
 }
 public void addTeam(Team teamname){
     //for logging
	 Log.d("addBook", teamname.toString()); 

	 // 1. get reference to writable DB
	 SQLiteDatabase db = this.getWritableDatabase();

	 // 2. create ContentValues to add key "column"/value
	 ContentValues values = new ContentValues();
	 //values.put(TEAM_NAMES, teamname.getName()); // get title 

	 // 3. insert
	 db.insert("Team_Names", // table
     null, //nullColumnHack
     values); // key/value -> keys = column names/ values = column values

	 // 4. close
	 db.close(); 
 }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
 
        // create fresh books table
        this.onCreate(db);
    }
 
}