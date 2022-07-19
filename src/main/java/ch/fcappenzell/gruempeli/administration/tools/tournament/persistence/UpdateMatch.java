/*
 * Created by Marco on 04.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Formatter;

public class UpdateMatch  {

    private final Match match;

    private int result;

    public UpdateMatch(Match match) {
        this.match = match;
    }

    private boolean updateHoldingValues(Connection connection) {
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

        try (PreparedStatement statement = connection.prepareStatement("UPDATE T_Kategorien SET T_Kategorien.SpielDatum = ?, T_Kategorien.wtag = ?, T_Kategorien.wochentag = ? WHERE T_Kategorien.KatNr = ?")) {

            statement.setDate(1, date);
            statement.setInt(2, dayOfWeek);
            statement.setString(3, dayOfWeekName);
            statement.setLong(4, match.getHolding().getId());

            statement.executeUpdate();

        } catch (Exception e) {
            System.out.print("Fehler beim Zugriff auf die Datenbank");
            return false;
        }
        return true;
    }

    private void updateMatchValues(Connection connection) {
        Timestamp time = match.getTime() == null ? null : Timestamp.valueOf(match.getTime());
        String field = new Formatter().format("%02d", match.getField()).toString();

        try (PreparedStatement statement = connection.prepareStatement("UPDATE T_Spielplan SET T_Spielplan.ZeitDatum = ?, T_Spielplan.Spielzeit = ?, T_Spielplan.Platz = ? WHERE T_Spielplan.SpielNr = ?")) {

            statement.setTimestamp(1, time);
            statement.setTimestamp(2, time);
            statement.setString(3, field);
            statement.setLong(4, match.getId());

            result = statement.executeUpdate();

        } catch (Exception e) {
            System.out.print("Fehler beim Zugriff auf die Datenbank");
        }
    }

    public int getUpdatedRecordsCount() {
        return result;
    }
}
