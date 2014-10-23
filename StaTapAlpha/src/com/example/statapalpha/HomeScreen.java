package com.example.statapalpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
        populateListView();
        registerClickCallback();
	}
    public void newGame(View view) {
    	Intent intent = new Intent(this, NewGameActivity.class);
    	startActivity(intent);
    }
    private void registerClickCallback() {
		// TODO Auto-generated method stub
    	ListView list = (ListView) findViewById(R.id.listViewMain);
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					TextView textView = (TextView) viewClicked;
					String message = "You Clicked # " + (position + 1) + ", which is string: " + textView.getText().toString();
					Toast.makeText(HomeScreen.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
    
	private void populateListView() {
    	//Create list of items
    	String[] gameNames = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5", "Game 6", "Game 7", "Game 8", "Game 9", 
    			"Game 10", "Game 11", "Game 12", "Game 13", "Game 14", "Game 15", "Game 16", "Game 17", "Game 18", "Game 19", "Game 20"};
    	
    	//Build Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.listviews,		// Layout to use
    			gameNames);				// Items to be displayed
    			
    	//Configure the List View
    	ListView list = (ListView) findViewById(R.id.listViewMain);
    	list.setAdapter(adapter);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
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
