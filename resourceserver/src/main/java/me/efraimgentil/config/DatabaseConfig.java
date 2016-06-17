package me.efraimgentil.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@Configuration
public class DatabaseConfig {


  @Bean(name = "database")
  @Qualifier(value = "database")
  public ResourceBundle dataBaseProperties(){
    return ResourceBundle.getBundle("database");
  }


  @Bean
  public DataSource dataSource( @Qualifier("database") ResourceBundle env ) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getString("pg.driver"));
    dataSource.setUrl(env.getString("pg.url"));
    dataSource.setUsername(env.getString("pg.username"));
    dataSource.setPassword(env.getString("pg.password"));
    return dataSource;
  }

  @Bean
  @Scope("prototype")
  public JdbcTemplate jdbcTemplate(DataSource dataSource){
    return new JdbcTemplate(dataSource);
  }



}
