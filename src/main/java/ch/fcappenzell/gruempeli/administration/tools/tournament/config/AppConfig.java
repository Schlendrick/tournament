package ch.fcappenzell.gruempeli.administration.tools.tournament.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ch.fcappenzell.gruempeli.administration.tools.tournament")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Autowired
    Environment environment;

    private final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String DB_USER = "database.username";
    private final String DB_PASSWORD = "database.password";

    DriverManagerDataSource driverManagerDataSource;

    public void setUrl(String url) {
        driverManagerDataSource.setUrl("jdbc:ucanaccess://"+url+";openExclusive=false;ignoreCase=true");
    }
    @Bean
    DataSource createDataSource() {
        driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername(environment.getProperty(DB_USER));
        driverManagerDataSource.setPassword(environment.getProperty(DB_PASSWORD));
        driverManagerDataSource.setDriverClassName(DRIVER);
        return driverManagerDataSource;
    }

    private final String FTP_USER = "ftp.username";
    private final String FTP_PASSWORD = "ftp.password";
    private final String FTP_PORT = "ftp.port";
    private final String FTP_HOST = "ftp.host";

    public String getFTP_USER(){
        return environment.getProperty(FTP_USER);
    }

    public String getFTP_PASSWORD(){
        return environment.getProperty(FTP_PASSWORD);
    }

    public String getFTP_PORT(){
        return environment.getProperty(FTP_PORT);
    }

    public String getFTP_HOST(){
        return environment.getProperty(FTP_HOST);
    }
}
