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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditTeam extends Activity {
	String teamname;
	SqliteHelper db;
	TextView tname;
	EditText editTextJersey;
	EditText editTextFirst;
	EditText editTextLast;
	String teamname2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_team);
		
    	
		// All this random crap here just to set the Team Name
		editTextJersey = (EditText)findViewById(R.id.editTextJersey);
		editTextFirst = (EditText)findViewById(R.id.editTextFirst);
		editTextLast = (EditText)findViewById(R.id.editTextLast);
		Intent mIntent = getIntent();
		teamname = mIntent.getStringExtra("TEAM_NAME");
		teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
		db = new SqliteHelper(this.getApplicationContext());
		tname = (TextView)findViewById(R.id.teamName);
		tname.setText(teamname);
		
		populateListViews();
	}
	/*
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        	super.onCreateContextMenu(menu, v, menuInfo);
        	if (v.getId()==R.id.homeTeamLV) {
        		MenuInflater inflater = getMenuInflater();
        		inflater.inflate(R.menu.home_context_menu, menu);
        	}
	}
	*/
	private void populateListViews() {
		
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
	
	public void addPlayer(View view) {
		Integer Jersey_Num;
		String First_Name = "";
		String Last_Name = "";
		if(editTextJersey.getText().toString().trim().length()==0) {
			String errormessage = "Error: Jersey Number cannot be blank";
			Toast.makeText(EditTeam.this, errormessage, Toast.LENGTH_SHORT).show();
		      return;
		}
		if(editTextFirst.getText().toString().trim().length()==0) {
			First_Name = editTextFirst.getText().toString();
		}
		if(editTextLast.getText().toString().trim().length()==0) {
			Last_Name = editTextLast.getText().toString();
		}
		Jersey_Num = Integer.parseInt(editTextJersey.getText().toString());
		db.addPlayer(teamname2, Jersey_Num, First_Name, Last_Name);
		populateListViews();
		
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
