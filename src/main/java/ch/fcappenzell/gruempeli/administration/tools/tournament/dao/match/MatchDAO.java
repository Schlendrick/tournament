package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;

import java.util.List;

public interface MatchDAO {

    List<Match> getMatchesByCategory(Long id);

}
