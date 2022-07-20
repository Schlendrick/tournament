package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.match;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Formatter;
import java.util.List;

@Component
public class MatchDAOImpl implements MatchDAO{
    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_MATCHES = "select SpielNr, Reihenfolge, ZeitDatum, Spielzeit, Platz, MannNrA, MannNrB, ResA, ResB, Elfmeter from T_Spielplan where KatNr = ?  ORDER BY SpielNr";
    private final String SQL_CLEAR_ALL_MATCHES_IN_SCHEDULE = "update T_Spielplan set ZeitDatum = Null, Spielzeit = Null, Platz = \"0\"";
    private final String SQL_UPDATE_MATCH_IN_SCHEDULE = "update T_Spielplan set ZeitDatum = ?, Spielzeit = ?, Platz = ? where SpielNr = ?";
    private final String SQL_UPDATE_HOLDING_VALUES = "update T_Kategorien set SpielDatum = ?, wtag = ?, wochentag = ? where KatNr = ?";

    @Autowired
    public MatchDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Match> getMatchesByCategory(Long id)  {
        return jdbcTemplate.query(SQL_FIND_MATCHES, new Object[] { id }, new MatchMapper());
    }

    @Override
    public boolean clearAllMatchesInSchedule() {
        return jdbcTemplate.update(SQL_CLEAR_ALL_MATCHES_IN_SCHEDULE) > 0;
    }

    @Override
    public boolean updateMatchInSchedule(Match match) {
        Timestamp time = match.getTime() == null ? null : Timestamp.valueOf(match.getTime());
        String field = new Formatter().format("%02d", match.getField()).toString();
        return jdbcTemplate.update(SQL_UPDATE_MATCH_IN_SCHEDULE, time, time, field,
                match.getId()) > 0;
    }

    public boolean updateHoldingValues(Match match) {
        if(match.getTime() == null){
            return true;
        }
        Date date = match.getTime() == null ? null : Date.valueOf(match.getTime().toLocalDate());
        int dayOfWeek = match.getTime().getDayOfWeek().getValue() - 1; //vb value
        String dayOfWeekName;
        switch (dayOfWeek) {
            case 0:
                dayOfWeekName = "Mo.";
                break;
            case 1:
                dayOfWeekName = "Di.";
                break;
            case 2:
                dayOfWeekName = "Mi.";
                break;
            case 3:
                dayOfWeekName = "Do.";
                break;
            case 4:
                dayOfWeekName = "Fr.";
                break;
            case 5:
                dayOfWeekName = "Sa.";
                break;
            default:
                dayOfWeekName = "So.";
                break;
        }
        return jdbcTemplate.update(SQL_UPDATE_HOLDING_VALUES, date, dayOfWeek, dayOfWeekName,
                match.getHolding().getId()) > 0;
    }

}
