package com.example.mytodolist.model;

import java.util.Date;

public class Task {

  private int id;
  private int taskId;
  private int userId;
  private String name;
  private String description;
  private Date reminderDate;
  private Date createDate;
  private Date updateDate;

  public Task() {
    super();
  }

  public Task(int id, int taskId, int userId, String name, String description, Date reminderDate, Date createDate, Date updateDate) {
    super();
    this.id = id;
    this.taskId = taskId;
    this.userId = userId;
    this.name = name;
    this.description = description;
    this.reminderDate = reminderDate;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getReminderDate() {
    return reminderDate;
  }

  public void setReminderDate(Date reminderDate) {
    this.reminderDate = reminderDate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", taskId=" + taskId + ", userId=" + userId + ", name=" + name + ", description="
        + description + ", reminderDate=" + reminderDate + ", createDate=" + createDate + "]";
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}
