/*
 * Created by Marco on 09.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.validation;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.PlayTime;

import java.time.LocalDateTime;

public class SnapTime {

    long timeIdex;
    LocalDateTime time;

    public SnapTime(int row, PlayTime time) {
        this.timeIdex = row;
        this.time = time.getStart();
    }

    public long getTimeIdex() {
        return timeIdex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnapTime snapTime = (SnapTime) o;

        return timeIdex == snapTime.timeIdex;
    }

    @Override
    public int hashCode() {
        return (int) (timeIdex ^ (timeIdex >>> 32));
    }
}
