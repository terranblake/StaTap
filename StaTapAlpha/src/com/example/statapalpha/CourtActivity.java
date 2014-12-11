package com.example.statapalpha;

import android.app.Activity;
import android.content.Intent;
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

	public void convertStrings() {
		Intent mIntent = getIntent();
		team1n = mIntent.getStringExtra("TEAM1");
		team2n = mIntent.getStringExtra("TEAM2");
	}
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
		
		playNumber++;
		db.recordPlay(Integer.parseInt(player), team, action, position, playNumber);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
		refreshPlayers();
	}
	
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