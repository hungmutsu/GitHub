package com.example.mytodolist.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mytodolist.model.Task;
import com.example.mytodolist.provider.DatabaseHelper;
import com.example.mytodolist.util.Constant;

public class TaskDao {

  private SQLiteDatabase db;
  private final Context context;
  private DatabaseHelper dbHelper;

  public TaskDao(Context context) {
    this.context = context ;
  }
  
  public TaskDao open() throws SQLException {
    dbHelper = new DatabaseHelper(this.context);
    return this;
  }
  

  /**
   * Thêm mới Task
   * 
   * @param task {@link Task}
   */
  public long createTask(Task task) {
    this.db = dbHelper.getWritableDatabase();

    SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
    String reminderDate = dateTimeFormat.format(task.getReminderDate());
    String createDate = dateTimeFormat.format(Calendar.getInstance().getTime());
    
    // dat cac gia tri cua user can them cho bien values
    ContentValues values = new ContentValues();
    
    values.put(Constant.TASK_ID, task.getTaskId());
    values.put(Constant.TASK_NAME, task.getName());
    values.put(Constant.TASK_DESCRIPTION, task.getDescription());
    values.put(Constant.TASK_REMINDER_DATE, reminderDate);
    values.put(Constant.TASK_CREATE_DATE, createDate);

    // them vao CSDL
    return db.insert(Constant.TABLE_TASK, null, values);
  }

  /**
   * Cập nhật Task theo Id
   * 
   * @param task {@link Task}
   * @param id Id của Task
   */
  public int updateTask(Task task, int id) {
    this.db = dbHelper.getWritableDatabase();

    SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
    String reminderDate = dateTimeFormat.format(task.getReminderDate());
    String createDate = dateTimeFormat.format(Calendar.getInstance().getTime());
    
    // dat cac gia tri cua user can them cho bien values
    ContentValues values = new ContentValues();
    
    values.put(Constant.TASK_ID, task.getTaskId());
    values.put(Constant.TASK_NAME, task.getName());
    values.put(Constant.TASK_DESCRIPTION, task.getDescription());
    values.put(Constant.TASK_REMINDER_DATE, reminderDate);
    values.put(Constant.TASK_CREATE_DATE, createDate);

    return db.update(Constant.TABLE_TASK, values, Constant.KEY_ID + "=" + id, null);
  }

  /**
   * Xóa Task theo Id
   * 
   * @param id Id của Task
   */
  public void deleteTask(int id) {
    this.db = dbHelper.getWritableDatabase();
    
    db.delete(Constant.TABLE_TASK, Constant.KEY_ID + "=" + id, null);
  }

  /**
   * Thông tin chi tiết của Task theo Id
   * 
   * @param id Id của Task
   * @return {@link Task}
   */
  public Task getTask(int id) {
    this.db = dbHelper.getReadableDatabase();
    Task task = new Task();
    
    String sql = String.format("SELECT * FROM %s WHERE %s = %s", Constant.TABLE_TASK, Constant.KEY_ID, id);
    
    Log.d("MYTODO", sql);

    // doi tuong luu cac hang cua bang truy van
    Cursor cursor = db.rawQuery(sql, null);

    // chuyen con tro den dong dau tien neu du lieu tra ve tu CSDL khong phai null
    if (cursor != null) {
      cursor.moveToFirst();
    }

    task.setId(cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)));
    task.setTaskId(cursor.getInt(cursor.getColumnIndex(Constant.TASK_ID)));
    task.setName(cursor.getString(cursor.getColumnIndex(Constant.TASK_NAME)));
    task.setDescription(cursor.getString(cursor.getColumnIndex(Constant.TASK_DESCRIPTION)));
    
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
    String reminderDate = cursor.getString(cursor.getColumnIndex(Constant.TASK_REMINDER_DATE));
    String createDate = cursor.getString(cursor.getColumnIndex(Constant.TASK_CREATE_DATE));
    try {
      task.setReminderDate(dateTimeFormat.parse(reminderDate));
      task.setCreateDate(dateTimeFormat.parse(createDate));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return task;
  }

  /**
   * Danh sách Task
   * 
   * @return {@link ArrayList}
   */
  public ArrayList<Task> getAllTask() {
    this.db = dbHelper.getReadableDatabase();
    ArrayList<Task> tasks = new ArrayList<Task>();

    String selectQuery = String.format("SELECT * FROM %s", Constant.TABLE_TASK);
    Log.d("MYTODO", selectQuery);
    Cursor cursor = db.rawQuery(selectQuery, null);
    Log.d("Count Cursor", String.valueOf(cursor.getCount()));
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      
      do {
        Task task = new Task();
        
        task.setId(cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)));
        task.setTaskId(cursor.getInt(cursor.getColumnIndex(Constant.TASK_ID)));
        task.setName(cursor.getString(cursor.getColumnIndex(Constant.TASK_NAME)));
        task.setDescription(cursor.getString(cursor.getColumnIndex(Constant.TASK_DESCRIPTION)));
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
        String reminderDate = cursor.getString(cursor.getColumnIndex(Constant.TASK_REMINDER_DATE));
        String createDate = cursor.getString(cursor.getColumnIndex(Constant.TASK_CREATE_DATE));
        try {
          task.setReminderDate(dateTimeFormat.parse(reminderDate));
          task.setCreateDate(dateTimeFormat.parse(createDate));
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        tasks.add(task);

      } while (cursor.moveToNext());
    }
    
    return tasks;
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
