package ch.fcappenzell.gruempeli.administration.tools.tournament.service.document;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DocumentServiceImpl implements DocumentService {

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

            //Store XML to File
            File file = new File("./data.xml");

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
