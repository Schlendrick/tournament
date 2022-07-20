package ch.fcappenzell.gruempeli.administration.tools.tournament.service.document;


import java.io.File;

public interface DocumentService {

    File createXML(Class[] clazz, Object data);

}
