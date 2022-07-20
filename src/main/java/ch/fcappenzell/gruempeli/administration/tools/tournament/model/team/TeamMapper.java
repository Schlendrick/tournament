package ch.fcappenzell.gruempeli.administration.tools.tournament.model.team;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamMapper implements RowMapper<Team> {

    public Team mapRow(ResultSet resultSet, int i) throws SQLException {

        Team team = new Team();
        team.setId(resultSet.getLong("MNr"));
        team.setName(resultSet.getString("Mannschaft"));
        team.setWishes(resultSet.getString("Wunsch"));
        team.setDisqualified(resultSet.getBoolean("Disqualifikation"));
        team.setBlindTeam(resultSet.getBoolean("Blinde_Mannschaft"));
        team.setPaid(resultSet.getBoolean("BetragErhalten"));
        team.setCashPayment(resultSet.getBoolean("Barzahlung"));
        //team.setDateRegistration(resultSet.getDate("DatumAnmeldung").toLocalDate());
        //team.setDateAmountReceived(resultSet.getDate("DatumBetragErhalten").toLocalDate());

        TeamCaptain captain = new TeamCaptain();
        captain.setTitle(resultSet.getString("Anrede"));
        captain.setFirstName(resultSet.getString("Vorname"));
        captain.setLastName(resultSet.getString("Namen"));
        captain.setStreet(resultSet.getString("Strasse"));
        captain.setPlzPlace(resultSet.getString("Plz/Ort"));
        captain.setEmail(resultSet.getString("EMail"));
        captain.setPhone(resultSet.getString("Telefon"));
        team.setCaptain(captain);

        // max 10 players possible
        List<TeamPlayer> teamPlayerList = new ArrayList<TeamPlayer>();
        for (Integer j = 1; j < 11; j++) {
            String firstName = resultSet.getString("SP" + j.toString() +  "Vor");
            if (firstName != "") {
                TeamPlayer player = new TeamPlayer();
                player.setTitle(""); // Todo no data provided
                player.setFirstName(resultSet.getString("SP" + j.toString() +  "Vor"));
                player.setLastName(resultSet.getString("SP" + j.toString() +  "Nach"));
                player.setClubPlayer(resultSet.getString("SP" + j.toString() +  "FCA") == "ja");
                String date = resultSet.getString("SP" + j.toString() +  "Jahr");
                teamPlayerList.add(player);
                // Todo
                /*
                player.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(date););
                List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
                knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'"));
                knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
                knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
                knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
                for (SimpleDateFormat pattern : knownPatterns) {
                    try {
                        // Take a try
                        return new Date(pattern.parse(candidate).getTime());

                    } catch (ParseException pe) {
                        // Loop on
                    }
                }
                System.err.println("No known Date format found: " + candidate);
                return null;

                 */
            }
        }
        team.setPlayers(teamPlayerList);

        return team;
    }
}