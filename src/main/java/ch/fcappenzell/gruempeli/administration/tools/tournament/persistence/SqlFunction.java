package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;/*
 * Created by Marco on 04.06.2018.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlFunction<R> {

    R apply(ResultSet resultSet) throws SQLException;

}
