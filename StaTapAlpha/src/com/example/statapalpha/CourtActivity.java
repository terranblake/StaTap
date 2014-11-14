package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.database.sqlite.*;
import java.lang.Math;

// Court Screen
public class CourtActivity extends Activity {

	// Opens database
	SQLiteDatabase db; 
	
	int homePlayer[] = new int[25], awayPlayer[] = new int[25];
	
	int player = 0; // Player number for current play
	String action = ""; // Action text for current play
	position position = new position(); // Position for current play
	
	int playNumber = 0; // Number to use for play ID
	
	/*
	
	// Arrays that will hold all the players for both teams
	public player[] homePlayers = new player[25];
	public player[] awayPlayers = new player[25];
	
	// Player class to declare all players as and store their info
	// firstName, lastName, number, points, fouls, rebounds, assists,
	// blocks, steals, turnovers, twoPointMade, twoPointMiss, 
	// threePointMade, threePointMiss, freeThrowMade, freeThrowMiss
	public class player {
		private String firstName = new String();
		private String lastName = new String();
		private int number = 0;
		private int points = 0;
		private int fouls = 0;
		private int rebounds = 0;
		private int assists = 0;
		private int blocks = 0;
		private int steals = 0;
		private int turnovers = 0;
		private int twoPointMade = 0;
		private int twoPointMiss = 0;
		private int threePointMade = 0;
		private int threePointMiss = 0;
		private int freeThrowMade = 0;
		private int freeThrowMiss = 0;
		
		public void setFirstName(String first) {
			firstName = first;
		}
		
		public void setLastName(String last) {
			lastName = last;
		}
		
		public void setNumber(int num) {
			number = num;
		}
		
		public void rebound() {
			rebounds++;
		}
		
		public void assist() {
			assists++;
		}
		
		public void block() {
			blocks++;
		}

		
		public void steal() {
			steals++;
		}
		
		public void turnover() {
			turnovers++;
		}
		
		public void foul() {
			fouls++;
		}
		
		public void twoPoint(int i) {
			if (i==0) twoPointMiss++;
			if (i==1) {
				twoPointMade++;
				points += 2;
			}
		}
		
		public void threePoint(int i) {
			if (i==0) threePointMiss++;
			if (i==1) {
				threePointMade++;
				points += 3;
			}
		}
		
		public void freeThrow(int i) {
			if (i==0) freeThrowMiss++;
			if (i==1) {
				freeThrowMade++;
				points++;
			}
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public int getNumber() {
			return number;
		}
		
		public int getPoints() {
			return points;
		}
		
		public int getFouls() {
			return fouls;
		}
		
		public int getRebounds() {
			return rebounds;
		}
		
		public int getAssists() {
			return assists;
		}
		
		public int getBlocks() {
			return blocks;
		}
		
		public int getSteals() {
			return steals;
		}
		
		public int getTurnovers() {
			return turnovers;
		}
		
		public int getTwoPointMade() {
			return twoPointMade;
		}
		
		public int getTwoPointMiss() {
			return twoPointMiss;
		}
		
		public int getThreePointMade() {
			return threePointMade;
		}
		
		public int getThreePointMiss() {
			return threePointMiss;
		}
		
		public int getFreeThrowMade() {
			return freeThrowMade;
		}
		
		public int getFreeThrowMiss() {
			return freeThrowMiss;
		}
	}
	
	*/

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
		
		//recordPlay(player, action, position);
	}
	
	// Gets tap position and saves it to 'position'
	// then records play to database
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			position.x = (int)event.getX(0);
			position.y = (int)event.getY(0);	
		}

	    String message = "At: " + position.x + ", " + position.y;
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	    
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
		db = openOrCreateDatabase("StaTap", Context.MODE_PRIVATE, null);
	}

}
