package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDragDropBoard {

    static final String DRAG_DROP_STRING = "hellanodikai_dragdrop_event";
    private final ObservableList<Match> customDragBoard = FXCollections.observableArrayList();

    public void put(List<Match> matches) {
        customDragBoard.clear();
        customDragBoard.addAll(matches);
    }

    public List<Match> get() {
        return customDragBoard;
    }

    public int size() {
        return customDragBoard.size();
    }
}
