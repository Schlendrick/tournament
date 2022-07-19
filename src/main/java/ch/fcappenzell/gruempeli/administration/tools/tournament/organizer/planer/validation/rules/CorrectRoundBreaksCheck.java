/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.SnapMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CorrectRoundBreaksCheck implements InsertCheck {


    @Override
    public OrganizerResult test(CheckRunData data) {
        // TODO int breaks = dbHandler.getTournament().getMinBreakMatchesRound();
        int breaks = 4;
        List<SnapMatch[]> matchesToCheckRange = data.getSnap().getMatchesToCheckRange(data.getInsertTimeIndex(), breaks);

        return matchesToCheckRange.stream()
                .flatMap(Arrays::stream)
                .map(match -> isSameCategoryAndOtherRound(data.getInsertMatch(), match, breaks))
                .filter(organizerResult -> !organizerResult.isOk()).findAny()
                .orElse(OrganizerResult.ok());
    }

    private OrganizerResult isSameCategoryAndOtherRound(SnapMatch match1, SnapMatch match2, int breaks) {
        if (match1 == null || match2 == null) {
            return OrganizerResult.ok();
        }

        if (match1.getMatchId() == match2.getMatchId()) {
            return OrganizerResult.ok();
        }

        if (match1.getCatgegoryId().equalsIgnoreCase(match2.getCatgegoryId()) && match1.getRoundId() != match2.getRoundId()) {
            return OrganizerResult.create("Zwischen zwei Runden der Kategore " + match1.getCatgegoryId() + " hat es nicht genügen Pause. (" + breaks + " Spiele nötig)");
        }

        return OrganizerResult.ok();
    }
}
