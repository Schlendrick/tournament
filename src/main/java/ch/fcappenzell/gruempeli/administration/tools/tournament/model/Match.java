package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;

import java.time.LocalDateTime;

public class Match {

    long id;
    long order;
    Holding holding;
    int field;
    LocalDateTime time;
    Team homeTeam;
    Team visitorTeam;
    long homeScore;
    long visitorScore;
    boolean penalty;

    public Match() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public LocalDateTime getTime() {
        return this.time;//LocalDateTime.now();
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public long getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(long homeScore) {
        this.homeScore = homeScore;
    }

    public long getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(long visitorScore) {
        this.visitorScore = visitorScore;
    }

    public boolean isPenalty() {
        return penalty;
    }

    public void setPenalty(boolean penalty) {
        this.penalty = penalty;
    }
}
