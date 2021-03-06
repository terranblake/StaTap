package com.statapalpha;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.statapalpha.R;

public class NewGameActivity extends Activity {
	EditText title;
	TextView team1, team2;
	SqliteHelper db;
	String teamn1, teamn2, gTitle, gTeam1, gTeam2, teamm1, teamm2, titlee;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgame);
		//ListViews
		team1 = (TextView) findViewById(R.id.textView4);
		team2 = (TextView) findViewById(R.id.textView5);
		title = (EditText) findViewById(R.id.editText1);
		db = new SqliteHelper(this.getApplicationContext());
		populateListViews();
		registerClickCallback();
	}

	/*
	 * 
	 * Database Functions
	 * 
	 */

	
	
	
	
	/*
	 * 
	 * ListView Stuff
	 * 
	 */
	private void populateListViews() {
		// THIS HERE WILL POPULATE BOTH TEAM LIST VIEWS
    	//Create list of items
		Cursor cursor = db.getTeams();
		
		ArrayList<String> values = new ArrayList<String>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {

		        values.add(cursor.getString(cursor.getColumnIndex("Team_Names")));

		        cursor.moveToNext();
		    }
		}    	
		cursor.close();
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			values);				// Items to be displayed
    	ArrayAdapter<String> t2adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			values);		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	t1list.setAdapter(t1adapter);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t2list.setAdapter(t2adapter);
	}
    private void registerClickCallback() {
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				//team 1
					TextView textView = (TextView) viewClicked;
					
					//Changing team name
			    	TextView t1 = (TextView) findViewById(R.id.textView4);
					teamn1 = textView.getText().toString();
					t1.setText(teamn1);
					//Toast message
			}
			
		});
    	t2list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				//team 2
					TextView textView = (TextView) viewClicked;
					//Changing Team name
					TextView t2 = (TextView) findViewById(R.id.textView5);
					teamn2 = textView.getText().toString();
					t2.setText(teamn2);
					//Toast message
			}
			
		});
	}
	public void onBackPressed() {
		//DO NOTHING
		Intent intent = new Intent(this, HomeScreen.class);
		finish();
		startActivity(intent);
	}
	@SuppressLint("DefaultLocale") 
	public void confirm(View view) {
		Spinner spinner = (Spinner) findViewById(R.id.spinnerT);
		Intent intent = new Intent(this, CourtActivity.class);
		gTitle = title.getText().toString();
		gTeam1 = team1.getText().toString();
		gTeam2 = team2.getText().toString();
		int timeG = (Integer.parseInt(spinner.getSelectedItem().toString())) * 60000;
		if (!gTitle.matches("[a-zA-Z0-9 ]*")) {
			Toast.makeText(NewGameActivity.this, "", Toast.LENGTH_SHORT).show();
	    	
		} else if (gTeam1.matches("Select a Team")) {
		
			Toast.makeText(NewGameActivity.this, "Error: Select a team for Team 1", Toast.LENGTH_SHORT).show();
		} else if (gTeam2.matches("Select a Team")) {
			Toast.makeText(NewGameActivity.this, "Error: Select a team for Team 2", Toast.LENGTH_SHORT).show();
		} else if (db.countPlayers(teamn1) < 5){
			Toast.makeText(NewGameActivity.this, "Error: Team 1 doesn't have 5 or more players", Toast.LENGTH_SHORT).show();
		} else if (db.countPlayers(teamn2) < 5){
			Toast.makeText(NewGameActivity.this, "Error: Team 2 doesn't have 5 or more players", Toast.LENGTH_SHORT).show();
		} else if(title.getText().toString().trim().length() == 0) {
			Toast.makeText(NewGameActivity.this, "Error: Please add a Game Title", Toast.LENGTH_SHORT).show();
		} else if (db.duplicateGame(gTitle) > 0){
			Toast.makeText(NewGameActivity.this, "Error: Another game with the same title already exists", Toast.LENGTH_SHORT).show();
		} else {
			teamm1 = teamn1.replaceAll(" ", "_").toLowerCase();
			teamm2 = teamn2.replaceAll(" ", "_").toLowerCase();
			titlee = gTitle.replaceAll(" ", "_").toLowerCase();
			intent.putExtra("TEAM1", teamm1);
	    	intent.putExtra("TEAM2", teamm2);
	    	intent.putExtra("GAME_TITLE", titlee);
	    	db.createGame(gTitle, teamn1, teamn2, timeG);
	    	finish();
	    	startActivity(intent);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
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
