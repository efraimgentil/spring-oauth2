package me.efraimgentil.model;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 21/06/16.
 */
public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    User u = new User();
    u.setId( resultSet.getInt("id"));
    u.setLogin( resultSet.getString("login") );
    u.setName( resultSet.getString("nome") );
    return u ;
  }
}
