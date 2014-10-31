package com.example.statapalpha;

import android.provider.BaseColumns;

public class HomeTable {
	
	public HomeTable() {
		
	}
	
	public static abstract class HomeTableInfo implements BaseColumns {
		
		public static final String DATABASE_NAME = "statap_alpha";
		public static final String TABLE_NAME = "table_info";
		public static final String GAME_NAME = "Game 1";
	}
	
}
