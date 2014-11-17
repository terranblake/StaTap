package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.lang.Math;

// Court Screen
public class CourtActivity extends Activity {

	// Opens database
	SqliteHelper db;
	
	int homePlayer[] = new int[25], awayPlayer[] = new int[25];
	
	int player = 0; // Player number for current play
	String action = ""; // Action text for current play
	position position = new position(); // Position for current play
	int playNumber = 0;
	
	// Populates arrays with player numbers
	void getPlayers() {
		
	}
	
	// Stores x and y coordinate
	public class position {
		public int x;
		public int y;
	}
	
	// Sets current player when a player button is clicked
	public void setPlayer(View v) {
		Button b = (Button)v;
		player = Integer.parseInt(b.getText().toString());
		String message = "Player: " + Integer.toString(player);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	
	// Sets string Action to whatever action the user taps
	// then records play to database.
	public void setAction(View v) {
		Button b = (Button)v;
		
		switch(b.getId()) {
		case R.id.fgMade: action = "F" + goal(position) + "H";
		break;
		case R.id.fgMissed: action = "F" + goal(position) + "M";
		break;
		case R.id.ftMade: action = "FTH";
		break;
		case R.id.ftMissed: action = "FTM";
		break;
		case R.id.rebound: action = "RB";
		break;
		case R.id.assist: action = "AST";
		break;
		case R.id.block: action = "BL";
		break;
		case R.id.steal: action = "STL";
		break;
		case R.id.turnover: action = "TO";
		break;
		case R.id.sub: action = "SUB";
		break;
		case R.id.foul: action = "FC";
		break;
		}
			
		String message = "Action: " + action;
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		
		
		playNumber++;
		db.recordPlay(player, action, position, playNumber);
		Toast.makeText(CourtActivity.this, "Recorded to database", Toast.LENGTH_SHORT).show();
	}
	
	// Gets tap position and saves it to 'position'
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			position.x = (int)event.getX(0);
			position.y = (int)event.getY(0);
		    String message = "At: " + position.x + ", " + position.y;
			Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		}
	    
	    return super.onTouchEvent(event);
	}
	
	int goal(position position) {
		int points=3;
		
		if ((Math.sqrt(Math.pow((position.x - 1085), 2) + Math.pow((position.y - 455), 2)) < 273) || (Math.sqrt(Math.pow((position.x - 194), 2) + Math.pow((position.y - 455), 2)) < 273)) points = 2;
		
		return points;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
		db = new SqliteHelper(this.getApplicationContext());
	}

}
