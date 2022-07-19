package ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoldingMapper implements RowMapper<Holding> {

    public Holding mapRow(ResultSet resultSet, int i) throws SQLException {

        Holding holding = new Holding();
        holding.setId(resultSet.getLong("KatNr"));
        holding.setOrder(resultSet.getLong("KNr"));
        holding.setRoundCode(resultSet.getString("Spielrunde"));
        holding.setCode(resultSet.getString("Gruppe"));
        holding.setBookName(resultSet.getString("Kategorie"));

        return holding;
    }
}
