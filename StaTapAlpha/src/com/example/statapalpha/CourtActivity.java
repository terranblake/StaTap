package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;

public class CourtActivity extends Activity {

	public class player {
		public int number = 0;
		public int points = 0;
		public int fouls = 0;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}
}
