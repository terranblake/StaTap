package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CourtActivity extends Activity {

	// Player class to declare all players as and store their info
	public class player {
		private String firstName = "";
		private String lastName = "";
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
	}

	// Array that will hold all the players for both teams
	public player[] homePlayers = new player[25];
	public player[] awayPlayers = new player[25];
	
	int player = 0;
	String action = "";
	
	
	public void setPlayer(View v) {
		Button b = (Button)v;
		player = Integer.parseInt(b.getText().toString());
		String message = "You Clicked Player #" + Integer.toString(player);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	
	public void setAction(View v) {
		Button b = (Button)v;
		
		switch(b.getId()) {
		case 2131296260: action = "Made Goal";
		case 2131296261: action = "Missed Goal";
		case 2131296259: action = "Made Freethrow";
		case 2131296258: action = "Missed Freethrow";
		case 2131296270: action = "Rebound";
		case 2131296269: action = "Assist";
		case 2131296268: action = "Block";
		case 2131296266: action = "Steal";
		case 2131296267: action = "Turnover";
		case 2131296281: action = "Substitution";
		case 2131296265: action = "Foul";
		}
			
		String message = "Action: " + b.getId();
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}
}
