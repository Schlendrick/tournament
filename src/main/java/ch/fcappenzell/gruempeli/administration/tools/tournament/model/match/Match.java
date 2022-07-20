package ch.fcappenzell.gruempeli.administration.tools.tournament.model.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;

import java.time.LocalDateTime;

public class Match {

    long id;
    long order;
    Holding holding;
    int field;
    LocalDateTime time;

    Long homeTeamId;
    Team homeTeam;

    Long visitorTeamId;
    Team visitorTeam;
    long homeScore;
    long visitorScore;
    boolean penalty;

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", order=" + order +
                ", holding=" + holding +
                ", field=" + field +
                ", time=" + time +
                ", homeTeamId=" + homeTeamId +
                ", homeTeam=" + homeTeam +
                ", visitorTeamId=" + visitorTeamId +
                ", visitorTeam=" + visitorTeam +
                ", homeScore=" + homeScore +
                ", visitorScore=" + visitorScore +
                ", penalty=" + penalty +
                '}';
    }

    public String toName() {
        return holding.getRound().getBookName() + " Gr. " + holding.getCode() + "  (" + (holding.getMatches().indexOf(this) + 1) + ")";
    }

    public Match() {
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Long getVisitorTeamId() {
        return visitorTeamId;
    }

    public void setVisitorTeamId(Long visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
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
