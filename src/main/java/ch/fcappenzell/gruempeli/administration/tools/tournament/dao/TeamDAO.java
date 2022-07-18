package ch.fcappenzell.gruempeli.administration.tools.tournament.dao;


import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;

import java.util.List;

public interface TeamDAO {
    Team getTeamById(Long id);

    List<Team> getAllTeams();

    boolean deleteTeam(Team team);

    boolean updateTeam(Team team);

    boolean createTeam(Team team);
}