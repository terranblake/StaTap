package com.example.statapalpha;

/*
 * ListData class will hold data for displaying in ListView
 * */
public class StatListData {
 
        int Jersey;
        int Points;
        int FT;
        int FG;
        int Assists;
        int Rebounds;
        int Steals;
        int TO;
        int Fouls;
        
        String title;
 
        public Integer getJersey() {
                return Jersey;
        }
 
        public void setJersey(Integer jerseynum) {
                Jersey = jerseynum;
        }
 
        public Integer getPoints() {
            return Points;
        }

        public void setPoints(Integer points) {
            Points = points;
        }
        public Integer getFT() {
            return FT;
        }

        public void setFT(Integer fts) {
            FT = fts;
        }
        public Integer getFG() {
            return FG;
        }

        public void setFG(Integer fgs) {
            FG = fgs;
        }
        public Integer getAssists() {
            return Assists;
        }

        public void setAssists(Integer ass) {
            Assists = ass;
        }
        public Integer getRebounds() {
            return Rebounds;
        }

        public void setRebounds(Integer rb) {
            Rebounds = rb;
        }
        public Integer getSteals() {
            return Steals;
        }

        public void setSteals(Integer stl) {
            Steals = stl;
        }
        public Integer getTO() {
            return TO;
        }

        public void setTO(Integer turnovers) {
            TO = turnovers;
        }
        public Integer getFouls() {
            return Fouls;
        }

        public void setFouls(Integer foul) {
            Fouls = foul;
        }

}