/*
 * Created by Marco on 04.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import java.sql.ResultSet;
import java.sql.Statement;

public class Query<R> implements DbStatementExecutable {

    private final String queryString;
    private final SqlFunction<R> consumer;

    private R result;

    public Query(String queryString, SqlFunction<R> queryFunction) {
        this.queryString = queryString;
        this.consumer = queryFunction;
    }

    public R getResult() {
        return result;
    }

    @Override
    public void accept(Statement statement) {
        try (ResultSet resultSet = statement.executeQuery(queryString)) {
            result = consumer.apply(resultSet);

        } catch (Exception e) {
            logger.error( "Fehler beim Zugriff auf die Datenbank", e);
        }
    }
}
