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
			if (i==1) twoPointMade++;
		}
		
		public void threePoint(int i) {
			if (i==0) threePointMiss++;
			if (i==1) threePointMade++;
		}
		
		public void freeThrow(int i) {
			if (i==0) freeThrowMiss++;
			if (i==1) freeThrowMade++;
		}
	}

	// Array that will hold all the players for both teams
	public player[] homePlayers = new player[25];
	public player[] awayPlayers = new player[25];
	
	int player = 0;
	
	public void setPlayer(View v) {
		Button b = (Button)v;
		player = Integer.parseInt(b.getText().toString());
		String message = "You Clicked Player #" + Integer.toString(player);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}
}
