package ch.fcappenzell.gruempeli.administration.tools.tournament.service.impl;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
public class FileUploadServiceImpl {

    Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    public void handleFileUpload(File file) {
        /*
        String FTP_ADDRESS = "fcappenzell.ch";
        String LOGIN = "gruempelidev";
        String PSW = "I2526u!ktRnx97h5#3rD1$5x5p7~Ld49a06l";
        */

        String FTP_ADDRESS = "localhost";
        String LOGIN = "user";
        String PSW = "123";


        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect(FTP_ADDRESS);

            if (con.login(LOGIN, PSW)) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                boolean result = con.storeFile(file.getName(), new FileInputStream(file));
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
