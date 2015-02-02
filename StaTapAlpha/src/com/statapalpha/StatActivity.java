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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class StatActivity extends Activity {
	SqliteHelper db;
	String team1, team2, tablename;
	ListView lv1, lv2;
	ArrayList<StatListData> values;
	ArrayList<StatListData> values2;
	TextView t1points, t2points;
	int t1p, t2p;
	
	Context context = StatActivity.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		Intent mIntent = getIntent();
		team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		tablename = mIntent.getStringExtra("TABLENAME");
		t1p = 0;
		t2p = 0;
		t1points = (TextView) findViewById(R.id.textT1);
		t2points = (TextView) findViewById(R.id.textT2);
		db = new SqliteHelper(this.getApplicationContext());
		lv1 = (ListView) findViewById(R.id.listView1);
		lv2 = (ListView) findViewById(R.id.ListView01);
		
		
		pullStats();
		lv1.setAdapter(new StatBaseAdapter(context, values));
		lv2.setAdapter(new StatBaseAdapter(context, values2));
		setPoints();
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, CourtActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	intent.putExtra("GAME_TITLE", tablename);
    	finish();
    	startActivity(intent);
	}
	public void back(View view) {
		Intent intent = new Intent(this, CourtActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	intent.putExtra("GAME_TITLE", tablename);
    	finish();
    	startActivity(intent);
	}
	public void setPoints() {
		String p1t, p2t;
		p1t = Integer.toString(t1p);
		p2t = Integer.toString(t2p);
		t1points.setText(p1t);
		t2points.setText(p2t);
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
			    	data.Points = db.getPoints(jnum, team1, tablename);
			    	t1p = t1p+(db.getPoints(jnum, team1, tablename));
			    	data.FTA = db.getFTA(jnum, team1, tablename);
			    	data.FTM = db.getStats("FTH", jnum, team1, tablename);
			    	data.FGA = db.getFGA(jnum, team1, tablename);
			    	data.FGM = db.getStats("F2H", jnum, team1, tablename);
			    	data.TPA = db.getTPA(jnum, team1, tablename);
			    	data.TPM = db.getStats("F3H", jnum, team1, tablename);
			    	data.Assists = db.getStats("AST", jnum, team1, tablename);
			    	data.Rebounds = db.getStats("RB", jnum, team1, tablename);
			    	data.Blocks = db.getStats("BL", jnum, team1, tablename);
			    	data.Steals = db.getStats("STL", jnum, team1, tablename);
			    	data.TO = db.getStats("TO", jnum, team1, tablename);
			    	data.Fouls = db.getStats("FC", jnum, team1, tablename);
			    	
			    	
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
			    	data.Points = db.getPoints(jnum, team2, tablename);
			    	t2p = t2p+(db.getPoints(jnum, team2, tablename));
			    	data.FTA = db.getFTA(jnum, team2, tablename);
			    	data.FTM = db.getStats("FTH", jnum, team2, tablename);
			    	data.FGA = db.getFGA(jnum, team2, tablename);
			    	data.FGM = db.getStats("F2H", jnum, team2, tablename);
			    	data.TPA = db.getTPA(jnum, team2, tablename);
			    	data.TPM = db.getStats("F3H", jnum, team2, tablename);
			    	data.Assists = db.getStats("AST", jnum, team2, tablename);
			    	data.Rebounds = db.getStats("RB", jnum, team2, tablename);
			    	data.Blocks = db.getStats("BL", jnum, team2, tablename);
			    	data.Steals = db.getStats("STL", jnum, team2, tablename);
			    	data.TO = db.getStats("TO", jnum, team2, tablename);
			    	data.Fouls = db.getStats("FC", jnum, team2, tablename);
			    	
			    	
			    	values2.add(data);
			        cursor2.moveToNext();
			    }
			}
		cursor1.close();
		cursor2.close();
	}
}
