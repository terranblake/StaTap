package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;
import com.example.statapalpha.R.id;
import com.example.statapalpha.R.layout;
import com.example.statapalpha.R.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class CurrentStats extends Activity {
	public View.OnTouchListener gestureListener;
	static final String MADE3PT = "MADE 3PT Shot";
	static final String MADE2PT = "MADE 2PT Shot";
	static final String MADEFT = "MADE Free Throw";
	static final String MISS3PT = "MISSED 3PT Shot";
	static final String MISS2PT = "MISSED 2PT Shot";
	static final String MISSFT = "MISSED Free Throw";
	static final String STEAL = "Stole Ball";
	static final String REBOUND = "Rebounded Ball";
	static final String BLOCK = "Blocked Shot";
	static final String ASSIST = "Assist Made";
	static final String FOUL = "Committed Foul";
	static final String TURNOVER = "Turned over ball";
	ArrayList<CurrentStatsListData> values;
	LayoutInflater inflater;
	String team1, team2, tablename;
	Context context = CurrentStats.this;
	SqliteHelper db;
	Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stats);
        ListView lv = (ListView)findViewById(R.id.lvStatList);
        Intent mIntent = getIntent();
        team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		tablename = mIntent.getStringExtra("TABLENAME");
		registerForContextMenu(lv);
		lv.setAdapter(new CurrentStatsBaseAdapter(context, values));
	}
	public void getPlays() {
		cursor = db.grabPlays(tablename);
		values = new ArrayList<CurrentStatsListData>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	CurrentStatsListData data = new CurrentStatsListData();
		    	int jnum = cursor.getInt(0);
		    	String jnums = Integer.toString(jnum);
		    	data.pNum = jnums;
		    	db = new SqliteHelper(this.getApplicationContext());
		    	//data.jNum = db.getPlayerFName(jnum, tableteamname);
		    	//data.Play = db.getPlayerLName(jnum, tableteamname);
		    	
		    	values.add(data);
		        cursor.moveToNext();
		    }
		}

    	cursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_stats, menu);
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
}
