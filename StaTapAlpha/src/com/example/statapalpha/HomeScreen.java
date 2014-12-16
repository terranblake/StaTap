package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	SqliteHelper db;
	ArrayList<HomeListData> values;
	ListView lv;
	Context context = HomeScreen.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        db = new SqliteHelper(this.getApplicationContext());
		//ListView Functions
		lv = (ListView) findViewById(R.id.homeTeamLV);
		getGames();
		lv.setAdapter(new HomeBaseAdapter(context, values));
        registerClickCallback();
        registerForContextMenu(lv);
	}
    public void newGame(View view) {
    	//This here starts the New Game Screen
    	Intent intent = new Intent(this, NewGameActivity.class);
    	startActivity(intent);
    }
    public void editTeams(View view) {
    	Intent intent = new Intent(this, CreateTeam.class);
    	startActivity(intent);
    }
    public void refresh(View view) {
    	getGames();
    	lv.setAdapter(new HomeBaseAdapter(context, values));
    }
    /**
     * MENU
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
          super.onCreateContextMenu(menu, v, menuInfo);
          if (v.getId()==R.id.homeTeamLV) {
              MenuInflater inflater = getMenuInflater();
              inflater.inflate(R.menu.home_context_menu, menu);
          }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
          AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
          switch(item.getItemId()) {
              case R.id.delete:
            // remove stuff here
            	  
                    return true;
              default:
                    return super.onContextItemSelected(item);
          }
    }
    
    public void openGame(View view, String game, String team1, String team2) {
    	Intent intent = new Intent(this, CourtActivity.class);
    	intent.putExtra("TEAM1", team1);
    	intent.putExtra("TEAM2", team2);
    	intent.putExtra("GAME_TITLE", game);
    	
    	startActivity(intent);
    }
    private void registerClickCallback() {
		// TODO Auto-generated method stub
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView list = (ListView) findViewById(R.id.homeTeamLV);
    	
    	//This Will check if there is a click on a ListView item
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					LinearLayout linearLayoutParent = (LinearLayout) viewClicked;
					String game, team1, team2;
					TextView tvGame = (TextView) linearLayoutParent.getChildAt(0);
					TextView tvT1 = (TextView) linearLayoutParent.getChildAt(1);
					TextView tvT2 = (TextView) linearLayoutParent.getChildAt(2);
					game = tvGame.getText().toString();
					team1 = tvT1.getText().toString();
					team2 = tvT2.getText().toString();
					openGame(null, game, team1, team2);
			}
		});
    	
    	
	}
   /* 
    
     	public void helppdf() {
    	Intent intent = new Intent(Intent.ACTION_VIEW,
    	        Uri.parse("H:\\GitHub\\StaTap\\StaTapAlpha\\"));
    	intent.setType("application/pdf");
    	PackageManager pm = getPackageManager();
    	List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
    	if (activities.size() > 0) {
    	    startActivity(intent);
    	} else {
    	    // Do something else here. Maybe pop up a Dialog or Toast
    	}
        
    }
    
    */
    public void getGames() {
    	Cursor cursor = db.getGameIDs();
    	values = new ArrayList<HomeListData>();
    	if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	HomeListData data = new HomeListData();
		    	int gid = cursor.getInt(0);
		    	data.gametitle = db.getGameTitle(gid);
		    	data.team1 = db.getGameT1(gid);
		    	data.team2 = db.getGameT2(gid);
		    	
		    	values.add(data);
		        cursor.moveToNext();
		    }
		}
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
