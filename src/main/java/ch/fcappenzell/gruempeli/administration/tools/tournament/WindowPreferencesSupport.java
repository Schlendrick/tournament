/*
 * Created by Marco on 04.06.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament;

import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

@Component
public class WindowPreferencesSupport {

    private final String STAAGE = "stage";
    private final String DB_PATH = "Db_Path";
    public static final String PATH = "path";
    private final String WINDOW_POSITION_X = "Window_Position_X";
    private final String WINDOW_POSITION_Y = "Window_Position_Y";
    private final String WINDOW_WIDTH = "Window_Width";
    private final String WINDOW_HEIGHT = "Window_Height";
    Logger logger = LoggerFactory.getLogger(WindowPreferencesSupport.class);


    public void setPreloader(Stage stage) {
        try {
            if (Preferences.userRoot().nodeExists(STAAGE)) {
                Preferences pref = Preferences.userRoot().node(stage.titleProperty().getName());
                stage.setX(pref.getDouble(WINDOW_POSITION_X, 0));
                stage.setY(pref.getDouble(WINDOW_POSITION_Y, 0));
            }
        } catch (BackingStoreException e) {
            logger.info( "Preference node not yet present", e);
        }
    }

    public void addStage(Stage stage) {
        // Pull the saved preferences and set the stage size and start location
        String nodeName = STAAGE;
        if (StringUtils.isEmpty(nodeName)) {
            throw new RuntimeException("Preference für Stage ohne Namen kann nicht gesetzt werden");
        }

        try {
            if (Preferences.userRoot().nodeExists(nodeName)) {
                Preferences pref = Preferences.userRoot().node(stage.titleProperty().getName());
                stage.setX(pref.getDouble(WINDOW_POSITION_X, 0));
                stage.setY(pref.getDouble(WINDOW_POSITION_Y, 0));
                stage.setWidth(pref.getDouble(WINDOW_WIDTH, 0));
                stage.setHeight(pref.getDouble(WINDOW_HEIGHT, 0));
            }
        } catch (BackingStoreException e) {
            logger.info("Preference node not yet present", e);
        }

        // When the stage closes store the current size and window location.

        stage.setOnCloseRequest((final WindowEvent event) -> {
            Preferences preferences = Preferences.userRoot().node(nodeName);

            preferences.putDouble(WINDOW_POSITION_X, stage.getX());
            preferences.putDouble(WINDOW_POSITION_Y, stage.getY());
            preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
            preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
        });
    }

    public void addSingleDividerSupport(SplitPane splitPane) {
        String nodeName = splitPane.getId();
        String position = "position";

        Preferences preferences = Preferences.userRoot().node(nodeName);

        try {
            if (Preferences.userRoot().nodeExists(nodeName)) {
                splitPane.setDividerPosition(0, preferences.getDouble(position, 50));
            }
        } catch (BackingStoreException e) {
            logger.info( "Preference node not yet present", e);
        }

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> preferences.putDouble(position, newValue.doubleValue()));
    }

    public void setDbPatch(String path) {
        Preferences preferences = Preferences.userRoot().node(DB_PATH);
        if (path == null) {
            preferences.remove(PATH);
        } else {
            preferences.put(PATH, path);
        }
    }

    public String getDbPath() {
        Preferences preferences = Preferences.userRoot().node(DB_PATH);
        return preferences.get(PATH, null);
    }
}
