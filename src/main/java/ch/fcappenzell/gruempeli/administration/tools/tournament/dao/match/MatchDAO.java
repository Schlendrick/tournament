package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;

import java.util.List;

public interface MatchDAO {

    List<Match> getMatchesByCategory(Long id);

    // TODO UpdateCleanMatchScheduling  "UPDATE T_Spielplan SET T_Spielplan.ZeitDatum = Null, T_Spielplan.Spielzeit = Null, T_Spielplan.Platz = \"0\"");

}
