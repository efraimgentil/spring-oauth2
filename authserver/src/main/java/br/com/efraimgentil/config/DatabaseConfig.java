package br.com.efraimgentil.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
public class DatabaseConfig  {


  @Bean(name = "database")
  @Qualifier(value = "database")
  public ResourceBundle dataBaseProperties(){
    return ResourceBundle.getBundle("database");
  }

  @Value("classpath:schema.sql")
  private Resource schemaScript;

 /* @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(schemaScript);
    return populator;
  }*/

  @Bean
  public DataSource dataSource( @Qualifier("database") ResourceBundle env ) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getString("pg.driver"));
    dataSource.setUrl(env.getString("pg.url"));
    dataSource.setUsername(env.getString("pg.username"));
    dataSource.setPassword(env.getString("pg.password"));
    return dataSource;
  }

}
