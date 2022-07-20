package ch.fcappenzell.gruempeli.administration.tools.tournament.service;

import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.arrangements.ArrangementsDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.holdings.HoldingDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match.MatchDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.team.TeamDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements.Arrangement;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class MatchServiceImpl implements MatchService {
    private ArrangementsDAO arrangementsDAO;
    private MatchDAO matchDAO;
    private HoldingDAO holdingDAO;
    private TeamDAO teamDAO;

    @Autowired
    public MatchServiceImpl(ArrangementsDAO arrangementsDAO, MatchDAO matchDAO, HoldingDAO holdingDAO, TeamDAO teamDAO) {
        this.arrangementsDAO = arrangementsDAO;
        this.matchDAO = matchDAO;
        this.holdingDAO = holdingDAO;
        this.teamDAO = teamDAO;
    }

    @Override
    public List<Match> getAllMatches() {

        List<Arrangement> arrangements = arrangementsDAO.getAllArrangements();
        List<Holding> holdings = new ArrayList<>();
        arrangements.forEach(arrangement -> {
            holdings.addAll(this.getHoldings(arrangement, arrangement.getCategory().getName()));
        });

        List<Match> matches = new ArrayList<>();
        holdings.forEach(holding -> {
            matches.addAll(getAllMatchesFromHolding(holding));
        });

        return matches;
    }

    @Override
    public void clearAllMatchesInSchedule() {
        matchDAO.clearAllMatchesInSchedule();
    }

    @Override
    public void clearMatchInSchedule(Match match) {
        match.setField(0);
        match.setTime(null);
        matchDAO.updateMatchInSchedule(match);
    }


    @Override
    public void updateMatches(List<Match> matches) {
        matches.forEach(match -> {
            matchDAO.updateMatchInSchedule(match);
            matchDAO.updateHoldingValues(match);
        });

    }


    private List<Holding> getHoldings(Arrangement arrangement, String category) {
        List<Holding> holdings = holdingDAO.getAllHoldingsByCategory(category);
        List<Round> rounds = new ArrayList<>();
        HashMap<String, Round> roundMap = new HashMap<>();

        holdings.forEach(holding -> {

            String roundCode = holding.getRoundCode();

            if (!roundMap.containsKey(roundCode)) {
                Round round = new Round();
                round.setArrangement(arrangement);
                round.setCode(roundCode);
                round.setBookName(roundCode);
                roundMap.put(roundCode, round);
                rounds.add(round);
            }

            holding.setRound(roundMap.get(roundCode));

        });

        return holdings;
    }

    private List<Match> getAllMatchesFromHolding(Holding holding){
        Map<Long, Team> teamMap = teamDAO.getAllTeamsMap();
        List<Match> matches = matchDAO.getMatchesByCategory(holding.getId());

        matches.forEach(match -> {

            match.setHolding(holding);
            match.setHomeTeam(this.assignTeamToMatch(teamMap, match.getHomeTeamId()));
            match.setVisitorTeam(this.assignTeamToMatch(teamMap, match.getVisitorTeamId()));

        });

        return matches;
    }

    private Team assignTeamToMatch(Map<Long, Team> teamMap, Long teamId)
    {
        if (!teamMap.containsKey(teamId)){
            System.out.println("Error: Team with this id does not exist: " + teamId);
        }

        return teamMap.get(teamId);
    }



}
