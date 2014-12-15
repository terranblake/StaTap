package com.example.statapalpha;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class SqliteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StaTap";
    private static final String TEAM_NAMES = "Team_Names";
    private static final String GAME_TITLES = "game_name";
    private static final String JERSEY_NUM = "Jersey_num";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String TEAM_1 = "team1";
    private static final String TEAM_2 = "team2";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Create Teamlist Table
		db.execSQL("CREATE TABLE IF NOT EXISTS teams3(id INTEGER PRIMARY KEY AUTOINCREMENT,Team_Names TEXT UNIQUE);");
		
		//Create Games Table
		db.execSQL("CREATE TABLE IF NOT EXISTS games2(id INTEGER PRIMARY KEY AUTOINCREMENT, game_name TEXT)");
		
		// Create stats table
		db.execSQL("CREATE TABLE IF NOT EXISTS stats4(play_id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, game_id INTEGER, Jersey_num INTEGER, team_name TEXT, " +
				"half_num INTEGER, action TEXT, x_coord INTEGER, y_coord INTEGER);");
		
    }
    public Cursor getTeams() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "Team_Names" };
    	return db.query("teams3",projection, null, null, null, null, null, null);
    }
    public Cursor getGames() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "game_name" };
    	return db.query("games2",projection, null, null, null, null, null, null);
    }
    public Cursor getPlayerJNums(String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "jersey_num" };
    	return db.query(teamname,projection, null, null, null, null, null, null);
    	
    }
    public Cursor getPlayerFName(Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	return db.rawQuery("SELECT first_name FROM "+teamname+" WHERE jersey_num = "+jnum, null);
    }
    public Cursor getPlayerLName(Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	return db.rawQuery("SELECT last_name FROM "+teamname+" WHERE jersey_num = "+jnum, null);
    }
    public void addPlayer(String teamname, Integer jerseynum, String firstname, String lastname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(JERSEY_NUM, jerseynum);
    	values.put(FIRST_NAME, firstname.toString());
    	values.put(LAST_NAME, lastname.toString()); // get title 

    	// 3. insert
    	db.insert(teamname, // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close(); 
    }
    public Cursor getJNums(String teamname) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
    	String command = "SELECT jersey_num FROM "+teamname2;
    	return db.rawQuery(command, null);
    }
    public int getStats(String action, Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String command;
    	Cursor cursor;
    	int number = 99;
    	command = "SELECT count(action) FROM stats4 WHERE Jersey_num = "+jnum+" AND action = '"+action+"' AND team_name = '"+teamname+"'";
    	cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    		number = cursor.getInt(0);
    	}
    	return number;
    }
    public int getPoints(Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int twopt, threept, points;
    	int rawtwopt = 0;
    	int rawthreept = 0;
    	String command1, command2;
    	Cursor cursor1, cursor2;
    	command1 = "SELECT count(action) FROM stats4 WHERE Jersey_num = "+jnum+" AND action = 'F2H' AND team_name = '"+teamname+"'";
    	command2 = "SELECT count(action) FROM stats4 WHERE Jersey_num = "+jnum+" AND action = 'F3H' AND team_name = '"+teamname+"'";
    	cursor1 = db.rawQuery(command1, null);
    	cursor2 = db.rawQuery(command2, null);
    	if(cursor1.moveToFirst()){
    	    rawtwopt = cursor1.getInt(0);
    	}
    	if(cursor2.moveToFirst()){
    	    rawthreept = cursor2.getInt(0);
    	}
    	twopt = (rawtwopt * 2);
    	threept = (rawthreept * 3);
    	points = (twopt + threept);
    	return points;
    }
    public void addTeam(String teamname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(TEAM_NAMES, teamname.toString()); // get title 
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+teamname2+"(jersey_num INTEGER PRIMARY KEY UNIQUE, first_name TEXT, last_name TEXT)");
    	// 3. insert
    	db.insert("teams3", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values
    	
    	// 4. close
    	db.close(); 
    }
    public void createGame(String gamename, String team1, String team2) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String team1n, team2n;
    	team1n = team1.replaceAll(" ", "_").toLowerCase();
    	team2n = team2.replaceAll(" ", "_").toLowerCase();
    	ContentValues values = new ContentValues();
    	values.put(GAME_TITLES, gamename.toString());
    	values.put(GAME_TITLES, team1n.toString());
    	values.put(GAME_TITLES, team2n.toString());// get title 
    	ContentValues values2 = new ContentValues();
    	values2.put(TEAM_1, team1n.toString());
    	values2.put(TEAM_2, team2n.toString());
    	String gamenamen = gamename.replaceAll(" ", "_").toLowerCase();
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+gamenamen+"(id INTEGER PRIMARY KEY UNIQUE, team1 TEXT, team2 TEXT)");
    	db.insert("games2", // table name
    	    	null, //nullColumnHack
    	    	values);
    	db.insert(gamenamen, // table name
    	    	null, //nullColumnHack
    	    	values);
    }
    public void delTeam(String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ",  "_").toLowerCase();
    	db.execSQL("DROP TABLE IF EXISTS "+teamname2);
    	db.execSQL("DELETE FROM teams3 WHERE Team_Names = '"+teamname+"'");
    	
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
 
        // create fresh books table
        this.onCreate(db);
    }
    // For getting player info
    public Cursor getPlayerNumber() {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "stat_num" };
    	return db.query("player",projection, null, null, null, null, null, null);
    }
    
    // For updating player stats
    public void recordPlay(int player, String team, String action, CourtActivity.position position) {
    	
    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	onCreate(db);
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put("jersey_num", player); // get title 
    	values.put("action", action);
    	values.put("x_coord", position.x);
    	values.put("y_coord", position.y);
    	values.put("team_name", team);
    	
    	// 3. insert
    	db.insert("stats4", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values
    	
    	
    	// 4. close
    	db.close();  
    }
    public void undoPlay(String number)//See my comment Paul link might help
    {
    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	

    	//db.delete("stats", number, whereArgs)
    	db.delete("stats4", number ,null);
    	db.close();
    }

}
