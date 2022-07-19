package ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements;

import javafx.scene.paint.Color;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrangementMapper implements RowMapper<Arrangement> {

    public Arrangement mapRow(ResultSet resultSet, int i) throws SQLException {

        Category category = new Category();
        category.setCode(resultSet.getString("Autnr"));
        category.setName(resultSet.getString("Kat"));
        category.setDescription(resultSet.getString("Kommentar"));

        Arrangement arrangement = new Arrangement();
        arrangement.setCategory(category);

        return arrangement;
    }
}