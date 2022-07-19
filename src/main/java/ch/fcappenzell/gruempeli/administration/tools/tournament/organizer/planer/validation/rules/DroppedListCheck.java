/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.SnapMatch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DroppedListCheck {

    public OrganizerResult check(List<Match> droppedItems) {
        for (int i = 0; i < droppedItems.size() - 1; i++) {

            SnapMatch m1 = new SnapMatch(droppedItems.get(i));
            SnapMatch m2 = new SnapMatch(droppedItems.get(i + 1));

            Optional<Long> sameTeamsPlaying = CheckRunData.isSameTeamsInvolved(m1, m2);
            if (sameTeamsPlaying.isPresent()) {
                return OrganizerResult.create("Bei den Spielen ("+ m1.getMessageString()+" und "+ m2.getMessageString() +") die beweget werden ist eine Pause nötig bei Team " +
                        sameTeamsPlaying + ". Spiele einzlen verschieben");
            }
        }

        return OrganizerResult.ok();
    }

}
