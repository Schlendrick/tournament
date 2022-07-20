package ch.fcappenzell.gruempeli.administration.tools.tournament.model.team;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapperSimple implements RowMapper<Team> {

    public Team mapRow(ResultSet resultSet, int i) throws SQLException {

        Team team = new Team();
        team.setId(resultSet.getLong("MannNr"));
        team.setName(resultSet.getString("Mannschaft"));

        return team;
    }
}