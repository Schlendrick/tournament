package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tournament {
    private final SimpleStringProperty title = new SimpleStringProperty();
    private ObservableList<TournamentDay> tournamentDays = FXCollections.observableArrayList();
    private final int minBrakMatchesRound = 2;
    private int fields;

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableList<TournamentDay> getTournamentDays() {
        return tournamentDays;
    }

    public void setTournamentDays(ObservableList<TournamentDay> tournamentDays) {
        this.tournamentDays = tournamentDays;
    }

    public int getFields() {
        return fields;
    }

    public void setFields(int fields) {
        this.fields = fields;
    }

    public int getMinBreakMatchesRound() {
        return minBrakMatchesRound;
    }
}
