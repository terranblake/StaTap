package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class CreateTeam extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		
		//Database Text Box Initialization
		EditText editTeam = (EditText)findViewById(R.id.editTeam);
		Button create = (Button)findViewById(R.id.create);
		
		SQLiteDatabase db = openOrCreateDatabase("StaTap", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS team(Team_Num INTEGER PRIMARY KEY AUTOINCREMENT,Team_Name TEXT);");
	}
	
	
	public void create(View view) {
		EditText editTeam = (EditText)findViewById(R.id.editTeam);
		SQLiteDatabase db = openOrCreateDatabase("StaTap", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS team(Team_Num INTEGER PRIMARY KEY AUTOINCREMENT,Team_Name TEXT);");
		if(editTeam.getText().toString().trim().length()==0) {
			String errormessage = "Error: Team name cannot be blank";
			Toast.makeText(CreateTeam.this, errormessage, Toast.LENGTH_SHORT).show();
		      return;
		}
		db.execSQL("INSERT INTO team(Team_Name) VALUES('"+editTeam.getText()+"');");
		String message = "Team " + editTeam.getText().toString() + " was added to the database";
		Toast.makeText(CreateTeam.this, message, Toast.LENGTH_SHORT).show();
	}
	
	
	/*
	 * 
	 * ListViews
	 * 
	 */
	private void populateListViews() {
		// THIS HERE WILL POPULATE BOTH TEAM LIST VIEWS
    	//Create list of items
    	String[] teamNames = {"Olathe South", "Olathe East", "Olathe North", "Olathe Northwest", "Shawnee Mission North", "Shawnee Mission South", "Shawnee Mission West", 
    			"Blue Valley High", "Blue Valley Northwest"};
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			teamNames);				// Items to be displayed		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	t1list.setAdapter(t1adapter);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_team, menu);
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
