/*
 * Created by Marco on 10.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.WindowPreferencesSupport;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Tournament;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.DefaultMessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.ConnectionListener;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
public class PlanerView implements ConnectionListener {

    @Autowired
    ApplicationContext context;

    @Autowired
    DbHandler dbHandler;

    @Autowired
    MatchDragDropBoard dragDropBoard;

    @Autowired
    WindowPreferencesSupport preferencesSupport;

    private SplitPane view;
    private VBox openMatchesPane;
    private TabPane scheduleTabPane;

    private final ObservableList<Match> matches = FXCollections.observableArrayList();

    private void init() {
        dbHandler.addListener(this);

        view = new SplitPane();
        view.setId("planerView");
        view.setDividerPosition(0, 0.2);
        view.setOrientation(Orientation.HORIZONTAL);

        openMatchesPane = new VBox();
        scheduleTabPane = new TabPane();
        scheduleTabPane.setSide(Side.BOTTOM);
        view.getItems().addAll(openMatchesPane, scheduleTabPane);

        preferencesSupport.addSingleDividerSupport(view);

        setUpViews();
    }

    @Override
    public void connected() {
        openMatchesPane.getChildren().clear();
        scheduleTabPane.getTabs().clear();

        updateMatches();
        setUpViews();
    }

    public Node getView() {
        if (view == null) {
            init();
        }
        return view;
    }

    private void updateMatches() {
        List<Match> matchList = dbHandler.getMatches();
        matches.clear();
        matches.addAll(FXCollections.observableArrayList(matchList));
    }

    private void setUpViews() {
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
            String dayDisplayName = td.getDay().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
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


}
