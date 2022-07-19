package ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TournamentMapper implements RowMapper<Tournament> {

    public Tournament mapRow(ResultSet resultSet, int i) throws SQLException {

        Tournament tournament = new Tournament();
        //TODO tournament.setId(resultSet.getLong("id"));
        tournament.setTitle(resultSet.getString("Titel"));
        //TODO tournament.setFields(resultSet.getInt("AnzSpielfelder"));
        tournament.setFields(5);
        // TODO tournament.setMinBreakMatchesRound(resultSet.getInt("MinBreak"));
        tournament.setMinBreakMatchesRound(2);

        List<TournamentDay> tournamentDaysList = new ArrayList<TournamentDay>();
        LocalDate start = resultSet.getDate("StartDatum").toLocalDate();
        LocalDate end = resultSet.getDate("EndDatum").toLocalDate();
        Duration breakTime = Duration.ofMinutes(resultSet.getTime("Dauer_Wechsel").toLocalTime().getMinute());
        LocalDate day = start;
        while (day.compareTo(end) <= 0) {
            TournamentDay tournamentDay = new TournamentDay();
            tournamentDay.setDay(day);
            tournamentDay.setBreakTime(breakTime);

            if (day.equals(start)) {
                tournamentDay.setStartTime(resultSet.getTime("StartFr").toLocalTime());
                tournamentDay.setMatchTime(Duration.ofMinutes(resultSet.getTime("Spieldauer_effektivFR").toLocalTime().getMinute()));
            } else {
                tournamentDay.setStartTime(resultSet.getTime("StartSaSo").toLocalTime());
                tournamentDay.setMatchTime(Duration.ofMinutes(resultSet.getTime("Spieldauer_effektiv").toLocalTime().getMinute()));
            }
            tournamentDaysList.add(tournamentDay);
            day = day.plusDays(1);
        }
        tournament.setTournamentDays(tournamentDaysList);
        return tournament;
    }
}
