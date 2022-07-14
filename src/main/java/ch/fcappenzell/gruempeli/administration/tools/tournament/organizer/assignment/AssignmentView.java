/*
 * Created by Marco on 10.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.assignment;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Tournament;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.ConnectionListener;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class AssignmentView implements ConnectionListener {

    @Autowired
    DbHandler dbHandler;

    private SplitPane view;
    private TabPane scheduleTabPane;

    @Override
    public void connected() {

        setUpViews();
    }

    private void init() {
        dbHandler.addListener(this);

        view = new SplitPane();

        scheduleTabPane = new TabPane();
        scheduleTabPane.setSide(Side.BOTTOM);
        view.getItems().addAll(scheduleTabPane);
    }

    public Node getView() {
        if (view == null) {
            init();
        }
        return view;
    }

    private void setUpViews() {
        Tournament tournament = dbHandler.getTournamentQuery();
        tournament.getTournamentDays().forEach(td -> {

            String dayDisplayName = td.getDay().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            BorderPane borderPane = new BorderPane();
            //borderPane.setCenter(schedule);
            //borderPane.setBottom(feedbackProvider);
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
