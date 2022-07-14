/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.SnapMatch;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.SnapTime;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MatchOrderCheck implements InsertCheck {

    @Override
    public OrganizerResult test(CheckRunData data) {
        //assumption round id and match id increment corresponds to match order

        for (Map.Entry<SnapTime, SnapMatch[]> scheduledEntry : data.getSnap().getAllScheduledMatches()) {

            OrganizerResult result = checkEntry(scheduledEntry, data.getInsertTimeIndex(), data.getInsertMatch());
            if (!result.isOk()) {
                return result;
            }
        }

        return OrganizerResult.ok();
    }

    private OrganizerResult checkEntry(Map.Entry<SnapTime, SnapMatch[]> scheduledEntry, int insertTimeIndex, SnapMatch insertMatch) {
        int insertCompare = Long.compare(scheduledEntry.getKey().getTimeIdex(), insertTimeIndex);
        for (SnapMatch match : scheduledEntry.getValue()) {

            if (match != null) {
                int timeOrderCompare = insertMatch.timeOrderCompareTo(match);
                boolean matchIsLaterInOrder = timeOrderCompare > 0;
                boolean matchIsBeforeInOrder = timeOrderCompare < 0;

                boolean notCorrect;

                if (insertMatch.getRoundId() != match.getRoundId()) {
                    notCorrect = insertCompare <= 0 && matchIsLaterInOrder || insertCompare >= 0 && matchIsBeforeInOrder;
                } else {
                    notCorrect = insertCompare < 0 && matchIsLaterInOrder || insertCompare > 0 && matchIsBeforeInOrder;
                }

                if (notCorrect) {
                    String position = insertCompare < 0 ? "nach" : "vor";
                    return OrganizerResult.create("Spiel " + insertMatch.getMessageString() + " kann nicht " + position + "dem Spiel " + match.getMessageString() + " eingefügt werden.");
                }
            }
        }

        return OrganizerResult.ok();
    }
}
