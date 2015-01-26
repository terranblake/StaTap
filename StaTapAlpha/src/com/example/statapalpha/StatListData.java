package com.example.statapalpha;

/*
 * ListData class will hold data for displaying in ListView
 * */
public class StatListData {
 
        int Jersey, Points, FTA, FTM, FGA, FGM, TPA, TPM, Assists, Rebounds, Steals,
        	TO, Fouls, Blocks;
        
        
        
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
        public Integer getFTA() {
            return FTA;
        }

        public void setFTA(Integer fts) {
            FTA = fts;
        }
        public Integer getFTM() {
            return FTM;
        }

        public void setFTM(Integer fts) {
            FTM = fts;
        }
        public Integer getFGA() {
            return FGA;
        }

        public void setFGA(Integer fts) {
            FGA = fts;
        }
        public Integer getFGM() {
            return FGM;
        }

        public void setFGM(Integer fts) {
            FGM = fts;
        }
        public Integer getTPA() {
            return TPA;
        }

        public void setTPA(Integer fts) {
            TPA = fts;
        }
        public Integer getTPM() {
            return TPM;
        }

        public void setTPM(Integer fts) {
            TPM = fts;
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
        public Integer getBlocks() {
        	return Blocks;
        }
        public void setBlocks(Integer bl) {
        	Blocks = bl;
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