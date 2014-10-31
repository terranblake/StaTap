package com.example.statapalpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ListView lv = (ListView) findViewById(R.id.listViewMain);
        populateListView();
        registerClickCallback();
        registerForContextMenu(lv);
	}
    public void newGame(View view) {
    	//This here starts the New Game Screen
    	Intent intent = new Intent(this, NewGameActivity.class);
    	startActivity(intent);
    }
    public void courtActivity(View view) {
    	//This here starts the Court Activity Screen
    	Intent intent = new Intent(this, CourtActivity.class);
    	startActivity(intent);
    }
    /**
     * MENU
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
          super.onCreateContextMenu(menu, v, menuInfo);
          if (v.getId()==R.id.listViewMain) {
              MenuInflater inflater = getMenuInflater();
              inflater.inflate(R.menu.home_context_menu, menu);
          }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
          AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
          switch(item.getItemId()) {
              case R.id.edit:
                // edit stuff here
                    return true;
              case R.id.delete:
            // remove stuff here
                    return true;
              default:
                    return super.onContextItemSelected(item);
          }
    }
    private void registerClickCallback() {
		// TODO Auto-generated method stub
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView list = (ListView) findViewById(R.id.listViewMain);
    	
    	//This Will check if there is a click on a ListView item
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					TextView textView = (TextView) viewClicked;
					//Toast message
					String message = "You Clicked # " + (position + 1) + ", which is string: " + textView.getText().toString();
					Toast.makeText(HomeScreen.this, message, Toast.LENGTH_SHORT).show();
			}
		});
    	
    	//This will check if there is a LONG click on a ListView item
    	/*list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int position, long id) {
            	TextView textView = (TextView) arg1;
            	//Dummy log message and toast message
            	
                Log.v("long clicked","pos: " + position);
                String message = "You long clicked the " + textView.getText().toString() + " button";
                //Toast.makeText(HomeScreen.this, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        }); */
	}
    public void helppdf() {
    	//Open the pdf here
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
