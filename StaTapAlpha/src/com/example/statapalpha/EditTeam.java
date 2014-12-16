package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
	ListView lv;
	String tableteamname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_team);
		
		lv = (ListView) findViewById(R.id.listViewPlayers);
		// All this random crap here just to set the Team Name
		editTextJersey = (EditText)findViewById(R.id.editTextJersey);
		editTextFirst = (EditText)findViewById(R.id.editTextFirst);
		editTextLast = (EditText)findViewById(R.id.editTextLast);
		Intent mIntent = getIntent();
		teamname = mIntent.getStringExtra("TEAM_NAME");
		tableteamname = teamname.replaceAll(" ", "_").toLowerCase();
		db = new SqliteHelper(this.getApplicationContext());
		tname = (TextView)findViewById(R.id.teamName);
		tname.setText(teamname);
		registerForContextMenu(lv);
		registerClickCallback();
		populateListViews();
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        	super.onCreateContextMenu(menu, v, menuInfo);
        	if (v.getId()==R.id.listViewPlayers) {
        		MenuInflater inflater = getMenuInflater();
        		inflater.inflate(R.menu.home_context_menu, menu);
        	}
	}
	
	private void populateListViews() {
		
		Cursor cursor = db.getPlayerJNums(tableteamname);
		
		ArrayList<String> jnums = new ArrayList<String>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {

		        jnums.add(cursor.getString(cursor.getColumnIndex("jersey_num")));

		        cursor.moveToNext();
		    }
		}
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.jerseylistview,	// Layout to use
    			jnums);				// Items to be displayed		
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
		if(!(editTextFirst.getText().toString().trim().length()==0)) {
			First_Name = editTextFirst.getText().toString();
		}
		if(!(editTextLast.getText().toString().trim().length()==0)) {
			Last_Name = editTextLast.getText().toString();
		}
		if (First_Name.matches("[a-zA-Z]*") && Last_Name.matches("[a-zA-Z]*")) {
			Jersey_Num = Integer.parseInt(editTextJersey.getText().toString());
			editTextJersey.setText("");
			editTextFirst.setText("");
			editTextLast.setText("");
			db.addPlayer(tableteamname, Jersey_Num, First_Name, Last_Name);
			populateListViews();
		} else {
			Toast.makeText(EditTeam.this, "Names must be BLANK or contain [a-Z]", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_team, menu);
		return true;
	}
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
          // remove stuff here
            Integer Position = ((AdapterView.AdapterContextMenuInfo)info).position;
            Object obj = lv.getItemAtPosition(Position);
            String teamname2 = obj.toString();
            int jnum = Integer.parseInt(teamname2);
          	db.delPlayer(jnum, tableteamname);
          	populateListViews();
            return true;
            default:
                  return super.onContextItemSelected(item);
        }
  }
    private void registerClickCallback() {
		// TODO Auto-generated method stub
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView list = (ListView) findViewById(R.id.listViewPlayers);
    	
    	//This Will check if there is a click on a ListView item
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					TextView textView = (TextView) viewClicked; 
					Integer Jersey_Num = Integer.parseInt(textView.getText().toString());
					String fname = "N/A";
					String lname = "N/A";
					
					Cursor cursorF;
					Cursor cursorL;
					//Toast message
					fname = db.getPlayerFName(Jersey_Num, tableteamname);
					lname = db.getPlayerLName(Jersey_Num, tableteamname);
					String message = "Jersey Number:" + Jersey_Num + " First Name:" + fname + " Last Name:" + lname;
					Toast.makeText(EditTeam.this, message, Toast.LENGTH_SHORT).show();
			}
		});
    	
    	
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
