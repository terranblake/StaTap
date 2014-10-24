package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.statapalpha.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgame);
		
		populateListViews();
		registerClickCallback();
	}

	private void populateListViews() {
		// THIS HERE WILL POPULATE BOTH TEAM LIST VIEWS
    	//Create list of items
    	String[] team1Names = {"Olathe South", "Olathe East", "Olathe North", "Olathe Northwest", "Shawnee Mission North", "Shawnee Mission South", "Shawnee Mission West", 
    			"Blue Valley High", "Blue Valley Northwest"};
    	String[] team2Names = {"Olathe South", "Olathe East", "Olathe North", "Olathe Northwest", "Shawnee Mission North", "Shawnee Mission South", "Shawnee Mission West", 
    			"Blue Valley High", "Blue Valley Northwest"};    	
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
    private void registerClickCallback() {
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					TextView textView = (TextView) viewClicked;
					//Toast message
					String message = "You Selected " + textView.getText().toString() + " for Team 1";
					Toast.makeText(NewGameActivity.this, message, Toast.LENGTH_SHORT).show();
			}
			
		});
    	t2list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					TextView textView = (TextView) viewClicked;
					//Toast message
					String message = "You Selected " + textView.getText().toString() + " for Team 2";
					Toast.makeText(NewGameActivity.this, message, Toast.LENGTH_SHORT).show();
			}
			
		});
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
