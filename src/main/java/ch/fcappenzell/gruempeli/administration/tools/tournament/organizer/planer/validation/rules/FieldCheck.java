/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;
import org.springframework.stereotype.Component;

@Component
public class FieldCheck implements InsertCheck {

    @Override
    public OrganizerResult test(CheckRunData data) {

        if (data.getInsertField() < 1) {
            return OrganizerResult.create("Kein gültiger Platz " + data.getInsertMatch().getMessageString());
        }

        if (data.getSnap().getMatch(data.getInsertTimeIndex(), data.getInsertField()) != null) {
            return OrganizerResult.create("Platz bereits besetzt " + data.getInsertMatch().getMessageString());
        }

        return OrganizerResult.ok();
    }
}
