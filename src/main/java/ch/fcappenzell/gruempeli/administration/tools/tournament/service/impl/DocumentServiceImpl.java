package ch.fcappenzell.gruempeli.administration.tools.tournament.service.impl;

import ch.fcappenzell.gruempeli.administration.tools.tournament.properties.DocumentStorageProperties;
import ch.fcappenzell.gruempeli.administration.tools.tournament.service.DocumentService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final Path docStorageLocation;

    @Autowired
    public DocumentServiceImpl(DocumentStorageProperties documentStorageProperty) throws IOException {
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
    }

    @Override
    public File createXML(Class[] clazz, Object data) {

        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting?
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Path targetLocation = this.docStorageLocation;

            //Store XML to File
            File file = new File(targetLocation + "/data.xml");

            //Writes XML file to file-system
            jaxbMarshaller.marshal(data, file);

            return file;

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
