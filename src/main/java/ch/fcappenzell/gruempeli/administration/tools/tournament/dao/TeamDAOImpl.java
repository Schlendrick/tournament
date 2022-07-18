package ch.fcappenzell.gruempeli.administration.tools.tournament.dao;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class TeamDAOImpl implements TeamDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_TEAM = "select * from T_Adressen where MNr = ?";
    private final String SQL_DELETE_TEAM = "delete from T_Adressen where MNr = ?";
    private final String SQL_UPDATE_TEAM = "update T_Adressen set Mannschaft = ?, Wunsch = ?, Disqualifikation = ?, Blinde_Mannschaft = ?, BetragErhalten = ?, Barzahlung = ?, DatumAnmeldung = ?, DatumBetragErhalten = ?, Anrede = ?, Vorname = ?, Namen = ?, Strasse = ?, PlzOrt = ?, EMail = ?, Telefon = ? where MNr = ?";
    private final String SQL_GET_ALL = "select * from T_Adressen";
    private final String SQL_INSERT_TEAM = "insert into T_Adressen(Mannschaft, Wunsch, Disqualifikation, Blinde_Mannschaft, BetragErhalten, Barzahlung, DatumAnmeldung, DatumBetragErhalten, Anrede, Vorname, Namen, Strasse, PlzOrt, EMail, Telefon) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    @Autowired
    public TeamDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Team getTeamById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_TEAM, new Object[] { id }, new TeamMapper());
    }

    public List<Team> getAllTeams() {
        return jdbcTemplate.query(SQL_GET_ALL, new TeamMapper());
    }

    public boolean deleteTeam(Team team) {
        return jdbcTemplate.update(SQL_DELETE_TEAM, team.getId()) > 0;
    }

    public boolean updateTeam(Team team) {
        return jdbcTemplate.update(SQL_UPDATE_TEAM, team.getName(), team.getWishes(), team.getDisqualified(), team.getBlindTeam(), team.getPaid(), team.getCashPayment(), team.getDateRegistration(), team.getDateAmountReceived(), team.getCaptain().getTitle(), team.getCaptain().getFirstName(), team.getCaptain().getLastName(), team.getCaptain().getStreet(), team.getCaptain().getPlzPlace(), team.getCaptain().getEmail(), team.getCaptain().getPhone(),
                team.getId()) > 0;
    }

    public boolean createTeam(Team team) {
        return jdbcTemplate.update(SQL_INSERT_TEAM, team.getName(), team.getWishes(), team.getDisqualified(), team.getBlindTeam(), team.getPaid(), team.getCashPayment(), team.getDateRegistration(), team.getDateAmountReceived(), team.getCaptain().getTitle(), team.getCaptain().getFirstName(), team.getCaptain().getLastName(), team.getCaptain().getStreet(), team.getCaptain().getPlzPlace(), team.getCaptain().getEmail(), team.getCaptain().getPhone()
                ) > 0;
    }

}
