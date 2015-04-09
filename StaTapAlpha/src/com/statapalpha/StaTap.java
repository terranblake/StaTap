package com.statapalpha;

import android.app.Application;

public class StaTap extends Application {
	private long defaultTimer;

    public long getDefaultTime() {
    	return defaultTimer;
    }
    public void setDefaultTime(long time) {
    	defaultTimer = time;
    }
}
