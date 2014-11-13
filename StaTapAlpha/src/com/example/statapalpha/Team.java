package com.example.statapalpha;

public class Team {
	 
    private int id;
    private String teamname;
 
    public Team(){}
 
    public Team(String teamname) {
        super();
        this.teamname = teamname;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Team [id=" + id + ", title=" + teamname
                + "]";
    }
}