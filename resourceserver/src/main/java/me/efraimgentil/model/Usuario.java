package me.efraimgentil.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
public class Usuario {

  private Integer id;
  private String login;

  @JsonIgnore
  private String password;

  private String nome;



  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
