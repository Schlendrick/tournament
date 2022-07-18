package ch.fcappenzell.gruempeli.administration.tools.tournament.model.team;

import java.util.Date;

public class TeamPlayer {
    private String title;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Boolean clubPlayer;

    public TeamPlayer() {
    }

    public TeamPlayer(String title, String firstName, String lastName, Date birthday, Boolean clubPlayer) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.clubPlayer = clubPlayer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getClubPlayer() {
        return clubPlayer;
    }

    public void setClubPlayer(Boolean clubPlayer) {
        this.clubPlayer = clubPlayer;
    }

    @Override
    public String toString() {
        return "TeamPlayer{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", clubPlayer=" + clubPlayer +
                '}';
    }
}
