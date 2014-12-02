package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
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
	
	String player = "0"; // Player number for current play
	String action = ""; // Action text for current play
	position position = new position(); // Position for current play
	int playNumber = 0;
	private PopupMenu popupMenu;
	boolean isHome = false;
	
	// Populates arrays with player numbers
	void getPlayers() {
		homePlayersBench.add("12");
		homePlayersBench.add("10");
		homePlayersBench.add("32");
		homePlayersBench.add("16");
		homePlayersBench.add("13");
		homePlayersBench.add("19");
		
		homePlayersIn.add("13");
		homePlayersIn.add("14");
		homePlayersIn.add("15");
		homePlayersIn.add("16");
		homePlayersIn.add("17");
		
		awayPlayersBench.add("21");
		awayPlayersBench.add("22");
		awayPlayersBench.add("23");
		awayPlayersBench.add("24");
		awayPlayersBench.add("25");
		awayPlayersBench.add("26");
		
		awayPlayersIn.add("31");
		awayPlayersIn.add("32");
		awayPlayersIn.add("33");
		awayPlayersIn.add("34");
		awayPlayersIn.add("35");
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
		case R.id.undoPlay: undoPlay();
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
		
		playNumber++;
		db.recordPlay(Integer.parseInt(player), action, position, playNumber);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
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
	

	void undoPlay() {
		db.undoPlay(Integer.toString(playNumber));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
		db = new SqliteHelper(this.getApplicationContext());
		
		// Gets players
		getPlayers();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Toast.makeText(CourtActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
		
		
		
		return false;
	}
	
	void refreshTeams() {
		
	}

	
}