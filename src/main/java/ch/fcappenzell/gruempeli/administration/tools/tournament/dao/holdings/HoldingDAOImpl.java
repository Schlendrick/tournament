package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.holdings;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.HoldingMapper;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class HoldingDAOImpl implements HoldingDAO{
    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_HOLDINGS = "select KatNr, KNr, Spielrunde, Gruppe, Kategorie FROM T_Kategorien WHERE Kat = ? ORDER BY KatNr";


    @Autowired
    public HoldingDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Holding> getAllHoldingsByCategory(String category) {
        return jdbcTemplate.query(SQL_FIND_HOLDINGS, new Object[] { category }, new HoldingMapper());
    }

}