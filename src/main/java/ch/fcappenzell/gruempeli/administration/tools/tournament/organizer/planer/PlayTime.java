package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.time.LocalDateTime;
import java.util.Map;

public class PlayTime {
    final LocalDateTime start;
    final ObservableMap<Integer, SimpleObjectProperty<Match>> matches;

    public PlayTime(LocalDateTime start) {
        this.start = start;
        matches = FXCollections.observableHashMap();
    }

    public void setMatch(int field, Match match) {
        getMatch(field).set(match);
        match.setField(field);
        match.setTime(start);
    }

    public SimpleObjectProperty<Match> getMatch(int field) {
        if (!matches.containsKey(field)) {
            matches.put(field, new SimpleObjectProperty<>());
        }
        return matches.get(field);
    }

    public void addMatch(Match m) {
        assert !matches.containsKey(m.getField());

        setMatch(m.getField(), m);
    }

    public boolean containsField(String field) {
        return matches.containsKey(field);
    }

    public java.util.Set<Map.Entry<Integer, SimpleObjectProperty<Match>>> getMatchEntries() {
        return matches.entrySet();
    }

    public java.util.Collection<SimpleObjectProperty<Match>> getMatches() {
        return matches.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayTime playTime = (PlayTime) o;

        return !(start != null ? !start.equals(playTime.start) : playTime.start != null);
    }

    @Override
    public int hashCode() {
        return start != null ? start.hashCode() : 0;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
