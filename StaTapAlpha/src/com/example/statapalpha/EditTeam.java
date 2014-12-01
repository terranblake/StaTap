package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EditTeam extends Activity {
	String teamname;
	SqliteHelper db;
	TextView tname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_team);
		// All this random crap here just to set the Team Name
		Intent mIntent = getIntent();
		teamname = mIntent.getStringExtra("TEAM_NAME");
		db = new SqliteHelper(this.getApplicationContext());
		tname = (TextView)findViewById(R.id.teamName);
		tname.setText(teamname);
		
		populateListViews();
	}

	private void populateListViews() {
		
		String teamname2;
    	teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
		Cursor cursor = db.getPlayerFNames(teamname2);
		
		ArrayList<String> values = new ArrayList<String>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {

		        values.add(cursor.getString(cursor.getColumnIndex("first_name")));

		        cursor.moveToNext();
		    }
		}
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,	// Layout to use
    			values);				// Items to be displayed		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listViewPlayers);
    	t1list.setAdapter(t1adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_team, menu);
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
