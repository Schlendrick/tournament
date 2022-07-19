package ch.fcappenzell.gruempeli.administration.tools.tournament.model.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;

public class MatchMapper implements RowMapper<Match> {

    public Match mapRow(ResultSet resultSet, int i) throws SQLException {

        Match match = new Match();

        match.setId(resultSet.getLong("SpielNr"));
        match.setOrder(resultSet.getLong("Reihenfolge"));
        Date date = resultSet.getDate("ZeitDatum");
        Time time = resultSet.getTime("Spielzeit");
        match.setTime(date == null || time == null ? null : LocalDateTime.of(date.toLocalDate(), time.toLocalTime()));
        match.setField(resultSet.getInt("Platz"));
        match.setHomeTeamId(resultSet.getLong("MannNrA"));
        match.setVisitorTeamId(resultSet.getLong("MannNrB"));
        match.setHomeScore(resultSet.getLong("ResA"));
        match.setVisitorScore(resultSet.getLong("ResB"));
        match.setPenalty(resultSet.getBoolean("Elfmeter"));

        return match;
    }
}