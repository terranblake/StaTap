package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
	EditText editTeam;
	SqliteHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		//Test
		editTeam = (EditText)findViewById(R.id.editTeam);
		//End
		//Database Text Box Initialization
		db = new SqliteHelper(this.getApplicationContext());
		Button create = (Button)findViewById(R.id.create);
		//ListViews
		ListView lv = (ListView)findViewById(R.id.listView1);

		populateListViews();
		//DB
	}
	
	public void create(View view) {
		

		if(editTeam.getText().toString().trim().length()==0) {
			String errormessage = "Error: Team name cannot be blank";
			Toast.makeText(CreateTeam.this, errormessage, Toast.LENGTH_SHORT).show();
		      return;
		}
		//db.execSQL("INSERT INTO teams(Team_Names) VALUES('"+editTeam.getText()+"');");
		String message = "Team " + editTeam.getText().toString() + " was added to the database";
		Toast.makeText(CreateTeam.this, message, Toast.LENGTH_SHORT).show();
		populateListViews();
	}
	
	
	/*
	 * 
	 * ListViews
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
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			values);				// Items to be displayed		
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
