/*
 * Created by Marco on 04.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public interface DbStatementExecutable extends Consumer<Statement> {

    Logger logger = LoggerFactory.getLogger(DbStatementExecutable.class);

    class DbConnectionWrapper implements DbConnectionExecutable {

        private final DbStatementExecutable executable;

        public DbConnectionWrapper(DbStatementExecutable executable) {
            this.executable = executable;
        }

        @Override
        public void accept(Connection connection) {
            try (Statement statement = connection.createStatement()) {
                executable.accept(statement);

            } catch (SQLException e) {
                logger.error( "Datenbank Operation konnte nicht erfolgreic durchgeführt werden", e);
            }
        }
    }
}
