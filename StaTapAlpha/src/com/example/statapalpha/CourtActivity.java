package com.example.statapalpha;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
public class CourtActivity extends Activity implements OnMenuItemClickListener{

	// Opens database
	SqliteHelper db;
	
	ArrayList<String> homePlayersIn = new ArrayList<String>();
	ArrayList<String> awayPlayersIn = new ArrayList<String>();
	ArrayList<String> homePlayersBench = new ArrayList<String>();
	ArrayList<String> awayPlayersBench = new ArrayList<String>();
	
	String team1, team2, team1n, team2n, gamename, gamenamen, tablename, message;
	int GameId, currentplay;
	String player = "0"; // Player number for current play
	String action = ""; // Action text for current play
	position position = new position(); // Position for current play
	int playNumber = 0;
	private PopupMenu popupMenu;
	boolean isHome = false;
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
		message = "There are currently "+Integer.toString(currentplay)+" play(s) in this game";
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		// Gets players
		getPlayers();
		refreshPlayers();
	}
	public void getCurrentPlay() {
		currentplay = (db.countPlays(tablename));
	}
	public void stats(View view) {
		Intent intent = new Intent(this, StatActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	intent.putExtra("TABLENAME", tablename);
    	startActivity(intent);
	}
	public void createTable() {
		db.createStatTable(tablename);
	}
	public void convertStrings() {
		Intent mIntent = getIntent();
		team1n = mIntent.getStringExtra("TEAM1");
		team2n = mIntent.getStringExtra("TEAM2");
		team1 = team1n.replaceAll(" ", "_").toLowerCase();
		team2 = team2n.replaceAll(" ", "_").toLowerCase();
		gamenamen = mIntent.getStringExtra("GAME_TITLE");
		gamename = gamenamen.replaceAll(" ", "_").toLowerCase();
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
		
	}
	@Override
	public void onBackPressed() {
		//DO NOTHING
		finish();
	}
	
	// Stores x and y coordinate
	public class position {
		public int x;
		public int y;
	}
	
	// Sets current player when a player button is clicked
	public void setPlayer(View v) {
		Button b = (Button)v;
		player = b.getText().toString();
		playerButton = b.getId();
		switch(b.getId()) {
		case R.id.p1: 
		case R.id.p2:
		case R.id.p3:
		case R.id.p4:
		case R.id.p5: isHome = true; break;
		default: isHome = false;
				break;
		}
	}
	
	// Sets string Action to whatever action the user taps
	// then records play to database.
	public void setAction(View v) {
		Button b = (Button)v;
		String toastAction = "";
		String message = "";
		String team = "";
		
		switch(b.getId()) {
		case R.id.fgMade: action = "F" + goal(position) + "H"; toastAction = "made " + goal(position) + " point shot";
		break;
		case R.id.fgMissed: action = "F" + goal(position) + "M"; toastAction = "missed " + goal(position) + " point shot";
		break;
		case R.id.ftMade: action = "FTH"; toastAction = "made freethrow";
		break;
		case R.id.ftMissed: action = "FTM"; toastAction = "missed freethrow";
		break;
		case R.id.rebound: action = "RB"; toastAction = "rebound";
		break;
		case R.id.assist: action = "AST"; toastAction = "assist";
		break;
		case R.id.block: action = "BL"; toastAction = "block";
		break;
		case R.id.steal: action = "STL"; toastAction = "steal";
		break;
		case R.id.turnover: action = "TO"; toastAction = "turnover";
		break;
		case R.id.sub: action = "SUB"; toastAction = "substitution";
		break;
		case R.id.foul: action = "FC"; toastAction = "commited foul";
		break;
		case R.id.undoPlay: undoPlay(v);
		break;
		}
		
		if (action == "SUB") {
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
		else {
			message = "Player " + player + " " + toastAction + " at ("  + Integer.toString(position.x) + ", " + Integer.toString(position.y) + ")";
		}
		
		if (isHome == true)
			team = team1;
		else
			team = team2;
		currentplay++;
		db.recordPlay(Integer.parseInt(player), team, action, position, tablename);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		refreshPlayers();
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
		db.undoPlay(tablename, currentplay);
		currentplay--;
		refreshPlayers();
		}
	}
	


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Button button = (Button)findViewById(playerButton);
		button.setText(item.getTitle());
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
		
		refreshPlayers();
		return false;
	}
	
	void refreshPlayers() {
		//First Player
		Button button = (Button)findViewById(R.id.p1);
		int jnum = Integer.parseInt(button.getText().toString());
		String team = team1;
		TextView textp = (TextView)findViewById(R.id.p1p);
		TextView textf = (TextView)findViewById(R.id.p1f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Second Player
		button = (Button)findViewById(R.id.p2);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p2p);
		textf = (TextView)findViewById(R.id.p2f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Third Player
		button = (Button)findViewById(R.id.p3);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p3p);
		textf = (TextView)findViewById(R.id.p3f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Fourth Player
		button = (Button)findViewById(R.id.p4);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p4p);
		textf = (TextView)findViewById(R.id.p4f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Fifth Player
		button = (Button)findViewById(R.id.p5);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p5p);
		textf = (TextView)findViewById(R.id.p5f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Sixth Player
		button = (Button)findViewById(R.id.p6);
		team = team2;
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p6p);
		textf = (TextView)findViewById(R.id.p6f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Seventh Player
		button = (Button)findViewById(R.id.p7);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p7p);
		textf = (TextView)findViewById(R.id.p7f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Eighth Player
		button = (Button)findViewById(R.id.p8);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p8p);
		textf = (TextView)findViewById(R.id.p8f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Ninth Player
		button = (Button)findViewById(R.id.p9);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p9p);
		textf = (TextView)findViewById(R.id.p9f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
		//Tenth Player
		button = (Button)findViewById(R.id.p10);
		jnum = Integer.parseInt(button.getText().toString());
		textp = (TextView)findViewById(R.id.p10p);
		textf = (TextView)findViewById(R.id.p10f);
		textp.setText(Integer.toString(db.getPoints(jnum, team, tablename)));
		textf.setText(Integer.toString(db.getFouls(jnum, team, tablename)));
	}
}