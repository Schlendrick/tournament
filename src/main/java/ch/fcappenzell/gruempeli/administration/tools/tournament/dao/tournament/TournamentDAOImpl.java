package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.tournament;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament.Tournament;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.tournament.TournamentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class TournamentDAOImpl implements TournamentDAO{

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_TOURNAMENT = "select * from T_Variablen where id = ?";
    private final String SQL_DELETE_TOURNAMENT = "delete from T_Variablen where id = ?";
    private final String SQL_UPDATE_TOURNAMENT = "update T_Variablen set Titel = ?, AnzSpielfelder = ?, MinBreak = ? where id = ?";
    private final String SQL_GET_ALL = "select * from T_Variablen";
    private final String SQL_INSERT_TOURNAMENT = "insert into T_Variablen(id, Titel, AnzSpielfelder, MinBreak) values(?,?,?,?)";

    @Autowired
    public TournamentDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Tournament getTournamentById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_TOURNAMENT, new Object[] { id }, new TournamentMapper());
    }

    public List<Tournament> getAllTournaments() {
        return jdbcTemplate.query(SQL_GET_ALL, new TournamentMapper());
    }

    public boolean deleteTournament(Tournament tournament) {
        return jdbcTemplate.update(SQL_DELETE_TOURNAMENT, tournament.getId()) > 0;
    }

    public boolean updateTournament(Tournament tournament) {
        return jdbcTemplate.update(SQL_UPDATE_TOURNAMENT, tournament.getTitle(), tournament.getFields(), tournament.getMinBreakMatchesRound(), tournament.getId()) > 0;
    }

    public boolean createTournament(Tournament tournament) {
        return jdbcTemplate.update(SQL_INSERT_TOURNAMENT, tournament.getId(), tournament.getTitle(), tournament.getFields(), tournament.getMinBreakMatchesRound()) > 0;
    }
}
