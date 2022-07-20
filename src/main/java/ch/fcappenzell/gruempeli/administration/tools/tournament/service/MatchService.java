package ch.fcappenzell.gruempeli.administration.tools.tournament.service;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;

import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();

    void clearAllMatchesInSchedule();

    void clearMatchInSchedule(Match match);

    void updateMatches(List<Match> matches);




}
