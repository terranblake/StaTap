package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;

public class CourtActivity extends Activity {

	public class player {
		public String firstName = "";
		public String lastName = "";
		public int number = 0;
		public int points = 0;
		public int fouls = 0;
		public int rebounds = 0;
		public int assists = 0;
		public int blocks = 0;
		public int steals = 0;
		public int turnovers = 0;
		public int twoPointMade = 0;
		public int twoPointMiss = 0;
		public int threePointMade = 0;
		public int threePointMiss = 0;
		public int freeThrowMade = 0;
		public int freeThrowMiss = 0;
		
		public void rebound() {
			rebounds++;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}
}
