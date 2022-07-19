package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.HoldingMapper;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class MatchDAOImpl implements MatchDAO{
    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_MATCHES = "select SpielNr, Reihenfolge, ZeitDatum, Spielzeit, Platz, MannNrA, MannNrB, ResA, ResB, Elfmeter from T_Spielplan where KatNr = ?  ORDER BY SpielNr";

    @Autowired
    public MatchDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Match> getMatchesByCategory(Long id)  {
        return jdbcTemplate.query(SQL_FIND_MATCHES, new Object[] { id }, new MatchMapper());
    }

}
