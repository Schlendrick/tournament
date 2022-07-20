package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament.TournamentDay;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.DefaultMessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.MessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.service.MatchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizerDayView extends TableView<PlayTime> {

    @Autowired
    ApplicationContext context;

    private MessageFeedbackProvider feedbackProvider;

    @Autowired
    private MatchDragDropBoard customDragBoard;

    @Autowired
    MatchService matchService;

    private TournamentDay tournamentDay;
    private int fields;
    private Runnable openMatchesViewUpdater;

    public void initSchedule(TournamentDay td, int fields, ObservableList<Match> matches) {
        this.tournamentDay = td;
        this.fields = fields;

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().setCellSelectionEnabled(true);

        createColumns();
        fillInScheduleTimes();

        updateTableFromMatches(matches);

        initDragDrop();
        addKeyActions();

    }

    public void setFeedbackProvider(DefaultMessageFeedbackProvider feedbackProvider) {
        this.feedbackProvider = feedbackProvider;
    }

    public void createColumns() {
        getColumns().add(createTimeColumn());
        for (int i = 0; i < fields; i++) {
            getColumns().add(createFieldColumn(i + 1));
        }
    }

    public TableColumn<PlayTime, String> createTimeColumn() {
        TableColumn<PlayTime, String> timeCol = new TableColumn<>("Zeit");
        timeCol.setMaxWidth(2000);
        timeCol.setCellFactory(param -> new TextFieldTableCell<>());
        timeCol.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().start.format(DateTimeFormatter.ISO_LOCAL_TIME)));
        return timeCol;
    }

    public TableColumn<PlayTime, Match> createFieldColumn(final int fieldNr) {
        TableColumn<PlayTime, Match> col = new TableColumn<>("Platz: " + fieldNr);
        col.setId(Integer.toString(fieldNr));
        col.setCellFactory(param -> {
            OrganizerEntryTableCell bean = context.getBean(OrganizerEntryTableCell.class);
            bean.setFeedbackProvider(feedbackProvider);
            return bean;
        });
        col.setCellValueFactory(param -> param.getValue().getMatch(fieldNr));
        return col;
    }

    private void fillInScheduleTimes() {
        ObservableList<PlayTime> scheduleItems = FXCollections.observableArrayList();
        LocalDateTime time = LocalDateTime.of(tournamentDay.getDay(), tournamentDay.getStartTime());
        long dTnextMatch = tournamentDay.getMatchTime().plusMinutes(tournamentDay.getBreakTime().toMinutes()).toMinutes();
        do {
            scheduleItems.add(new PlayTime(time));
            time = time.plusMinutes(dTnextMatch);
        } while (time.getDayOfMonth() == tournamentDay.getDay().getDayOfMonth());
        setItems(scheduleItems);
    }

    private void updateTableFromMatches(ObservableList<Match> matches) {
        matches.stream().filter(this::isScheduled).forEach(this::put);
    }

    private boolean isScheduled(Match m) {
        return m.getTime() != null && m.getField() > 0;
    }

    private void put(Match m) {
        getItems().stream()
                .filter(p -> p.start.equals(m.getTime()))
                .findFirst()
                .ifPresent(p -> p.setMatch(m.getField(), m));
    }
    private void addKeyActions() {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {

                List<Match> matches = new ArrayList<>();
                List<SimpleObjectProperty<Match>> matchProperties = new ArrayList<>();

                for (TablePosition pos : getSelectionModel().getSelectedCells()) {
                    SimpleObjectProperty<Match> match = getItems().get(pos.getRow()).getMatch(pos.getColumn());
                    matchProperties.add(match);

                    if (match.get() != null) {
                        matches.add(match.get());
                    }
                }
                //matchService.clearMatches(matches);

                matchProperties.forEach(match -> match.set(null));
                openMatchesViewUpdater.run();

            }
        });
    }

    private void initDragDrop() {
        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            try {
                ClipboardContent content = new ClipboardContent();
                content.putString(MatchDragDropBoard.DRAG_DROP_STRING);
                db.setContent(content);

                List<Match> selection = getSelectionModel().getSelectedCells().stream()
                        .map(p -> getItems().get(p.getRow()).getMatch(Integer.parseInt(p.getTableColumn().getId())))
                        .filter(m -> m.get() != null)
                        .map(ObservableObjectValue::get)
                        .collect(Collectors.toList());
                customDragBoard.put(selection);
            } catch (Exception ex) {
                System.out.print("Fehler in Drag&Drop");
            }

            e.consume();
        });

        setOnDragDone(e -> {
            getItems().forEach(p ->
                    p.getMatchEntries().forEach(entry -> {
                        Match match = entry.getValue().get();
                        if (match != null && (!entry.getKey().equals(match.getField()) || !p.start.equals(match.getTime()))) {
                            entry.getValue().set(null);
                        }
                    }));

            e.consume();
        });
    }

    public void addChangedListener(Runnable updater) {
        openMatchesViewUpdater = updater;
    }
}