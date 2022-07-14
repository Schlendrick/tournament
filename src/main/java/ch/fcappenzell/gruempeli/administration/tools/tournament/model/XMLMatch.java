package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLMatch{
    public String dateTime;
    public String field;
    public String category;
    public String group;
    public String game;
    public String homeTeam;
    public String visitorTeam;
    public long homeScore;
    public long visitorScore;
    public boolean penalty;
}
