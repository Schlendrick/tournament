package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AvailableMatchesView extends ListView<Match>  {

    static Logger logger = LoggerFactory.getLogger(AvailableMatchesView.class);

    DbHandler dbHandler;

    MatchDragDropBoard customDragBoard;

    public AvailableMatchesView(DbHandler dbHandler, MatchDragDropBoard customDragBoard) {
        this.dbHandler = dbHandler;
        this.customDragBoard = customDragBoard;
    }

    public void initMatchesView(ObservableList<Match> matches) {
        setCellFactory(p -> new SchedulerEntryListCell());
        setItems(new FilteredList<>(matches, this::isOpen));
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        initDragAndDrop();
    }

    public boolean isOpen(Match m) {
        return m != null && m.getTime() == null;
    }

    public void initDragAndDrop() {
        setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture*/
            logger.info("onDragDetected");

            Dragboard db = startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(MatchDragDropBoard.DRAG_DROP_STRING);
            db.setContent(content);
            customDragBoard.put(getSelectionModel().getSelectedItems());

            event.consume();
        });

        setOnDragDone(event -> {
            /* the drag-and-drop gesture ended */
            logger.info("onDragDone");

            updateViewItems();
            event.consume();
        });

        setOnDragOver(event -> {
            /* data is dragged over the target */
            //logger.info("onDragOver");

            if (event.getDragboard().hasString() && event.getDragboard().getString().equals(MatchDragDropBoard.DRAG_DROP_STRING)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            /* data dropped */
            logger.info("onDragDropped");

            List<Match> matches = customDragBoard.get();
            dbHandler.clearMatches(matches);

            updateViewItems();
            event.consume();
        });
    }

    public void updateViewItems() {
        ((FilteredList<Match>) getItems()).setPredicate(this::isOpen);
    }

    static class SchedulerEntryListCell extends ListCell<Match> {
        OrganizerEntry control = new OrganizerEntry();

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
    }
}
