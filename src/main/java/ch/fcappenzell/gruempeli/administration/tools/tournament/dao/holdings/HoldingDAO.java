package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.holdings;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;

import java.util.List;

public interface HoldingDAO {

    List<Holding> getAllHoldingsByCategory(String category);

}
