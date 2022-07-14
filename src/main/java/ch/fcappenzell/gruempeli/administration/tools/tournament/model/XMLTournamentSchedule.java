package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLTournamentSchedule{
    public XMLMatch[] matches;
}
