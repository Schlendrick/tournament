/*
 * Created by Marco on 07.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCleanMatchScheduling implements DbStatementExecutable {
    @Override
    public void accept(Statement statement) {
        try {
            statement.executeUpdate("UPDATE T_Spielplan SET T_Spielplan.ZeitDatum = Null, T_Spielplan.Spielzeit = Null, T_Spielplan.Platz = \"0\"");

        } catch (SQLException e) {
            logger.error( "Datenbank Operation konnte nicht erfolgreic durchgeführt werden", e);
        }

    }
}
