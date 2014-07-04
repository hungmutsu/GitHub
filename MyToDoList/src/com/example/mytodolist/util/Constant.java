package com.example.mytodolist.util;

public class Constant {

  public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	// Tên bảng trong CSDL
	public static final String TABLE_USER = "users";
	public static final String TABLE_TASK = "tasks";
	public static final String TABLE_TASKINDEVICE = "taskInDevice";
	
	public static final String KEY_ID = "id";

	// Tên các cột trong bảng users
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "username";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FULL_NAME = "fullName";

	// Tên các cột trong bảng tasks
	public static final String TASK_ID = "taskId";
	public static final String TASK_NAME = "name";
	public static final String TASK_DESCRIPTION = "description";
	public static final String TASK_REMINDER_DATE = "reminderDate";
	public static final String TASK_CREATE_DATE = "createDate";

	// Tên các cột trong bảng taskInDevice
	public static final String TASKINDEVICE_TID = "tId";
	public static final String TASKINDEVICE_DID = "dId";
	public static final String TASKINDEVICE_STATUS = "status";
	
	//JSON Node names
  public static final String TAG_ERROR = "error";
  public static final String TAG_MESSAGE = "message";
  
  //Url login
   public static String URL_USER_lOGIN = "http://192.168.1.79:8080/mytodo-service/login.php";
   public static String URL_USER_SIGNUP = "http://192.168.1.79:8080/mytodo-service/sign-up.php";
     
}
