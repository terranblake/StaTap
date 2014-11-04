package com.example.statapalpha.model;

import java.sql.Date;
 
public class Games {
 
    private int id;
    private String title;
    private String htn;
    private String atn;
    private Date date;
 
    public Games(){}
 
    public Games(String title, String htn, String atn, Date date) {
        super();
        this.title = title;
        this.htn = htn;
        this.atn = atn;
        this.date = date;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Game ID=" + id + ", Title=" + title + ", Home team ID=" + htn
                + "Away Team ID=" + atn + "Date=" + date;
    }

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	public String getHTN() {
		// TODO Auto-generated method stub
		return htn;
	}

	public String getATN() {
		// TODO Auto-generated method stub
		return atn;
	}

}