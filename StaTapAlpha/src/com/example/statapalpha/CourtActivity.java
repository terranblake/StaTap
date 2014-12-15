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
	
	String team1, team2, team1n, team2n;
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
		// Gets players
		getPlayers();
	}
	public void stats(View view) {
		Intent intent = new Intent(this, StatActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	startActivity(intent);
	}
	public void convertStrings() {
		Intent mIntent = getIntent();
		team1n = mIntent.getStringExtra("TEAM1");
		team2n = mIntent.getStringExtra("TEAM2");
		team1 = team1n.replaceAll(" ", "_").toLowerCase();
		team2 = team2n.replaceAll(" ", "_").toLowerCase();
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
		
		db.recordPlay(Integer.parseInt(player), team, action, position);
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
		
		if ((Math.sqrt(Math.pow((position.x - 1082), 2) + Math.pow((position.y - 453), 2)) < 270) || (Math.sqrt(Math.pow((position.x - 193), 2) + Math.pow((position.y - 453), 2)) < 270)) points = 2;
		
		return points;
	}
	

	public void undoPlay(View v) {
		
		db.undoPlay(Integer.toString(playNumber));
	}
	


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Button button = (Button)findViewById(playerButton);
		button.setText(item.getTitle());
		
		refreshPlayers();
		return false;
	}
	
	void refreshPlayers() {
		
		Button button = (Button)findViewById(R.id.p1);
		TextView text = (TextView)findViewById(R.id.p1p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p1f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p2);
		text = (TextView)findViewById(R.id.p2p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p2f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p3);
		text = (TextView)findViewById(R.id.p3p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p3f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p4);
		text = (TextView)findViewById(R.id.p4p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p4f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p5);
		text = (TextView)findViewById(R.id.p5p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p5f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p6);
		text = (TextView)findViewById(R.id.p6p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p6f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p7);
		text = (TextView)findViewById(R.id.p7p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p7f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p8);
		text = (TextView)findViewById(R.id.p8p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p8f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p9);
		text = (TextView)findViewById(R.id.p9p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p9f);
		text.setText(button.getText().toString());
		
		button = (Button)findViewById(R.id.p10);
		text = (TextView)findViewById(R.id.p10p);
		text.setText(button.getText().toString());
		
		text = (TextView)findViewById(R.id.p10f);
		text.setText(button.getText().toString());
	}
}