package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.tournament;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament.Tournament;

import java.util.List;

public interface TournamentDAO {
    Tournament getTournamentById(Long id);

    List<Tournament> getAllTournaments();

    boolean deleteTournament(Tournament Tournament);

    boolean updateTournament(Tournament Tournament);

    boolean createTournament(Tournament Tournament);
}
