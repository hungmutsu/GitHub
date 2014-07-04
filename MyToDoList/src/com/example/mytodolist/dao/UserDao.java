package com.example.mytodolist.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mytodolist.model.User;
import com.example.mytodolist.provider.DatabaseHelper;
import com.example.mytodolist.util.Constant;

public class UserDao {

  private SQLiteDatabase db;
  private final Context context;

  private DatabaseHelper dbHelper;
  
  public UserDao(Context context) {
    this.context = context;
  }

  public UserDao open() throws SQLException {
    dbHelper = new DatabaseHelper(this.context);
    return this;
  }
  
  /**
   * Thêm mới User
   * 
   * @param user {@link User}
   */
  public void createUser(User user) {
    this.db = dbHelper.getWritableDatabase();

    // dat cac gia tri cua user can them cho bien values
    ContentValues values = new ContentValues();
    values.put(Constant.USER_ID, user.getUserId());
    values.put(Constant.USER_NAME, user.getUsername());
    values.put(Constant.USER_PASSWORD, user.getPassword());
    values.put(Constant.USER_FULL_NAME, user.getUsername());

    Log.d("USER_ID", user.getUsername());
    
    // them vao CSDL
    long result = db.insert(Constant.TABLE_USER, null, values);
  }

  /**
   * Cập nhật User
   * 
   * @param user {@link User}
   * @param id User Id
   */
  public void updateUser(User user, int id) {
    this.db = dbHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(Constant.USER_ID, user.getUserId());
    values.put(Constant.USER_NAME, user.getUsername());
    values.put(Constant.USER_PASSWORD, user.getPassword());
    values.put(Constant.USER_FULL_NAME, user.getUsername());

    db.update(Constant.TABLE_USER, values, Constant.KEY_ID + "=" + id, null);
  }

  /**
   * Thông tin chi tiết của User theo Id
   * 
   * @param id User Id
   */
  public User getUser(int id) {
    User user = new User();

    // cap quyen doc CSDL cho bien database
    this.db = dbHelper.getReadableDatabase();

    // gan cau lenh SQL vao bien selectQuerry
    String selectQuery = String.format("SELECT * FROM %s WHERE %s = %s", Constant.TABLE_USER, Constant.USER_ID, id);

    // Log ra selectQuerry
    Log.d("MYTODO", selectQuery);

    // doi tuong luu cac hang cua bang truy van
    Cursor cursor = db.rawQuery(selectQuery, null);

    // chuyen con tro den dong dau tien neu du lieu tra ve tu CSDL khong phai null
    if (cursor != null) {
      cursor.moveToFirst();
    }

    user.setId(cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)));
    user.setUserId(cursor.getInt(cursor.getColumnIndex(Constant.USER_ID)));
    user.setUsername(cursor.getString(cursor.getColumnIndex(Constant.USER_NAME)));
    user.setPassword(cursor.getString(cursor.getColumnIndex(Constant.USER_PASSWORD)));
    user.setFullname(cursor.getString(cursor.getColumnIndex(Constant.USER_FULL_NAME)));

    return user;
  }

  /**
   * Danh sách user
   * 
   * @return {@link ArrayList}
   */
  public ArrayList<User> getAllUser() {
    ArrayList<User> users = new ArrayList<User>();

    this.db = dbHelper.getReadableDatabase();

    String selectQuery = String.format("SELECT * FROM %s", Constant.TABLE_USER);
    Log.d("MYTODO", selectQuery);
    Cursor cursor = db.rawQuery(selectQuery, null);
    if (cursor != null) {
      cursor.moveToFirst();
      do {
        User user = new User();

        user.setId(cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)));
        user.setUserId(cursor.getInt(cursor.getColumnIndex(Constant.USER_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Constant.USER_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constant.USER_PASSWORD)));
        user.setFullname(cursor.getString(cursor.getColumnIndex(Constant.USER_FULL_NAME)));

        users.add(user);

      } while (cursor.moveToNext());
    }

    return users;
  }
  

 /**
 * Dong ket noi
 */
public void closeDatabase() {
   this.db = dbHelper.getWritableDatabase();
   
   if(this.db !=null && this.db.isOpen()) {
     this.db.close();
   }
 }
}
