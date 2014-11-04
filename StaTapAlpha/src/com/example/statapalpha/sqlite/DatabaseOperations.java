package com.example.statapalpha.sqlite;

import java.sql.Date;

import com.example.statapalpha.model.Games;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseOperations extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "GameDB";
 
    // Books table name
    private static final String TABLE_GAMES = "games";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_HTN = "home_team_num";
    private static final String KEY_ATN = "away_team_num";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_HTN,KEY_ATN};
    
    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Defining the SQL Statements for creation
        String CREATE_GAMES = "CREATE TABLE games ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "title TEXT, "+
                "home_team_num INTEGER"+
                "away_team_num INTEGER"+
                "date DATE )";
        String CREATE_PLAYER = "CREATE TABLE players ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "title TEXT, "+
                "date DATE )";
        String CREATE_TEAMS = "CREATE TABLE teams ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT )";
        String CREATE_STATS = "CREATE TABLE stats ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT )";
        // Create the Tables
        db.execSQL(CREATE_GAMES);
        db.execSQL(CREATE_PLAYER);
        db.execSQL(CREATE_TEAMS);
        db.execSQL(CREATE_STATS);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS games");
 
        // create fresh books table
        this.onCreate(db);
    }
    
    public void addTeam(Games game){
        //for logging
    	Log.d("addGame", game.toString()); 

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();

    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(KEY_TITLE, game.getTitle()); // get title 
    	values.put(KEY_HTN, game.getHTN()); // get home team number
    	values.put(KEY_ATN, game.getATN()); // get away team number

    	// 3. insert
    	db.insert(TABLE_GAMES, // table
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close(); 
}
    
    
    
 
}
