package ch.fcappenzell.gruempeli.administration.tools.tournament.service.file;

import ch.fcappenzell.gruempeli.administration.tools.tournament.config.AppConfig;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Autowired
    private AppConfig appConfig;

    Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    public void handleFileUpload(File file) {

        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect(appConfig.getFTP_HOST());

            if (con.login(appConfig.getFTP_USER(), appConfig.getFTP_PASSWORD())) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                con.storeFile(file.getName(), new FileInputStream(file));
                con.logout();
                con.disconnect();
                logger.info("You successfully uploaded " + file.getName());
            }
        } catch (Exception e) {
            logger.error("Could not upload " + file.getName() + ", because: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText("Could not upload " + file.getName());
            alert.setContentText("Reason: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
