package ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TournamentDay {
    private Long id;
    private LocalDate day;
    private LocalTime startTime;
    private Duration matchTime;
    private Duration breakTime;
    private Duration breakTimeFinals;

    public TournamentDay() {
    }

    public TournamentDay(Long id, LocalDate day, LocalTime startTime, Duration matchTime, Duration breakTime, Duration breakTimeFinals) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.matchTime = matchTime;
        this.breakTime = breakTime;
        this.breakTimeFinals = breakTimeFinals;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Duration getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Duration matchTime) {
        this.matchTime = matchTime;
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

    @Override
    public String toString() {
        return "TournamentDay{" +
                "id=" + id +
                ", day=" + day +
                ", startTime=" + startTime +
                ", matchTime=" + matchTime +
                ", breakTime=" + breakTime +
                ", breakTimeFinals=" + breakTimeFinals +
                '}';
    }
}
