package com.example.statapalpha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;
import java.util.ArrayList;

// Court Screen
@SuppressLint("DefaultLocale")
public class CourtActivity extends Activity implements OnMenuItemClickListener{

	// Opens database
	SqliteHelper db;
	
	ArrayList<String> homePlayersIn = new ArrayList<String>();
	ArrayList<String> awayPlayersIn = new ArrayList<String>();
	ArrayList<String> homePlayersBench = new ArrayList<String>();
	ArrayList<String> awayPlayersBench = new ArrayList<String>();
	
	String team, team1, team2, team1n, team2n, gamename, gamenamen, tablename, message;
	Button b, c; //b = button c=player
	TextView d, e; //d = Player Points TV	e = Player Fouls TV
	int GameId, currentplay;
	String player; // Player number for current play
	String action = ""; // Action text for current play
	position position = new position(); // Position for current play
	int playNumber = 0;
	private PopupMenu popupMenu;
	boolean isHome;
	int playerButton = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
		db = new SqliteHelper(this.getApplicationContext());
		//Get Team 1 and 2 and Game Title
		
		
		convertStrings();
		createTable();
		getCurrentPlay();
		message = "There are currently "+Integer.toString(currentplay-1)+" play(s) in this game";
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		// Gets players
		getPlayers();
		initialization();
	}
	public void initialization() {
		//This is just so we don't have any errors to start with
		team = team1;
		c = (Button) findViewById(R.id.p1);
		d = (TextView) findViewById(R.id.p1p);
		e = (TextView) findViewById(R.id.p1f);
		isHome = true;
		initialLoad();
	}
	public void getCurrentPlay() {
		currentplay = ((db.countPlays(tablename))+1);
	}
	public void stats(View view) {
		Intent intent = new Intent(this, StatActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	intent.putExtra("TABLENAME", tablename);
    	finish();
    	startActivity(intent);
	}
	public void createTable() {
		db.createStatTable(tablename);
	}
	public void convertStrings() {
		Intent mIntent = getIntent();
		team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		gamename = mIntent.getStringExtra("GAME_TITLE");
		tablename = gamename;
	}
	// Populates arrays with player numbers
	void getPlayers() {
		Cursor cursorH = db.getPlayerJNums(team1);
		Cursor cursorA = db.getPlayerJNums(team2);
		Button button = (Button) findViewById(R.id.p1);
		int i = 1;
		if (cursorH != null && cursorH.getCount() != 0) {
		    cursorH.moveToFirst();
		    while (!cursorH.isAfterLast()) {
		    	if (i < 6) {
		        homePlayersIn.add(cursorH.getString(cursorH.getColumnIndex("jersey_num")));		 
		        i++;		  
		    	} else {
		    	homePlayersBench.add(cursorH.getString(cursorH.getColumnIndex("jersey_num")));
		    	}
		    	cursorH.moveToNext();
		    }
		}
		String string;
		string = homePlayersIn.get(0);
		player = string;
		button.setText(string);
		string = homePlayersIn.get(1);
		button = (Button) findViewById(R.id.p2);
		button.setText(string);
		string = homePlayersIn.get(2);
		button = (Button) findViewById(R.id.p3);
		button.setText(string);
		string = homePlayersIn.get(3);
		button = (Button) findViewById(R.id.p4);
		button.setText(string);
		string = homePlayersIn.get(4);
		button = (Button) findViewById(R.id.p5);
		button.setText(string);
		i=1;
		if (cursorA != null && cursorA.getCount() != 0) {
		    cursorA.moveToFirst();
		    while (!cursorA.isAfterLast()) {
		    	if (i < 6) {
		        awayPlayersIn.add(cursorA.getString(cursorA.getColumnIndex("jersey_num")));		 
		        i++;		  
		    	} else {
		    	awayPlayersBench.add(cursorA.getString(cursorA.getColumnIndex("jersey_num")));
		    	}
		    	cursorA.moveToNext();
		    }
		}
		string = awayPlayersIn.get(0);
		button = (Button) findViewById(R.id.p6);
		button.setText(string);
		string = awayPlayersIn.get(1);
		button = (Button) findViewById(R.id.p7);
		button.setText(string);
		string = awayPlayersIn.get(2);
		button = (Button) findViewById(R.id.p8);
		button.setText(string);
		string = awayPlayersIn.get(3);
		button = (Button) findViewById(R.id.p9);
		button.setText(string);
		string = awayPlayersIn.get(4);
		button = (Button) findViewById(R.id.p10);
		button.setText(string);

    	cursorH.close();
    	cursorA.close();
	}
	@Override
	public void onBackPressed() {
		//DO NOTHING
		Intent intent = new Intent(this, HomeScreen.class);
		finish();
		startActivity(intent);
	}
	
	// Stores x and y coordinate
	public class position {
		public int x;
		public int y;
	}
	
	// Sets current player when a player button is clicked
	public void setPlayer(View v) {
		c = (Button)v;
		player = c.getText().toString();
		playerButton = c.getId();
		switch(c.getId()) {
		case R.id.p1: team = team1; d = (TextView) findViewById(R.id.p1p); e = (TextView) findViewById(R.id.p1f); isHome=true;
		break;
		case R.id.p2: team = team1; d = (TextView) findViewById(R.id.p2p); e = (TextView) findViewById(R.id.p2f); isHome=true;
		break;
		case R.id.p3: team = team1; d = (TextView) findViewById(R.id.p3p); e = (TextView) findViewById(R.id.p3f); isHome=true;
		break;
		case R.id.p4: team = team1; d = (TextView) findViewById(R.id.p4p); e = (TextView) findViewById(R.id.p4f); isHome=true;
		break;
		case R.id.p5: team = team1; d = (TextView) findViewById(R.id.p5p); e = (TextView) findViewById(R.id.p5f); isHome=true;
		break;
		case R.id.p6: team = team2; d = (TextView) findViewById(R.id.p6p); e = (TextView) findViewById(R.id.p6f); isHome=false;
		break;
		case R.id.p7: team = team2; d = (TextView) findViewById(R.id.p7p); e = (TextView) findViewById(R.id.p7f); isHome=false;
		break;
		case R.id.p8: team = team2; d = (TextView) findViewById(R.id.p8p); e = (TextView) findViewById(R.id.p8f); isHome=false;
		break;
		case R.id.p9: team = team2; d = (TextView) findViewById(R.id.p9p); e = (TextView) findViewById(R.id.p9f); isHome=false;
		break;
		case R.id.p10: team = team2; d = (TextView) findViewById(R.id.p10p); e = (TextView) findViewById(R.id.p10f); isHome=false;
		break;
		
		}
	}
	/*
	Recording Functions 
	*/
	public void recordFoul(View v) {
		int jnum = Integer.parseInt(c.getText().toString());
		db.recordPlay(Integer.parseInt(player), team, "FC", position, tablename);
		e.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		currentplay++;
	}
	public void recordRebound(View v) {
		db.recordPlay(Integer.parseInt(player), team, "RB", position, tablename);
		currentplay++;
	}
	public void recordAssist(View v) {
		db.recordPlay(Integer.parseInt(player), team, "AST", position, tablename);
		currentplay++;
	}
	public void recordBlock(View v) {
		db.recordPlay(Integer.parseInt(player), team, "BL", position, tablename);
		currentplay++;
	}
	public void recordSteal(View v) {
		db.recordPlay(Integer.parseInt(player), team, "STL", position, tablename);
		currentplay++;
	}
	public void recordTurnover(View v) {
		db.recordPlay(Integer.parseInt(player), team, "TO", position, tablename);
		currentplay++;
	}
	public void recordMadeShot(View v) {
		b = (Button)v;
		
		switch(b.getId()) {
		case R.id.fgMade:
			action = "F" + goal(position) + "H";break;
		case R.id.ftMade:
			action = "FTH";break;
		}
		int jnum = Integer.parseInt(c.getText().toString());
		
		db.recordPlay(Integer.parseInt(player), team, action, position, tablename);
		d.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		currentplay++;
		refreshScore();
		
	}
	public void recordMissedShot(View v) {
		b = (Button)v;
		
		switch(b.getId()) {
		case R.id.fgMissed:
			action = "F" + goal(position) + "M";break;
		case R.id.ftMissed:
			action = "FTM";break;
		}
		db.recordPlay(Integer.parseInt(player), team, action, position, tablename);
		currentplay++;
	}
	
	public void substitution(View v) {
		popupMenu = new PopupMenu(this.getBaseContext(), v);
		Menu menu = popupMenu.getMenu();
		
		if (isHome == true) {
			for (String number : homePlayersBench) {
				menu.add(number);
			}
		}
		else {
			for (String number : awayPlayersBench) {
					menu.add(number);
				}
		}
		
		popupMenu.setOnMenuItemClickListener(this);
		popupMenu.show();
		
		return;
	}
	
	// Gets tap position and saves it to 'position'
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			position.x = (int)event.getX(0);
			position.y = (int)event.getY(0);
		}
	    
	    return super.onTouchEvent(event);
	}
	
	int goal(position position) {
		int points=3;
		
		if ((Math.sqrt(Math.pow((position.x - 2170), 2) + Math.pow((position.y - 1000), 2)) < 500) || (Math.sqrt(Math.pow((position.x - 400), 2) + Math.pow((position.y - 1000), 2)) < 500)) points = 2;
		
		return points;
	}
	

	public void undoPlay(View view) {
		
		if (currentplay == 1) {
			Toast.makeText(CourtActivity.this, "No plays to Undo", Toast.LENGTH_SHORT).show();
		} else {
			String undoPlayAction = db.grabUndoAction(tablename, currentplay);
			int jnum = db.grabUndoJNum(tablename, currentplay);
			String jnumS = Integer.toString(jnum);
			String teamname = db.grabTeamName(tablename, currentplay);
		db.undoPlay(tablename, currentplay);
		switch(undoPlayAction) {
		case "F2H":case "F3H":case "FTH":
			if (teamname.equals(team1)) {
				
				if (homePlayersIn.contains(jnumS)) { 
					undoHomeScore(jnumS);
				}
			} else {
				if (awayPlayersIn.contains(jnumS)) { 
					undoAwayScore(jnumS);
				}
			}
			break;
		case "FC":
			if (teamname.equals(team1)) {
				
				if (homePlayersIn.contains(jnumS)) { 
					undoHomeFoul(jnumS);
				}
			} else {
				if (awayPlayersIn.contains(jnumS)) { 
					undoAwayFoul(jnumS);
				}
			}
			break;
		}
		currentplay--;
		}
	}
	public void undoHomeFoul(String jnum) {
		Button z,y,x,w,v;
		TextView u;
		int jnumi = Integer.parseInt(jnum);
		z = (Button) findViewById(R.id.p1);
		y = (Button) findViewById(R.id.p2); 
		x = (Button) findViewById(R.id.p3); 
		w = (Button) findViewById(R.id.p4); 
		v = (Button) findViewById(R.id.p5); 
		//z.getText().toString();
		
		if (jnum.equals(z.getText().toString())) {
			u = (TextView)findViewById(R.id.p1f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(y.getText().toString())) {
			u = (TextView)findViewById(R.id.p2f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(x.getText().toString())) {
			u = (TextView)findViewById(R.id.p3f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(w.getText().toString())) {
			u = (TextView)findViewById(R.id.p4f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(v.getText().toString())) {
			u = (TextView)findViewById(R.id.p5f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} 
	}
	public void undoAwayFoul(String jnum) {
		Button z,y,x,w,v;
		TextView u;
		int jnumi = Integer.parseInt(jnum);
		z = (Button) findViewById(R.id.p6);
		y = (Button) findViewById(R.id.p7); 
		x = (Button) findViewById(R.id.p8); 
		w = (Button) findViewById(R.id.p9); 
		v = (Button) findViewById(R.id.p10); 
		//z.getText().toString();
		
		if (jnum.equals(z.getText().toString())) {
			u = (TextView)findViewById(R.id.p6f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(y.getText().toString())) {
			u = (TextView)findViewById(R.id.p7f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(x.getText().toString())) {
			u = (TextView)findViewById(R.id.p8f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(w.getText().toString())) {
			u = (TextView)findViewById(R.id.p9f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} else if (jnum.equals(v.getText().toString())) {
			u = (TextView)findViewById(R.id.p10f);
			u.setText(Integer.toString(db.getFouls(jnumi, team, tablename)));
		} 
	}
	public void undoHomeScore(String jnum) {
		Button z,y,x,w,v;
		TextView u;
		int jnumi = Integer.parseInt(jnum);
		z = (Button) findViewById(R.id.p1);
		y = (Button) findViewById(R.id.p2); 
		x = (Button) findViewById(R.id.p3); 
		w = (Button) findViewById(R.id.p4); 
		v = (Button) findViewById(R.id.p5); 
		//z.getText().toString();
		
		if (jnum.equals(z.getText().toString())) {
			u = (TextView)findViewById(R.id.p1p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(y.getText().toString())) {
			u = (TextView)findViewById(R.id.p2p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(x.getText().toString())) {
			u = (TextView)findViewById(R.id.p3p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(w.getText().toString())) {
			u = (TextView)findViewById(R.id.p4p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(v.getText().toString())) {
			u = (TextView)findViewById(R.id.p5p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} 
		refreshScore();
	}
	public void undoAwayScore(String jnum) {
		Button z,y,x,w,v;
		TextView u;
		int jnumi = Integer.parseInt(jnum);
		z = (Button) findViewById(R.id.p6);
		y = (Button) findViewById(R.id.p7); 
		x = (Button) findViewById(R.id.p8); 
		w = (Button) findViewById(R.id.p9); 
		v = (Button) findViewById(R.id.p10); 
		//z.getText().toString();
		
		if (jnum.equals(z.getText().toString())) {
			u = (TextView)findViewById(R.id.p6p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(y.getText().toString())) {
			u = (TextView)findViewById(R.id.p7p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(x.getText().toString())) {
			u = (TextView)findViewById(R.id.p8p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(w.getText().toString())) {
			u = (TextView)findViewById(R.id.p9p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} else if (jnum.equals(v.getText().toString())) {
			u = (TextView)findViewById(R.id.p10p);
			u.setText(Integer.toString(db.getPoints(jnumi, team, tablename)));
		} 
		refreshScore();
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Button button = (Button)findViewById(playerButton);
		int jnum = Integer.parseInt(item.getTitle().toString());
		button.setText(item.getTitle());
		d.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		e.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		if (isHome == true) {
			homePlayersIn.remove(player);
			homePlayersBench.add(player);
			homePlayersIn.add(item.getTitle().toString());
			homePlayersBench.remove(item.getTitle().toString());
		}
		else {
			awayPlayersIn.remove(player);
			awayPlayersBench.add(player);
			awayPlayersIn.add(item.getTitle().toString());
			awayPlayersBench.remove(item.getTitle().toString());
		}
		return false;
	}
	public void refreshScore() {
		TextView textP1 = (TextView) findViewById(R.id.tvS1);
		TextView textP2 = (TextView) findViewById(R.id.tvS2);
		textP1.setText(Integer.toString(db.grabScore(tablename, team1)));
		textP2.setText(Integer.toString(db.grabScore(tablename, team2)));
	}
	
	public void initialLoad() {
		//First Player
		Button button = (Button)findViewById(R.id.p1);
		int jnum = Integer.parseInt(button.getText().toString());
		String team = team1;
		TextView textf = (TextView)findViewById(R.id.p1f);
		TextView textp = (TextView)findViewById(R.id.p1p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Second Player
		button = (Button)findViewById(R.id.p2);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p2f);
		textp = (TextView)findViewById(R.id.p2p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Third Player
		button = (Button)findViewById(R.id.p3);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p3f);
		textp = (TextView)findViewById(R.id.p3p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Fourth Player
		button = (Button)findViewById(R.id.p4);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p4f);
		textp = (TextView)findViewById(R.id.p4p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Fifth Player
		button = (Button)findViewById(R.id.p5);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p5f);
		textp = (TextView)findViewById(R.id.p5p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Sixth Player
		button = (Button)findViewById(R.id.p6);
		team = team2;
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p6f);
		textp = (TextView)findViewById(R.id.p6p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Seventh Player
		button = (Button)findViewById(R.id.p7);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p7f);
		textp = (TextView)findViewById(R.id.p7p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Eighth Player
		button = (Button)findViewById(R.id.p8);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p8f);
		textp = (TextView)findViewById(R.id.p8p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Ninth Player
		button = (Button)findViewById(R.id.p9);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p9f);
		textp = (TextView)findViewById(R.id.p9p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Tenth Player
		button = (Button)findViewById(R.id.p10);
		jnum = Integer.parseInt(button.getText().toString());
		textf = (TextView)findViewById(R.id.p10f);
		textp = (TextView)findViewById(R.id.p10p);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		
		textf = (TextView) findViewById(R.id.tvS1);
		textp = (TextView) findViewById(R.id.tvS2);
		textf.setText(Integer.toString(db.grabScore(tablename, team1)));
		textp.setText(Integer.toString(db.grabScore(tablename, team2)));
	}
}
