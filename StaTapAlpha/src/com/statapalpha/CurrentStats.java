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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	static final String ASSIST = "Assisted Shot";
	static final String FOUL = "Committed Foul";
	static final String TURNOVER = "Turned over ball";
	ListView lv;
	ArrayList<CurrentStatsListData> values;
	LayoutInflater inflater;
	String team1, team2, tablename;
	Context context = CurrentStats.this;
	SqliteHelper db;
	Cursor cursor, cursor2, cursor3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stats);
        lv = (ListView)findViewById(R.id.lvStatList);
        db = new SqliteHelper(this.getApplicationContext());
        Intent mIntent = getIntent();
        team1 = mIntent.getStringExtra("TEAM1");
		team2 = mIntent.getStringExtra("TEAM2");
		tablename = mIntent.getStringExtra("TABLENAME");
		setLongClick();
		Toast.makeText(CurrentStats.this, tablename, Toast.LENGTH_SHORT).show();
		registerForContextMenu(lv);
		getPlays();
		lv.setAdapter(new CurrentStatsBaseAdapter(context, values));
	}
	public void getPlays() {
		cursor = db.grabPlays(tablename);
		values = new ArrayList<CurrentStatsListData>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	CurrentStatsListData data = new CurrentStatsListData();
		    	String playy = "";
		    	data.pNum = cursor.getString(0);
		    	data.jNum = cursor.getString(1);
		    	switch (cursor.getString(2)) {
		    	case "F3H": playy = MADE3PT;break;
		    	case "F3M": playy = MISS3PT;break;
		    	case "F2H": playy = MADE2PT;break;
		    	case "F2M": playy = MISS2PT;break;
		    	case "FTH": playy = MADEFT;break;
		    	case "FTM": playy = MISSFT;break;
		    	case "FC": playy = FOUL;break;
		    	case "RB": playy = REBOUND;break;
		    	case "TO": playy = TURNOVER;break;
		    	case "BL": playy = BLOCK;break;
		    	case "AST": playy = ASSIST;break;
		    	case "STL": playy = STEAL;break;
		    	}
		    	data.Play = playy;
		    	if ((cursor.getString(3)).equals(team2)) {
		    		data.isHome = false;
		    	} else {
		    		data.isHome = true;
		    	}
		    	db = new SqliteHelper(this.getApplicationContext());
		    	values.add(data);
		        cursor.moveToNext();
		    }
		}
    	cursor.close();
	}
	public void setLongClick() {
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
                    int pos, long id) {
            	RelativeLayout relativeLayoutParent = (RelativeLayout) viewClicked;
            	TextView tvPlayNum = (TextView) relativeLayoutParent.getChildAt(0);
            	String playNum = tvPlayNum.getText().toString();
                db.delPlay(playNum, tablename);
                getPlays();
                lv.setAdapter(new CurrentStatsBaseAdapter(context, values));
                return true;
            }
        });
	}
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
