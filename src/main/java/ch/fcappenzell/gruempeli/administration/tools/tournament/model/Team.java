package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Team implements Comparable<Team>{
    private Long id;

    private final SimpleStringProperty name = new SimpleStringProperty();
    private LocalDate entry;
    private LocalDate paid;
    private final SimpleStringProperty note = new SimpleStringProperty();
    private final SimpleIntegerProperty force = new SimpleIntegerProperty();
    private int teamNr;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getEntry() {
        return entry;
    }

    public void setEntry(LocalDate entry) {
        this.entry = entry;
    }

    public LocalDate getPaid() {
        return paid;
    }

    public void setPaid(LocalDate paid) {
        this.paid = paid;
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public Integer getForce() {
        return force.get();
    }

    public void setForce(Integer force) {
        this.force.set(force);
    }

    public SimpleIntegerProperty forceProperty() {
        return force;
    }

    public void setTeamNr(int nr) {
        this.teamNr = nr;
    }

    public int getTeamNr() {
        return teamNr;
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
    public String toString() {
        if (name != null && name.get() != null)
            return name.get();
        if (id != null)
            return String.valueOf(id);

        return super.toString();
    }

    @Override
    public int compareTo( Team o) {
        return Long.compare(id, o.getId());
    }
}