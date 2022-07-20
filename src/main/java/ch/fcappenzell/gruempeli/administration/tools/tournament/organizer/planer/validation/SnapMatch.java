/*
 * Created by Marco on 09.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;

public class SnapMatch {

    long matchId;
    String catgegoryId;
    long roundId;
    long holdingId;
    long teamAId;
    long teamBId;
    String messageString;

    public SnapMatch(Match match) {
        matchId = match.getId();
        teamAId = match.getHomeTeam().getId();
        teamBId = match.getVisitorTeam().getId();

        Holding holding = match.getHolding();
        holdingId = holding.getId();

        Round round = holding.getRound();
        roundId = round.getIndex();

        catgegoryId = round.getArrangement().getCategory().getCode();

        messageString = match.toName();
    }

    public long getMatchId() {
        return matchId;
    }

    public long getTeamAId() {
        return teamAId;
    }

    public long getTeamBId() {
        return teamBId;
    }

    public long getHoldingId() {
        return holdingId;
    }

    public long getRoundId() {
        return roundId;
    }

    public String getCatgegoryId() {
        return catgegoryId;
    }

    public int timeOrderCompareTo(SnapMatch o) { //negative if o < this
        if (!o.getCatgegoryId().equalsIgnoreCase(getCatgegoryId())) {
            return 0;
        }

        int roundCompare = Long.compare(o.getRoundId(), getRoundId());
        if (roundCompare != 0) {
            return roundCompare;
        }

        if (o.getHoldingId() != getHoldingId()) {
            return 0;
        }

        return Long.compare(o.getMatchId(), getMatchId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnapMatch snapMatch = (SnapMatch) o;

        return matchId == snapMatch.matchId;
    }

    @Override
    public int hashCode() {
        return (int) (matchId ^ (matchId >>> 32));
    }

    public String getMessageString() {
        return messageString;
    }
}
