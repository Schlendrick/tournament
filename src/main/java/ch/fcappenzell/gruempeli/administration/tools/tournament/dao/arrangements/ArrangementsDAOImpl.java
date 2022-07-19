package ch.fcappenzell.gruempeli.administration.tools.tournament.dao.arrangements;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements.Arrangement;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements.ArrangementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ArrangementsDAOImpl implements ArrangementsDAO{

    JdbcTemplate jdbcTemplate;
    private final String SQL_GET_ALL = "select Autnr, Kat, Kommentar from T_Liste_Kategorien";

    @Autowired
    public ArrangementsDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Arrangement> getAllArrangements() {
        return jdbcTemplate.query(SQL_GET_ALL, new ArrangementMapper());
    }
}
