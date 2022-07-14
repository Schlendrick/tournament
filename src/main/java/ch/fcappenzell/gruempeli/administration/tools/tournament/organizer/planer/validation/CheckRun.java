/*
 * Created by Marco on 09.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.OrganizerResult;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation.rules.InsertCheck;

class CheckRun implements CheckRunData {

    private final InsertCheck[] checks;

    private ValidationSnap snap;

    private int insertTimeIndex;
    private int insertField;
    private SnapMatch insertMatch;

    CheckRun(InsertCheck... checks) {
        this.checks = checks;
    }

    void init(ValidationSnap snap) {
        this.snap = snap;
    }

    OrganizerResult run(int insertTimeIndex, int insertField, SnapMatch insertMatch) {
        this.insertTimeIndex = insertTimeIndex;
        this.insertField = insertField;
        this.insertMatch = insertMatch;

        OrganizerResult result = OrganizerResult.ok();

        for (InsertCheck check : checks) {
            result = check.test(this);
            if (!result.isOk()) {
                return result;
            }
        }

        return result;
    }

    @Override
    public ValidationSnap getSnap() {
        return snap;
    }

    @Override
    public int getInsertTimeIndex() {
        return insertTimeIndex;
    }

    @Override
    public int getInsertField() {
        return insertField;
    }

    @Override
    public SnapMatch getInsertMatch() {
        return insertMatch;
    }

}
