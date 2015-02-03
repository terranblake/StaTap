package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;
import com.example.statapalpha.R.id;
import com.example.statapalpha.R.layout;
import com.example.statapalpha.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class CurrentStats extends Activity {
	public View.OnTouchListener gestureListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stats);
        ListView lv = (ListView)findViewById(R.id.lvStatList);
        
        gestureListener = new View.OnTouchListener() {
            private int padding = 0;
            private int initialx = 0;
            private int currentx = 0;
            private  ViewHolder viewHolder;
            public boolean onTouch(View v, MotionEvent event) {
                if ( event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    padding = 0;
                    initialx = (int) event.getX();
                    currentx = (int) event.getX();
                    viewHolder = ((ViewHolder) v.getTag());
                }
                if ( event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    currentx = (int) event.getX();
                    padding = currentx - initialx;
                }
                
                if ( event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    padding = 0;
                    initialx = 0;
                    currentx = 0;
                }
                
                if(viewHolder != null)
                {
                    if(padding == 0)
                    {
                        v.setBackgroundColor(0xFF000000 );
                    }
                    if(padding > 75)
                    {
                        viewHolder.setRunning(true);
                    }
                    if(padding < -75)
                    {
                        viewHolder.setRunning(false);
                    }
                    v.setBackgroundColor(viewHolder.getColor());  
                    viewHolder.icon.setImageResource(viewHolder.getImageId());
                    v.setPadding(padding, 0,0, 0);
                }
                return true;
            }
        };
        
        ModelArrayAdapter adapter = new ModelArrayAdapter(this, getData(),gestureListener);
        lv.setAdapter(adapter);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_stats, menu);
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
