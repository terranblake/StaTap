package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StatActivity extends Activity {
	SqliteHelper db;
	String team1, team2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		Intent mIntent = getIntent();
		team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		
		db = new SqliteHelper(this.getApplicationContext());
		
		
		pullStats();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void pullStats() {
		
		Cursor cursor1 = db.getJNums(team1);
		Cursor cursor2 = db.getJNums(team2);
		
			ArrayList<StatListData> values = new ArrayList<StatListData>();
			if (cursor1 != null && cursor1.getCount() != 0) {
			    cursor1.moveToFirst();
			    while (!cursor1.isAfterLast()) {
			    	StatListData data = new StatListData();
			    	int jnum = cursor1.getInt(0);
			    	data.Jersey = jnum;
			    	data.Assists = db.getStats("Assist", jnum);
			    	
			    	
			    	
			    	values.add(data);
			        cursor1.moveToNext();
			    }
			}
	}
}
