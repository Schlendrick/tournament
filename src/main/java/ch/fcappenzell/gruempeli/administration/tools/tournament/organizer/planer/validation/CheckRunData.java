package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;/*
 * Created by Marco on 09.06.2018.
 */

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CheckRunData {
    ValidationSnap getSnap();

    int getInsertTimeIndex();

    int getInsertField();

    SnapMatch getInsertMatch();

    static Optional<Long> isSameTeamsInvolved(@NonNull SnapMatch m1, @NonNull SnapMatch m2) {

        if (m1.getTeamAId() == m2.getTeamAId()) {
            return Optional.of(m1.getTeamAId());
        }

        if (m1.getTeamAId() == m2.getTeamBId()) {
            return Optional.of(m1.getTeamAId());
        }

        if (m1.getTeamBId() == m2.getTeamBId()) {
            return Optional.of(m1.getTeamBId());
        }

        if (m1.getTeamBId() == m2.getTeamAId()) {
            return Optional.of(m1.getTeamBId());
        }

        return Optional.empty();
    }
}
