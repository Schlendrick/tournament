/*
 * Created by Marco on 03.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;


import ch.fcappenzell.gruempeli.administration.tools.tournament.model.*;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Component
public class DbHandler {

    private Connection connection;
    private Tournament tournament;
    private final Set<ConnectionListener> listeners = new HashSet<>();
    Logger logger = LoggerFactory.getLogger(DbHandler.class);


    public void connect(String dbPath) {
        tournament = null;

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

        } catch (ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {
            String dbURL = "jdbc:ucanaccess://" + dbPath;
            connection = DriverManager.getConnection(dbURL);
            listeners.forEach(ConnectionListener::connected);

        } catch (SQLException sqlex) {
            logger.error( "Verbindung zur Datenbank war nicht erfolgreich", sqlex);
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error( "Fehler beim Beenden der DB-Verbindung", e);
            }
        }
    }

    public void addListener(ConnectionListener listener){
        listeners.add(listener);
    }

    public boolean isRunning() {
        return connection != null;
    }

    public Tournament getTournamentQuery() {
        if (connection == null) {
            return new Tournament();
        }

        String queryVarsSqlString = "SELECT " +
                "T_Variablen.Titel, " +
                "T_Variablen.StartDatum, " +
                "T_Variablen.EndDatum, " +
                "T_Variablen.Dauer_Wechsel, " +
                "T_Variablen.StartFr, " +
                "T_Variablen.Spieldauer_effektivFR, " +
                "T_Variablen.StartSaSo, " +
                "T_Variablen.Spieldauer_effektiv " +
                "FROM T_Variablen";

        Query<Tournament> tournamentQuery = new Query<>(
                queryVarsSqlString,
                resultSet -> {
                    Tournament tournament = new Tournament();
                    tournament.setFields(5);

                    if (!resultSet.next()) {
                        return tournament;
                    }

                    tournament.setTitle(resultSet.getString(1));

                    LocalDate start = resultSet.getDate(2).toLocalDate();
                    LocalDate end = resultSet.getDate(3).toLocalDate();
                    Duration time = Duration.ofMinutes(resultSet.getTime(4).toLocalTime().getMinute());

                    LocalDate day = start;
                    while (day.compareTo(end) <= 0) {
                        TournamentDay tDay = new TournamentDay();
                        tDay.setDay(day);
                        tDay.setBreakTime(time);
                        tournament.getTournamentDays().add(tDay);

                        if (day.equals(start)) {
                            tDay.setStartTime(resultSet.getTime(5).toLocalTime());
                            tDay.setMatchTime(Duration.ofMinutes(resultSet.getTime(6).toLocalTime().getMinute()));
                        } else {
                            tDay.setStartTime(resultSet.getTime(7).toLocalTime());
                            tDay.setMatchTime(Duration.ofMinutes(resultSet.getTime(8).toLocalTime().getMinute()));
                        }

                        day = day.plusDays(1);
                    }

                    return tournament;
                }
        );
        execute(tournamentQuery);

        return tournamentQuery.getResult();
    }

    public Tournament getTournament() {
        if (tournament == null) {
            tournament = getTournamentQuery();
        }
        return tournament;
    }

    public void clearPlan() {
        UpdateCleanMatchScheduling update = new UpdateCleanMatchScheduling();
        execute(update);
    }

    public void clearMatches(List<Match> matches) {
        matches.forEach(m -> {
            m.setField(0);
            m.setTime(null);
        });

        updateMatches(matches);
    }


    public int updateMatches(List<Match> matches) {
        UpdateMatch[] updates = matches.stream()
                .map(UpdateMatch::new)
                .toArray(UpdateMatch[]::new);

        execute(updates);

        return Arrays.stream(updates).mapToInt(UpdateMatch::getUpdatedRecordsCount).sum();
    }


    void execute(DbStatementExecutable... executables) {
        DbStatementExecutable.DbConnectionWrapper[] wrappedExecutables = Arrays.
                stream(executables)
                .map(DbStatementExecutable.DbConnectionWrapper::new)
                .toArray(DbStatementExecutable.DbConnectionWrapper[]::new);

        execute(wrappedExecutables);
    }

    void execute(DbConnectionExecutable... executables) {

        if (!isRunning()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText("Fehler bei DB-Verbindung");
            alert.setContentText("Es besteht keine Verbindung zu der Datenbank, deshalb kann die Aktion nicht ausgeführt werden.");

            alert.showAndWait();
            return;
        }

        Arrays.stream(executables).forEach(executable -> executable.accept(connection));
    }

}
