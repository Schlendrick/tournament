package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;

public interface GameModelVisitor {

    default void visit(Arrangement arrangement) {
    }

    default void visit(Round round) {
    }

    default void visit(Holding holding) {
    }

    default void visit(Match match) {
    }

    default void visit(Team team) {
    }
}
