package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.arrangements.ArrangementsDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.holdings.HoldingDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match.MatchDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.team.TeamDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.tournament.TournamentDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements.Arrangement;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament.Tournament;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.DefaultMessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.AvailableMatchesView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.MatchDragDropBoard;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerDayView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.*;

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

    public void updateMatches() {
        List<Match> matchList = this.getMatches();

        matches.clear();

        openMatchesPane.getChildren().clear();
        scheduleTabPane.getTabs().clear();

        matches.addAll(FXCollections.observableArrayList(matchList));

        AvailableMatchesView openMatchesView = new AvailableMatchesView(dragDropBoard);
        openMatchesView.initMatchesView(matches);
        openMatchesPane.getChildren().add(openMatchesView);
        VBox.setVgrow(openMatchesView, Priority.ALWAYS);

        TournamentDAO tournamentDAO = context.getBean(TournamentDAO.class);
        List<Tournament> tournamentList = tournamentDAO.getAllTournaments();
        Tournament tournament = tournamentList.get(0);
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

    public List<Match> getMatches() {

        List<Match> matches = new ArrayList<>();

        HoldingDAO holdingDAO = context.getBean(HoldingDAO.class);
        TeamDAO teamDAO = context.getBean(TeamDAO.class);
        MatchDAO matchDAO = context.getBean(MatchDAO.class);
        ArrangementsDAO arrangementsDAO = context.getBean(ArrangementsDAO.class);

        Map<Long, Team> teamMap = teamDAO.getAllTeamsMap();

        for (Long key : teamMap.keySet()) {
            System.out.print(key + "=" + teamMap.get(key) + ", ");
        }

        List<Color> colors = FXCollections.observableArrayList( //todo color picker
                Color.LIGHTBLUE,
                Color.LIGHTCORAL,
                Color.LIGHTCYAN,
                Color.LIGHTGOLDENRODYELLOW,
                Color.LIGHTPINK,
                Color.LIGHTSEAGREEN,
                Color.LIGHTSKYBLUE,
                Color.LIGHTSTEELBLUE,
                Color.LIGHTYELLOW,
                Color.BISQUE,
                Color.MOCCASIN,
                Color.TURQUOISE,
                Color.CHARTREUSE
        );

        List<Arrangement> arrangements = arrangementsDAO.getAllArrangements();
        //List<Match> matches1 = new ArrayList<>();
        HashMap<String, Round> roundMap = new HashMap<>();
        arrangements.forEach(arrangement -> {

            arrangement.getCategory().setColor(colors.get(0).toString());colors.remove(0);

            List<Round> rounds = new ArrayList<>();
            List<Holding> holdingsInCategory= holdingDAO.getAllHoldingsByCategory(arrangement.getCategory().getName());
            holdingsInCategory.forEach(holding -> {

                String roundCode = holding.getRoundCode();
                if (!roundMap.containsKey(roundCode)) {

                    Round round = new Round();
                    round.setArrangement(arrangement);
                    round.setCode(roundCode);
                    round.setBookName(roundCode);
                    roundMap.put(roundCode, round);
                    rounds.add(round);
                }
                holding.setRound(roundMap.get(roundCode));

                System.out.println(holding);
                List<Match> matches1 = matchDAO.getMatchesByCategory(holding.getId());
                matches1.forEach(match -> {

                    match.setHolding(holding);


                    if (teamMap.containsKey(match.getHomeTeamId())){
                        Team homeTeam = teamMap.get(match.getHomeTeamId());
                        match.setHomeTeam(homeTeam);
                    }
                    else {
                        System.out.println("ERRRRRORRR Visitor team" + match.toString());
                    }

                    if (teamMap.containsKey(match.getVisitorTeamId())){
                        Team visitorTeam = teamMap.get(match.getVisitorTeamId());
                        match.setVisitorTeam(visitorTeam);
                    }
                    else {
                        System.out.println("ERRRRRORRR Vistor team" + match.toString());
                    }

                    System.out.println(match);
                });
                matches.addAll(matches1);

                arrangement.setRounds(rounds);
            });
        });
        return matches;
    }

}
