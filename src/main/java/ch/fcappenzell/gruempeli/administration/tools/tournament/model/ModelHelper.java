package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;

public class ModelHelper {

    private ModelHelper() {
    }

    public static String toName(Match match) {
        Holding holding = match.getHolding();
        return match.getHolding().getRound().getBookName() + " Gr. " + holding.getCode() + "  (" + (holding.getMatches().indexOf(match) + 1) + ")";
    }
}
