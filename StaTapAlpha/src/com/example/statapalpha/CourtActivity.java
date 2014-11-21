package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
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
	private PopupMenu popupMenu;
	
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
	}
	
	// Sets string Action to whatever action the user taps
	// then records play to database.
	public void setAction(View v) {
		Button b = (Button)v;
		String toastAction = "";
		
		switch(b.getId()) {
		case R.id.fgMade: action = "F" + goal(position) + "H"; toastAction = "made " + goal(position) + " point shot";
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
		
		if (action == "SUB") {
			popupMenu = new PopupMenu(this.getBaseContext(), v);
			
			popupMenu.show();
		}
		
		playNumber++;
		db.recordPlay(player, action, position, playNumber);
		Toast.makeText(CourtActivity.this, "Player " + Integer.toString(player) + " " + toastAction + " at ("  + Integer.toString(position.x) + ", " + Integer.toString(position.y) + ")", Toast.LENGTH_SHORT).show();
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
		db = new SqliteHelper(this.getApplicationContext());
		
		// Gets players
		/*
		getPlayers();
		
		for(int i=1;i<25;i++) {
			popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, Integer.toString(homePlayer[i]));
		}
		*/
	}
}