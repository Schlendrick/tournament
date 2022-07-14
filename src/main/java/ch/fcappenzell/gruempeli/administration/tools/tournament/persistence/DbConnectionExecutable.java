package ch.fcappenzell.gruempeli.administration.tools.tournament.persistence;/*
 * Created by Marco on 07.06.2018.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.function.Consumer;

public interface DbConnectionExecutable extends Consumer<Connection> {

    Logger logger = LoggerFactory.getLogger(DbConnectionExecutable.class);

}
