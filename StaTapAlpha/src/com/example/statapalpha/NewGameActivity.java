package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.statapalpha.R;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgame);
		
		populateListViews();
	}

	private void populateListViews() {
		// THIS HERE WILL POPULATE BOTH TEAM LIST VIEWS
    	//Create list of items
    	String[] team1Names = {"Team 1", "Team 2", "Team 3", "Team 4", "Team 5", "Team 6", "Team 7", "Team 8", "Team 9", 
    			"Team 10", "Team 11", "Team 12", "Team 13", "Team 14", "Team 15", "Team 16", "Team 17", "Team 18", "Team 19", "Team 20"};
    	String[] team2Names = {"Team 1", "Team 2", "Team 3", "Team 4", "Team 5", "Team 6", "Team 7", "Team 8", "Team 9", 
    			"Team 10", "Team 11", "Team 12", "Team 13", "Team 14", "Team 15", "Team 16", "Team 17", "Team 18", "Team 19", "Team 20"};    	
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			team1Names);				// Items to be displayed
    	ArrayAdapter<String> t2adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			team2Names);		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	t1list.setAdapter(t1adapter);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t2list.setAdapter(t2adapter);
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
