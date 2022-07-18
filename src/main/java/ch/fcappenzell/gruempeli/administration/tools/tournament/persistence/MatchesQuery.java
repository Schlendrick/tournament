/*
 * Created by Marco on 10.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.*;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class MatchesQuery extends ResultDbStatementExecutable<List<Match>> {



    List<Color> colors = FXCollections.observableArrayList( //todo color picker
            Color.LIGHTBLUE,
            Color.LIGHTCORAL,
            Color.LIGHTCYAN,
            Color.LIGHTGOLDENRODYELLOW,
            Color.LIGHTPINK,
            Color.LIGHTSEAGREEN,
            Color.LIGHTSKYBLUE,
            Color.LIGHTSTEELBLUE,
            Color.LIGHTYELLOW,
            Color.BISQUE,
            Color.MOCCASIN,
            Color.TURQUOISE,
            Color.CHARTREUSE
    );


    @Override
    protected List<Match> doQueries(Statement statement) throws SQLException {
        List<Arrangement> arrangements = queryArrangements(statement);

        List<Holding> holdings = new ArrayList<>();
        for (Arrangement arrangement : arrangements) {
            holdings.addAll(queryHoldings(arrangement, statement));
        }

        HashMap<Long, Team> teamMap = queryTeams(statement);

        List<Match> matches = new ArrayList<>();
        for (Holding holding : holdings) {
            matches.addAll(queryMatches(teamMap, holding, statement));
        }


        return matches;
    }



    private List<Arrangement> queryArrangements(Statement statement) throws SQLException {
        List<Arrangement> arrangements = new ArrayList<>();
        logger.info("queryArrangements");
        try (ResultSet resultSet = statement.executeQuery("SELECT Autnr, Kat, Kommentar FROM T_Liste_Kategorien")) {

            while (resultSet.next()) {

                Category category = new Category(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                //logger.info("New Category created Name:{} , Code:{}", category.getName(), category.getCode());
                category.setColor(colors.get(0).toString());
                colors.remove(0);
                Arrangement arrangement = new Arrangement();
                arrangement.setCategory(category);
                arrangements.add(arrangement);
            }
        }catch(Error e)
        {
            logger.error("Matches querry not received", e);
        }

        return arrangements;
    }

    private List<Holding> queryHoldings(Arrangement arrangement, Statement statement) throws SQLException {
        ArrayList<Holding> holdings = new ArrayList<>();
        List<Round> rounds = new ArrayList<>();
        HashMap<String, Round> roundMap = new HashMap<>();
        //logger.info("queryHoldings");
        try (ResultSet resultSet = statement.executeQuery("SELECT KatNr, KNr, Spielrunde, Gruppe, Kategorie FROM T_Kategorien WHERE Kat = \"" + arrangement.getCategory().getName() + "\" ORDER BY KatNr")) {

            while (resultSet.next()) {

                String roundCode = resultSet.getString(3);
                //logger.info(roundCode);

                if (!roundMap.containsKey(roundCode)) {
                    Round round = new Round();
                    round.setArrangement(arrangement);
                    round.setCode(roundCode);
                    round.setBookName(roundCode);
                    roundMap.put(roundCode, round);
                    rounds.add(round);
                }

                Round round = roundMap.get(roundCode);
                Holding holding = new Holding();
                holding.setRound(round);
                holding.setId(resultSet.getLong(1));
                holding.setOrder(resultSet.getLong(2));
                holding.setCode(resultSet.getString(4));
                holding.setBookName(resultSet.getString(5));
                round.getHoldings().add(holding);
                holdings.add(holding);
            }
        }

        arrangement.setRounds(rounds);
        return holdings;
    }

    private List<Match> queryMatches(HashMap<Long, Team> teamMap, Holding holding, Statement statement) throws SQLException {
        List<Match> matches = new ArrayList<>();
        TreeSet<Team> teamsInHolding = new TreeSet<>();
        //logger.info("queryMatches 2");
        try (ResultSet resultSet = statement.executeQuery("SELECT SpielNr, Reihenfolge, ZeitDatum, Spielzeit, Platz, MannNrA, MannNrB, ResA, ResB, Elfmeter FROM T_Spielplan WHERE KatNr = \"" + holding.getId() + "\" ORDER BY SpielNr")) {

            while (resultSet.next()) {
                Match match = new Match();
                match.setHolding(holding);
                match.setId(resultSet.getLong(1));
                match.setOrder(resultSet.getLong(2));
                match.setField(resultSet.getInt(5));

                Date date = resultSet.getDate(3);
                Time time = resultSet.getTime(4);
                match.setTime(date == null || time == null ? null : LocalDateTime.of(date.toLocalDate(), time.toLocalTime()));

                Assert.isTrue(teamMap.containsKey(resultSet.getLong(6)), "Heim-Team nicht gefunden");
                Team homeTeam = teamMap.get(resultSet.getLong(6));
                match.setHomeTeam(homeTeam);
                teamsInHolding.add(homeTeam);

                Assert.isTrue(teamMap.containsKey(resultSet.getLong(7)), "Gast-Team nicht gefunden");
                Team visitorTeam = teamMap.get(resultSet.getLong(7));
                match.setVisitorTeam(visitorTeam);
                teamsInHolding.add(visitorTeam);

                match.setHomeScore(resultSet.getLong(8));
                match.setVisitorScore(resultSet.getLong(9));
                match.setPenalty(resultSet.getBoolean(10));


                matches.add(match);
                //logger.info("Match created Home:{} vs Gast:{}", match.getHomeTeam(), match.getVisitorTeam());
                holding.getMatches().add(match);
            }
        }

        int i = 1;
        while (!teamsInHolding.isEmpty()){
            teamsInHolding.pollFirst().setTeamNr(i++);
        }

        return matches;
    }

    private HashMap<Long, Team> queryTeams(Statement statement) throws SQLException {
        HashMap<Long, Team> teamMap = new HashMap<>();

        String sqlString =
                "SELECT T_Mannschaften.MannNr, T_Kategorien.KatNr, T_Mannschaften.Mannschaft, T_Adressen.DatumBetragErhalten, T_Adressen.Wunsch, T_Adressen.AnzDamen, T_Adressen.DatumAnmeldung, T_Adressen.MNr\n" +
                        "FROM T_Kategorien INNER JOIN (T_Adressen RIGHT JOIN T_Mannschaften ON T_Adressen.MNr = T_Mannschaften.MNr) ON T_Kategorien.KatNr = T_Mannschaften.KatNr";
        try (ResultSet resultSet = statement.executeQuery(sqlString)) {
            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getLong(1));
                team.setName(resultSet.getString(3));

                Date paid = resultSet.getDate(4);
                //team.setPaid(paid == null ? null : paid.toLocalDate());
                //team.setNote(resultSet.getString(5));
                //team.setForce(resultSet.getInt(6));

                Date entry = resultSet.getDate(7);
                team.setEntry(entry == null ? null : entry.toLocalDate());

                teamMap.put(team.getId(), team);
            }
        }

        return teamMap;
    }
}
