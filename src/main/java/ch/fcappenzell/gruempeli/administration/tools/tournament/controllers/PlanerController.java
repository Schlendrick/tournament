package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.AvailableMatchesView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.MatchDragDropBoard;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlanerController {

    @FXML
    private VBox openMatchesPane;

    @FXML
    private TabPane scheduleTabPane;

    @Autowired
    MatchDragDropBoard dragDropBoard;

    private final ObservableList<Match> matches = FXCollections.observableArrayList();

    public void updateMatches(DbHandler dbHandler) {
        List<Match> matchList = dbHandler.getMatches();
        matches.clear();
        matches.addAll(FXCollections.observableArrayList(matchList));

        AvailableMatchesView openMatchesView = new AvailableMatchesView(dbHandler, dragDropBoard);
        openMatchesView.initMatchesView(matches);
        openMatchesPane.getChildren().add(openMatchesView);
        VBox.setVgrow(openMatchesView, Priority.ALWAYS);
    }

    public void destroy() {
        matches.clear();
    }

}
