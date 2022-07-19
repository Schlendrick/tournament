package ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament;

import java.util.List;

public class Tournament {
    private Long id;
    private String title;
    private Integer fields;
    private Integer minBreakMatchesRound;
    private List<TournamentDay> tournamentDays;

    public Tournament() {
    }

    public Tournament(Long id, String title, Integer fields, Integer minBreakMatchesRound, List<TournamentDay> tournamentDays) {
        this.id = id;
        this.title = title;
        this.fields = fields;
        this.minBreakMatchesRound = minBreakMatchesRound;
        this.tournamentDays = tournamentDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFields() {
        return fields;
    }

    public void setFields(Integer fields) {
        this.fields = fields;
    }

    public Integer getMinBreakMatchesRound() {
        return minBreakMatchesRound;
    }

    public void setMinBreakMatchesRound(Integer minBreakMatchesRound) {
        this.minBreakMatchesRound = minBreakMatchesRound;
    }

    public List<TournamentDay> getTournamentDays() {
        return tournamentDays;
    }

    public void setTournamentDays(List<TournamentDay> tournamentDays) {
        this.tournamentDays = tournamentDays;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fields=" + fields +
                ", minBreakMatchesRound=" + minBreakMatchesRound +
                ", tournamentDays=" + tournamentDays +
                '}';
    }
}

