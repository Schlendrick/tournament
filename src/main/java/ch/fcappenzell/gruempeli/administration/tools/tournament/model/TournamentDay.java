package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TournamentDay {

    private long id;
    private LocalDate day;
    private LocalTime startTime;
    private Duration matchTime;
    private Duration breakTime;
    private Duration breakTimeFinals;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Duration getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Duration breakTime) {
        this.breakTime = breakTime;
    }

    public Duration getBreakTimeFinals() {
        return breakTimeFinals;
    }

    public void setBreakTimeFinals(Duration breakTimeFinals) {
        this.breakTimeFinals = breakTimeFinals;
    }

    public Duration getMatchTime() {
        return matchTime;
    }

    public TournamentDay setMatchTime(Duration matchTime) {
        this.matchTime = matchTime;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
