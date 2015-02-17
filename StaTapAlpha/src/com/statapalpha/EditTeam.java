package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	ArrayList<EditListData> values;
	Cursor cursor;
	Context context = EditTeam.this;
	@SuppressLint("DefaultLocale") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_team);
		
		lv = (ListView) findViewById(R.id.listViewPlayers);
		// All this random crap here just to set the Team Name
		editTextJersey = (EditText)findViewById(R.id.editTextJersey);
		editTextFirst = (EditText)findViewById(R.id.editTextFirst);
		editTextLast = (EditText)findViewById(R.id.editTextLast);
		editTextJersey.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	addPlayer(v);
		          return true;
		        }
		        return false;
		    }
		});
		Intent mIntent = getIntent();
		teamname = mIntent.getStringExtra("TEAM_NAME");
		tableteamname = teamname.replaceAll(" ", "_").toLowerCase();
		db = new SqliteHelper(this.getApplicationContext());
		tname = (TextView)findViewById(R.id.teamName);
		tname.setText(teamname);
		registerForContextMenu(lv);
		registerClickCallback();
		getPlayers();
		lv.setAdapter(new EditBaseAdapter(context, values));
		
	}
	public void getPlayers() {
		cursor = db.getJNums(tableteamname);
		values = new ArrayList<EditListData>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	EditListData data = new EditListData();
		    	int jnum = cursor.getInt(0);
		    	String jnums = Integer.toString(jnum);
		    	data.jnum = jnums;
		    	data.FName = db.getPlayerFName(jnum, tableteamname);
		    	data.LName = db.getPlayerLName(jnum, tableteamname);
		    	
		    	values.add(data);
		        cursor.moveToNext();
		    }
		}

    	cursor.close();
	}
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        	super.onCreateContextMenu(menu, v, menuInfo);
        	if (v.getId()==R.id.listViewPlayers) {
        		MenuInflater inflater = getMenuInflater();
        		inflater.inflate(R.menu.home_context_menu, menu);
        	}
	}
	

	
	public void addPlayer(View view) {
		Integer Jersey_Num;
		String First_Name = "";
		String Last_Name = "";
		if(db.countPlayers(tableteamname) > 24) {
			
		}
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
		if (First_Name.matches("[a-zA-Z-]*") && Last_Name.matches("[a-zA-Z-]*")) {
			Jersey_Num = Integer.parseInt(editTextJersey.getText().toString());
			editTextJersey.setText("");
			editTextFirst.setText("");
			editTextLast.setText("");
			db.addPlayer(tableteamname, Jersey_Num, First_Name, Last_Name);
			getPlayers();
			lv.setAdapter(new EditBaseAdapter(context, values));
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
            View view = ((AdapterView.AdapterContextMenuInfo)info).targetView;
            LinearLayout linearLayoutParent = (LinearLayout) view;
            TextView tv = (TextView) linearLayoutParent.getChildAt(0);
            String teamname2 = tv.getText().toString();
            int jnum = Integer.parseInt(teamname2);
          	db.delPlayer(jnum, tableteamname);
          	getPlayers();
          	lv.setAdapter(new EditBaseAdapter(context, values));
            return true;
            default:
                  return super.onContextItemSelected(item);
        }
  }
    private void registerClickCallback() {
    	ListView list = (ListView) findViewById(R.id.listViewPlayers);
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					LinearLayout linearLayoutParent = (LinearLayout) viewClicked;
					TextView textView = (TextView) linearLayoutParent.getChildAt(0);
					TextView fname = (TextView) linearLayoutParent.getChildAt(1);
					TextView lname = (TextView) linearLayoutParent.getChildAt(2);
					Integer Jersey_Num = Integer.parseInt(textView.getText().toString());
					String fnamef = "N/A";
					String lnamel = "N/A";
					
					//Toast message
					fnamef = fname.getText().toString();
					lnamel = lname.getText().toString();
					String message = "Jersey Number:" + Jersey_Num + " First Name:" + fnamef + " Last Name:" + lnamel;
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
