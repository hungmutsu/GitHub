package com.example.mytodolist.model;

public class User {

  private int id;
  private int userId;
  private String fullname;
  private String username;
  private String password;

  public User() {
    super();
  }

  public User(int id, int userId, String username, String password, String fullname) {
    super();
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.fullname = fullname;
  }
  
  public User(int userId, String username, String password, String fullname) {
    super();
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.fullname = fullname;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", userId=" + userId + ", fullname=" + fullname + ", username=" + username
        + ", password=" + password + "]";
  } 
}
