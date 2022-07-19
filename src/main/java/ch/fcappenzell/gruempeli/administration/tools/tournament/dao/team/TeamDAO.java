package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.team;


import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;

import java.util.List;
import java.util.Map;

public interface TeamDAO {
    Team getTeamById(Long id);

    List<Team> getAllTeams();

    Map<Long, Team> getAllTeamsMap();

    boolean deleteTeam(Team team);

    boolean updateTeam(Team team);

    boolean createTeam(Team team);
}