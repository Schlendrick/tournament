/*
 * Created by Marco on 09.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.PlayTime;
import javafx.scene.control.TableView;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

public class ValidationSnap {

    private final SnapTime[] times;
    private final HashMap<SnapTime, SnapMatch[]> timeTableMap;

    public static ValidationSnap takeSnap(TableView<PlayTime> tableView, List<Match> draggedItems) {
        return new ValidationSnap(tableView, draggedItems);
    }

    private ValidationSnap(TableView<PlayTime> tableView, List<Match> draggedItems) {
        times = new SnapTime[tableView.getItems().size()];
        timeTableMap = new HashMap<>();

        for (int i = 0; i < tableView.getItems().size(); i++) {
            PlayTime time = tableView.getItems().get(i);

            SnapTime snapTime = new SnapTime(i, time);
            times[i] = snapTime;

            int fieldSize = time.getMatches().size();
            SnapMatch[] matches = new SnapMatch[fieldSize];
            for (int field = 0; field < fieldSize; field++) {
                Match match = time.getMatch(field).get();
                if (match != null && !draggedItems.contains(match)) { //don't put the dragged items into the snap
                    matches[field] = new SnapMatch(match);
                }
            }

            timeTableMap.put(snapTime, matches);
        }
    }

    @NonNull
    public SnapMatch[] getMatches(int timeIndex) {
        SnapTime time = getSnapTime(timeIndex);
        return timeTableMap.get(time);
    }

    void insert(int insertTimeIndex, int insertField, SnapMatch snapMatch) {
        getMatches(insertTimeIndex)[insertField] = snapMatch;
    }

    @Nullable
    public SnapMatch getMatch(int insertTimeIndex, int insertField) {
        return getMatches(insertTimeIndex)[insertField];
    }

    @NonNull
    public List<SnapMatch[]> getMatchesToCheckRange(int insertTimeIndex, int range) {
        return getMatchesToCheckRange(insertTimeIndex, range, range);
    }

    @NonNull
    public List<SnapMatch[]> getMatchesToCheckRange(int insertTimeIndex, int rangeBefore, int rangeAfter) {
        List<SnapMatch[]> rowsToCheck = new ArrayList<>();

        for (int checkTimeOffset = -rangeBefore; checkTimeOffset <= rangeAfter; checkTimeOffset++) {
            int rowToCheck = insertTimeIndex + checkTimeOffset;
            if (rowToCheck >= 0) {
                rowsToCheck.add(getMatches(rowToCheck));
            }
        }

        return rowsToCheck;
    }

    public Set<Map.Entry<SnapTime, SnapMatch[]>> getAllScheduledMatches() {
        return timeTableMap.entrySet();
    }

    private SnapTime getSnapTime(int insertTimeIndex) {
        return times[insertTimeIndex];
    }
}
