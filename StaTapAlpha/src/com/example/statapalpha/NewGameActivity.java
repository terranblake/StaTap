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
		
		populateListView();
	}

	private void populateListView() {
		// TODO Auto-generated method stub
    	//Create list of items
    	String[] team1Names = {"Team 1", "Team 2", "Team 3", "Team 4", "Team 5", "Team 6", "Team 7", "Team 8", "Team 9", 
    			"Team 10", "Team 11", "Team 12", "Team 13", "Team 14", "Team 15", "Team 16", "Team 17", "Team 18", "Team 19", "Team 20"};
    	
    	//Build Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			team1Names);				// Items to be displayed
    			
    	//Configure the List View
    	ListView list = (ListView) findViewById(R.id.listView1);
    	list.setAdapter(adapter);		
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
