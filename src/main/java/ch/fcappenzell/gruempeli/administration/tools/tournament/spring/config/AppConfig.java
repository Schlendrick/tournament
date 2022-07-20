package ch.fcappenzell.gruempeli.administration.tools.tournament.spring.config;

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
@PropertySource("classpath:database.properties")
public class AppConfig {

    @Autowired
    Environment environment;

    private final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String USER = "dbuser";
    private final String PASSWORD = "dbpassword";

    DriverManagerDataSource driverManagerDataSource;

    public void setUrl(String url) {
        driverManagerDataSource.setUrl("jdbc:ucanaccess://"+url+";openExclusive=false;ignoreCase=true");
    }
    @Bean
    DataSource createDataSource() {
        driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(DRIVER);
        return driverManagerDataSource;
    }
}
