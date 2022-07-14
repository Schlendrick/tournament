/*
 * Created by Marco on 05.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class ResultDbStatementExecutable<R> implements DbStatementExecutable {

    private R result;

    public R getResult() {
        return result;
    }

    @Override
    public void accept(Statement statement) {
        try {
            result = doQueries(statement);
        } catch (SQLException ex) {
            logger.error( "Matches konnten nicht ermittelt werden.", ex);
        }
    }

    protected abstract R doQueries(Statement statement) throws SQLException;
}
