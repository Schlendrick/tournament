package ch.fcappenzell.gruempeli.administration.tools.tournament.model.team;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.List;

public class Team implements Comparable<Team>{
    private Long id;
    private String name;
    private TeamCaptain captain;
    private List<TeamPlayer> players;
    private String wishes;
    private Boolean disqualified;
    private Boolean blindTeam;
    private Boolean paid;
    private Boolean cashPayment;
    private LocalDate dateRegistration;
    private LocalDate dateAmountReceived;
    private LocalDate entry;
    private int teamNr;

    public Team() {
    }

    public Team(Long id, String name, TeamCaptain captain, List<TeamPlayer> players, String wishes, Boolean disqualified, Boolean blindTeam, Boolean paid, Boolean cashPayment, LocalDate dateRegistration, LocalDate dateAmountReceived) {
        this.id = id;
        this.name = name;
        this.captain = captain;
        this.players = players;
        this.wishes = wishes;
        this.disqualified = disqualified;
        this.blindTeam = blindTeam;
        this.paid = paid;
        this.cashPayment = cashPayment;
        this.dateRegistration = dateRegistration;
        this.dateAmountReceived = dateAmountReceived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamCaptain getCaptain() {
        return captain;
    }

    public void setCaptain(TeamCaptain captain) {
        this.captain = captain;
    }

    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayer> players) {
        this.players = players;
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }

    public Boolean getDisqualified() {
        return disqualified;
    }

    public void setDisqualified(Boolean disqualified) {
        this.disqualified = disqualified;
    }

    public Boolean getBlindTeam() {
        return blindTeam;
    }

    public void setBlindTeam(Boolean blindTeam) {
        this.blindTeam = blindTeam;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Boolean cashPayment) {
        this.cashPayment = cashPayment;
    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public LocalDate getDateAmountReceived() {
        return dateAmountReceived;
    }

    public void setDateAmountReceived(LocalDate dateAmountReceived) {
        this.dateAmountReceived = dateAmountReceived;
    }

    public LocalDate getEntry() {
        return entry;
    }

    public void setEntry(LocalDate entry) {
        this.entry = entry;
    }

    public int getTeamNr() {
        return teamNr;
    }

    public void setTeamNr(int teamNr) {
        this.teamNr = teamNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( Team o) {
        return Long.compare(id, o.getId());
    }
}