package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.feeedback;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;



public class DefaultMessageFeedbackProvider extends TextField implements MessageFeedbackProvider {

    @Override
    public void setMessage(String message) {
        setText(message);
        if (message == null || message.equalsIgnoreCase("") || message.equalsIgnoreCase("ok")) {
            setColor(Color.WHITE);
        } else {
            setColor(Color.YELLOW);
        }
    }

    private void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), Insets.EMPTY)));
    }
}