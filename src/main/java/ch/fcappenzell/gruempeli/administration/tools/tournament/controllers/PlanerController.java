package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Tournament;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.DefaultMessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.AvailableMatchesView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.MatchDragDropBoard;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerDayView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
public class PlanerController {

    @FXML
    private VBox openMatchesPane;

    @FXML
    private TabPane scheduleTabPane;

    @Autowired
    MatchDragDropBoard dragDropBoard;

    @Autowired
    ApplicationContext context;

    private final ObservableList<Match> matches = FXCollections.observableArrayList();

    public void updateMatches(DbHandler dbHandler) {
        List<Match> matchList = dbHandler.getMatches();

        matches.clear();
        openMatchesPane.getChildren().clear();
        scheduleTabPane.getTabs().clear();

        matches.addAll(FXCollections.observableArrayList(matchList));

        AvailableMatchesView openMatchesView = new AvailableMatchesView(dbHandler, dragDropBoard);
        openMatchesView.initMatchesView(matches);
        openMatchesPane.getChildren().add(openMatchesView);
        VBox.setVgrow(openMatchesView, Priority.ALWAYS);

        Tournament tournament = dbHandler.getTournamentQuery();
        tournament.getTournamentDays().forEach(td -> {
            BorderPane borderPane = new BorderPane();

            DefaultMessageFeedbackProvider feedbackProvider = new DefaultMessageFeedbackProvider();

            OrganizerDayView schedule = context.getBean(OrganizerDayView.class);
            schedule.initSchedule(td, tournament.getFields(), matches);
            schedule.setFeedbackProvider(feedbackProvider);
            schedule.addChangedListener(openMatchesView::updateViewItems);
            String dayDisplayName = td.getDay().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            borderPane.setCenter(schedule);
            borderPane.setBottom(feedbackProvider);
            addTab(dayDisplayName, borderPane);
        });
    }

    private void addTab(String name, Node node) {
        Tab tab = new Tab(name);
        tab.closableProperty().set(false);
        tab.setContent(node);
        scheduleTabPane.getTabs().add(tab);
    }

    public void destroy() {

    }

}
