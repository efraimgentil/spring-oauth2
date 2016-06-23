package br.com.efraimgentil.model;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 22/06/16.
 */
public class UserInfo {

  private String login;
  private String name;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserInfo{");
    sb.append("login='").append(login).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
