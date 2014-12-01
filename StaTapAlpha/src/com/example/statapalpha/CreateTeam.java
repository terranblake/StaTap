package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;

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
		//Button create = (Button)findViewById(R.id.create);

		//ListView lv = (ListView)findViewById(R.id.listView1);

		populateListViews();
		registerClickCallback();
		//DB
	}
	
	public void create(View view) {
		
		
		if(editTeam.getText().toString().trim().length()==0) {
			String errormessage = "Error: Team name cannot be blank";
			Toast.makeText(CreateTeam.this, errormessage, Toast.LENGTH_SHORT).show();
		      return;
		}
		
		String Team_Name = editTeam.getText().toString();
		db.addTeam(Team_Name);
		String message = "Team " + editTeam.getText().toString() + " was added to the database";
		Toast.makeText(CreateTeam.this, message, Toast.LENGTH_SHORT).show();
		populateListViews();
	}
	
	
	/*
	 * 
	 * ListViews
	 * 
	 */
    private void registerClickCallback() {
		// TODO Auto-generated method stub
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView list = (ListView) findViewById(R.id.listView1);
    	
    	//This Will check if there is a click on a ListView item
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					
					TextView textView = (TextView) viewClicked; 
					//Toast message
					String teamname = textView.getText().toString();
					editTeam(teamname, null);
			}
		});
   	}
    public void editTeam(String teamname, View view) {
    	Intent intent = new Intent(this, EditTeam.class);
    	intent.putExtra("TEAM_NAME", teamname);
    	startActivity(intent);
    }
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
    			R.layout.teamlistviews,	// Layout to use
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
