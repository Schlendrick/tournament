package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback.MessageFeedbackProvider;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.OrganizerInsertValidation;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component()
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizerEntryTableCell extends TableCell<PlayTime, Match> {

    @Autowired
    private OrganizerInsertValidation validator;

    @Autowired
    private MatchDragDropBoard customDragBoard;

    private MessageFeedbackProvider feedbackProvider;

    private final OrganizerEntry control;

    OrganizerEntryTableCell() {
        control = new OrganizerEntry();
    }

    @PostConstruct
    void initDnD() {
        control.setOnDragEntered(e -> {
            OrganizerResult result = analyzeDropEvent(e, customDragBoard.get());
            if (result.isOk()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            feedbackProvider.setMessage(result.getMessage());
            e.consume();
        });

        control.setOnDragOver(e -> {
            OrganizerResult result = analyzeDropEvent(e, customDragBoard.get());
            if (result.isOk()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        control.setOnDragDropped(e -> {
            boolean success = false;
            TablePosition<PlayTime, Match> dp = getDropPoint();
            List<Match> matches = customDragBoard.get();

            OrganizerResult result = analyzeDropEvent(e, matches);
            if (result.isOk()) {
                transferMatches(dp, matches);
                success = true;
            }

            TableView.TableViewSelectionModel<PlayTime> selModel = getTableView().getSelectionModel();
            selModel.clearSelection();
            selModel.selectRange(dp.getRow(), dp.getTableColumn(), dp.getRow() + customDragBoard.size() - 1, dp.getTableColumn());

            e.setDropCompleted(success);
            e.consume();
        });
    }

    void setFeedbackProvider(MessageFeedbackProvider feedbackProvider) {
        this.feedbackProvider = feedbackProvider;
    }

    @Override
    public void updateItem(Match item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
            return;
        }

        control.set(item);
        setGraphic(control);
    }

    private void transferMatches(TablePosition<PlayTime, Match> dropPoint, List<Match> droppedItems) {
        int row = dropPoint.getRow();

        ObservableList<PlayTime> items = getTableView().getItems();
        for (Match match : droppedItems) {
            items.get(row++).setMatch(Integer.parseInt(dropPoint.getTableColumn().getId()), match);
        }

        // TODO dbHandler.updateMatches(new ArrayList<>(droppedItems));
    }

    private OrganizerResult analyzeDropEvent(DragEvent e, List<Match> droppedItems) {
        if (!(e.getDragboard().hasString() && e.getDragboard().getString().equals(MatchDragDropBoard.DRAG_DROP_STRING))) {
            return OrganizerResult.create("Falsches Format");
        }

        return validator.checkInsertPosition(getDropPoint(), droppedItems);

    }

    private TablePosition<PlayTime, Match> getDropPoint() {
        return new TablePosition<>(getTableView(), getTableRow().getIndex(), getTableColumn());
    }

}