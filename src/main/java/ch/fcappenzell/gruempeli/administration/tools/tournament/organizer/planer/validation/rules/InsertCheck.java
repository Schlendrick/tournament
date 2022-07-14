/*
 * Created by Marco on 08.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.CheckRunData;

public interface InsertCheck {

    OrganizerResult test(CheckRunData data);

}