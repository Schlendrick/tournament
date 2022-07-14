/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.SnapMatch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamBreakCheck implements InsertCheck {

    private static final int NUM_GAMES_BREAK_FOR_TEAM = 1;

    @Override
    public OrganizerResult test(CheckRunData data) {
        OrganizerResult result = OrganizerResult.ok();

        List<SnapMatch[]> matchesToCheckRange = data.getSnap().getMatchesToCheckRange(data.getInsertTimeIndex(), NUM_GAMES_BREAK_FOR_TEAM);
        for (SnapMatch[] matchesToCheck : matchesToCheckRange) {
            result = checkNotPlayingAtTime(matchesToCheck, data.getInsertMatch());

            if (!result.isOk()) {
                return result;
            }
        }

        return result;
    }

    private OrganizerResult checkNotPlayingAtTime(SnapMatch[] matches, SnapMatch insertedMatch) {
        for (SnapMatch scheduledMatch : matches) {

            if (scheduledMatch != null && !scheduledMatch.equals(insertedMatch)) {

                Optional<Long> sameTeams = CheckRunData.isSameTeamsInvolved(scheduledMatch, insertedMatch);

                if (sameTeams.isPresent()) {
                    return OrganizerResult.create("Die Mannschaft Nr. " + sameTeams.get() + " des Spiels " + insertedMatch.getMessageString() + " hat nicht genügend Spielpause");
                }
            }
        }

        return OrganizerResult.ok();
    }
}
