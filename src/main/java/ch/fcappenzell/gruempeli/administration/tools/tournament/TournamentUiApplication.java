package ch.fcappenzell.gruempeli.administration.tools.tournament;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TournamentUiApplication {

    public static void main(final String[] args) {
        Application.launch(ChartApplication.class, args);
    }

}
