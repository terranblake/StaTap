package com.example.statapalpha;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class StatActivity extends Activity {
	SqliteHelper db;
	String team1, team2;
	ListView lv1, lv2;
	ArrayList<StatListData> values = new ArrayList<StatListData>();
	ArrayList<StatListData> values2 = new ArrayList<StatListData>();
	
	Context context = StatActivity.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		Intent mIntent = getIntent();
		team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		db = new SqliteHelper(this.getApplicationContext());
		lv1 = (ListView) findViewById(R.id.listView1);
		lv2 = (ListView) findViewById(R.id.ListView01);
		
		
		pullStats();
		lv1.setAdapter(new StatBaseAdapter(context, values));
		lv2.setAdapter(new StatBaseAdapter(context, values2));
		
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
		
			values = new ArrayList<StatListData>();
			values2 = new ArrayList<StatListData>();
			if (cursor1 != null && cursor1.getCount() != 0) {
			    cursor1.moveToFirst();
			    while (!cursor1.isAfterLast()) {
			    	StatListData data = new StatListData();
			    	int jnum = cursor1.getInt(0);
			    	data.Jersey = jnum;
			    	data.Points = db.getPoints(jnum, team1);
			    	data.FT = db.getStats("FTM", jnum, team1);
			    	data.Assists = db.getStats("AST", jnum, team1);
			    	data.Rebounds = db.getStats("RB", jnum, team1);
			    	data.Steals = db.getStats("STL", jnum, team1);
			    	data.TO = db.getStats("TO", jnum, team1);
			    	data.Fouls = db.getStats("FC", jnum, team1);
			    	
			    	
			    	values.add(data);
			        cursor1.moveToNext();
			    }
			}
			if (cursor2 != null && cursor2.getCount() != 0) {
			    cursor2.moveToFirst();
			    while (!cursor2.isAfterLast()) {
			    	StatListData data = new StatListData();
			    	int jnum = cursor2.getInt(0);
			    	data.Jersey = jnum;
			    	data.Points = db.getPoints(jnum, team2);
			    	data.FT = db.getStats("FTM", jnum, team2);
			    	data.Assists = db.getStats("AST", jnum, team2);
			    	data.Rebounds = db.getStats("RB", jnum, team2);
			    	data.Steals = db.getStats("STL", jnum, team2);
			    	data.TO = db.getStats("TO", jnum, team2);
			    	data.Fouls = db.getStats("FC", jnum, team2);
			    	
			    	
			    	values2.add(data);
			        cursor2.moveToNext();
			    }
			}
	}
}
