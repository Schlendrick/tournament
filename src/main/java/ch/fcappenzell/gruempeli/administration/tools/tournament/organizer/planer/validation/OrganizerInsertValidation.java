package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.PlayTime;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules.*;
import javafx.scene.control.TablePosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class OrganizerInsertValidation {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DroppedListCheck droppedListcheck;

    private CheckRun insertCheck;

    public OrganizerResult checkInsertPosition(TablePosition<PlayTime, Match> pos, List<Match> droppedItems) {
        //analyze dropped list
        OrganizerResult result = droppedListcheck.check(droppedItems);
        if (!result.isOk()) {
            return result;
        }

        ValidationSnap snap = ValidationSnap.takeSnap(pos.getTableView(), droppedItems);
        insertCheck.init(snap);

        int insertTimeIndex = pos.getRow();
        int insertField = pos.getColumn();

        for (Match match : droppedItems) {

            SnapMatch snapMatch = new SnapMatch(match);
            result = insertCheck.run(insertTimeIndex, insertField, snapMatch);

            if (!result.isOk()) {
                return result;
            }

            snap.insert(insertTimeIndex, insertField, snapMatch);

            insertTimeIndex++; //with every item increment (jump to next time)
        }

        return result;
    }

    @PostConstruct
    public void init() {
        insertCheck = new CheckRun(
                context.getBean(FieldCheck.class),
                context.getBean(MatchOrderCheck.class),
                context.getBean(TeamBreakCheck.class)
//                context.getBean(CorrectRoundBreaksCheck.class) //todo finalspiele (3.+4. und Final) baauchen keine Pause
        );
    }
}
