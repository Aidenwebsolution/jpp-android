package com.jaipurpinkpanthers.android.adapters;

/**
 * Created by wohlig on 22/8/16.
 */
public class MatchUpdateGetter {

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getStarttimedate() {
        return starttimedate;
    }

    public void setStarttimedate(String starttimedate) {
        this.starttimedate = starttimedate;
    }

    public String getTeam1id() {
        return team1id;
    }

    public void setTeam1id(String team1id) {
        this.team1id = team1id;
    }

    public String getTeam2id() {
        return team2id;
    }

    public void setTeam2id(String team2id) {
        this.team2id = team2id;
    }

    public String getTeamimage1() {
        return teamimage1;
    }

    public void setTeamimage1(String matchtime) {
        this.teamimage1 = teamimage1;
    }
    public String getTeamimage2() {
        return teamimage2;
    }

    public void setTeamimage2(String matchtime) {
        this.teamimage2 = teamimage2;
    }
    public String getMatchtime() {
        return matchtime;
    }

    public void setMatchtime(String matchtime) {
        this.matchtime = matchtime;
    }


    String team1 ;
    String team2 ;
    String score1 ;
    String score2 ;
    String stadium ;
    String starttimedate;
    String team1id ;
    String team2id ;
    String matchtime ;
    String teamimage1 ;
    String teamimage2 ;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String count;

    public MatchUpdateGetter(String team1, String team2, String score1, String score2, String stadium,
                             String starttimedate, String team1id, String team2id, String matchtime){
        this.team1=team1;
        this.team2=team2;
        this.score1=score1;
        this.score2=score2;
        this.stadium=stadium;
        this.starttimedate=starttimedate;
        this.team1id=team1id;
        this.team2id=team2id;
        this.matchtime=matchtime;
        this.teamimage1=teamimage1;
        this.teamimage2=teamimage2;

    }
    public MatchUpdateGetter(String team1, String team2, String score1, String score2, String stadium,
                             String starttimedate, String team1id, String team2id, String matchtime,String teamimage1 ,String teamimage2 ,String count){
        this.team1=team1;
        this.team2=team2;
        this.score1=score1;
        this.score2=score2;
        this.stadium=stadium;
        this.starttimedate=starttimedate;
        this.team1id=team1id;
        this.team2id=team2id;
        this.matchtime=matchtime;
        this.teamimage1=teamimage1;
        this.teamimage2=teamimage2;
        this.count=count;


    }
    public MatchUpdateGetter(String team1, String team2, String score1, String score2, String stadium,
                             String starttimedate, String team1id, String team2id, String matchtime,String count){
        this.team1=team1;
        this.team2=team2;
        this.score1=score1;
        this.score2=score2;
        this.stadium=stadium;
        this.starttimedate=starttimedate;
        this.team1id=team1id;
        this.team2id=team2id;
        this.matchtime=matchtime;

        this.count=count;

    }

}

