package com.example.mytodolist;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mytodolist.provider.MyToDo;

public class TaskListActivity extends ListActivity {

//For logging and debugging
  private static final String TAG = "TasksListActivity";

  private static final String[] TASK_PROJECTION = new String[]{
          MyToDo.Tasks._ID,
          MyToDo.Tasks.COLUMN_NAME_ID,
          MyToDo.Tasks.COLUMN_NAME_NAME,
          MyToDo.Tasks.COLUMN_NAME_DESCRIPTION,
          MyToDo.Tasks.COLUMN_NAME_REMINDER_DATE,
          MyToDo.Tasks.COLUMN_NAME_CREATE_DATE,
          MyToDo.Tasks.COLUMN_NAME_UPDATE_DATE
  };


  /**
   * The index of the TaskId column
   */
  private static final int COLUMN_INDEX_NAME = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //setContentView(R.layout.activity_task_list);

      // The user does not need to hold down the key to use menu shortcuts.
      setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

       /* If no data is given in the Intent that started this Activity, then this Activity
       * was started when the intent filter matched a MAIN action. We should use the default
       * provider URI.
       */
      // Gets the intent that started this Activity.
      Intent intent = getIntent();

      // If there is no data associated with the Intent, sets the data to the default URI, which
      // accesses a list of tasks.
      if (intent.getData() == null) {
          intent.setData(MyToDo.Tasks.CONTENT_URI);
      }

       /*
       * Sets the callback for context menu activation for the ListView. The listener is set
       * to be this Activity. The effect is that context menus are enabled for items in the
       * ListView, and the context menu is handled by a method in NotesList.
       */
      getListView().setOnCreateContextMenuListener(this);

      /* Performs a managed query. The Activity handles closing and requerying the cursor when needed.
       *
       * Please see the introductory note about performing provider operations on the UI thread.
       */
      Cursor cursor = managedQuery(getIntent().getData(), TASK_PROJECTION, null, null, null);
      
       /*
       * The following two arrays create a "map" between columns in the cursor and view IDs
       * for items in the ListView. Each element in the dataColumns array represents
       * a column name; each element in the viewID array represents the ID of a View.
       * The SimpleCursorAdapter maps them in ascending order to determine where each column
       * value will appear in the ListView.
       */

      // The names of the cursor columns to display in the view, initialized to the title column
      String[] dataColumns = { MyToDo.Tasks.COLUMN_NAME_NAME, MyToDo.Tasks.COLUMN_NAME_REMINDER_DATE, MyToDo.Tasks.COLUMN_NAME_UPDATE_DATE } ;

      // The view IDs that will display the cursor columns, initialized to the TextView in
      // noteslist_item.xml
      int[] viewIDs = { R.id.tvTaskName, R.id.tvReminder, R.id.tvUpdate };

      // Creates the backing adapter for the ListView.
      SimpleCursorAdapter adapter
              = new SimpleCursorAdapter(
              this,                             // The Context for the ListView
              R.layout.task_list_item,          // Points to the XML for a list item
              cursor,                           // The cursor to get items from
              dataColumns,
              viewIDs
      );

     
      // Sets the ListView's adapter to be the cursor adapter that was just created.
      setListAdapter(adapter);
  }

  /**
   * Called when the user clicks the device's Menu button the first time for
   * this Activity. Android passes in a Menu object that is populated with items.
   *
   * Sets up a menu that provides the Insert option plus a list of alternative actions for
   * this Activity. Other applications that want to handle notes can "register" themselves in
   * Android by providing an intent filter that includes the category ALTERNATIVE and the
   * mimeTYpe MyToDo.Tasks.CONTENT_TYPE. If they do this, the code in onCreateOptionsMenu()
   * will add the Activity that contains the intent filter to its list of options. In effect,
   * the menu will offer the user other applications that can handle notes.
   * @param menu A Menu object, to which menu items should be added.
   * @return True, always. The menu should be displayed.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate menu from XML resource
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.task_list, menu);

      // Generate any additional actions that can be performed on the
      // overall list.  In a normal install, there are no additional
      // actions found here, but this allows other applications to extend
      // our menu with their own actions.
      Intent intent = new Intent(null, getIntent().getData());
      intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
      menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
              new ComponentName(this, TaskListActivity.class), null, intent, 0, null);

      return super.onCreateOptionsMenu(menu);
  }

  /**
   * This method is called when the user selects an option from the menu, but no item
   * in the list is selected. If the option was INSERT, then a new Intent is sent out with action
   * ACTION_INSERT. The data from the incoming Intent is put into the new Intent. In effect,
   * this triggers the NoteEditor activity in the NotePad application.
   *
   * If the item was not INSERT, then most likely it was an alternative option from another
   * application. The parent method is called to process the item.
   * @param item The menu item that was selected by the user
   * @return True, if the INSERT menu item was selected; otherwise, the result of calling
   * the parent method.
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      if (id == R.id.action_insert) {
          /*
         * Launches a new Activity using an Intent. The intent filter for the Activity
         * has to have action ACTION_INSERT. No category is set, so DEFAULT is assumed.
         * In effect, this starts the NoteEditor Activity in NotePad.
         */
          startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
          return true;
      }
      return super.onOptionsItemSelected(item);
  }

  /**
   * This method is called when the user context-clicks a note in the list. NotesList registers
   * itself as the handler for context menus in its ListView (this is done in onCreate()).
   *
   * The only available options are COPY and DELETE.
   *
   * Context-click is equivalent to long-press.
   *
   * @param menu A ContexMenu object to which items should be added.
   * @param v The View for which the context menu is being constructed.
   * @param menuInfo Data associated with view.
   * @throws ClassCastException
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
      // The data from the menu item.
      AdapterView.AdapterContextMenuInfo info;

      // Tries to get the position of the item in the ListView that was long-pressed.
      try {
          // Casts the incoming data object into the type for AdapterView objects.
          info = (AdapterView.AdapterContextMenuInfo) menuInfo;
      } catch (ClassCastException e) {
          // If the menu object can't be cast, logs an error.
          Log.e(TAG, "bad menuInfo", e);
          return;
      }

      /*
       * Gets the data associated with the item at the selected position. getItem() returns
       * whatever the backing adapter of the ListView has associated with the item. In NotesList,
       * the adapter associated all of the data for a note with its list item. As a result,
       * getItem() returns that data as a Cursor.
       */
      Cursor cursor = (Cursor) getListAdapter().getItem(info.position);

      // If the cursor is empty, then for some reason the adapter can't get the data from the
      // provider, so returns null to the caller.
      if (cursor == null) {
          // For some reason the requested item isn't available, do nothing
          return;
      }

      // Inflate menu from XML resource
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.task_list_context, menu);

      // Sets the menu header to be the title of the selected note.
      menu.setHeaderTitle(cursor.getString(COLUMN_INDEX_NAME));

      // Append to the
      // menu items for any other activities that can do stuff with it
      // as well.  This does a query on the system for any activities that
      // implement the ALTERNATIVE_ACTION for our data, adding a menu item
      // for each one that is found.
      Intent intent = new Intent(null, Uri.withAppendedPath(getIntent().getData(),
              Integer.toString((int) info.id)));
      intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
      menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
              new ComponentName(this, TaskListActivity.class), null, intent, 0, null);
  }

  /**
   * This method is called when the user selects an item from the context menu
   * (see onCreateContextMenu()). The only menu items that are actually handled are DELETE and
   * COPY. Anything else is an alternative option, for which default handling should be done.
   *
   * @param item The selected menu item
   * @return True if the menu item was DELETE, and no default processing is need, otherwise false,
   * which triggers the default handling of the item.
   * @throws ClassCastException
   */
  @Override
  public boolean onContextItemSelected(MenuItem item) {
      // The data from the menu item.
      AdapterView.AdapterContextMenuInfo info;

      try {
          // Casts the data object in the item into the type for AdapterView objects.
          info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      } catch (ClassCastException e) {

          // If the object can't be cast, logs an error
          Log.e(TAG, "bad menuInfo", e);

          // Triggers default processing of the menu item.
          return false;
      }
      // Appends the selected task's ID to the URI sent with the incoming Intent.
      Uri taskUri = ContentUris.withAppendedId(getIntent().getData(), info.id);

      /*
       * Gets the menu item's ID and compares it to known actions.
       */
      switch (item.getItemId()) {
          case R.id.context_edit:
              // Launch activity to view/edit the currently selected item
              startActivity(new Intent(Intent.ACTION_EDIT, taskUri));
              return true;

          case R.id.context_delete:

              // Deletes the note from the provider by passing in a URI in note ID format.
              // Please see the introductory note about performing provider operations on the
              // UI thread.
              getContentResolver().delete(taskUri, null, null);

              // Returns to the caller and skips further processing.
              return true;
          default:
              return super.onContextItemSelected(item);
      }
  }

  /**
   * This method is called when the user clicks a task in the displayed list.
   *
   * This method handles incoming actions of either PICK (get data from the provider) or
   * GET_CONTENT (get or create data). If the incoming action is EDIT, this method sends a
   * new Intent to start NoteEditor.
   * @param l The ListView that contains the clicked item
   * @param v The View of the individual item
   * @param position The position of v in the displayed list
   * @param id The row ID of the clicked item
   */
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {

      // Constructs a new URI from the incoming URI and the row ID
      Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

      // Gets the action from the incoming Intent
      String action = getIntent().getAction();

      // Handles requests for note data
      if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {

          // Sets the result to return to the component that called this Activity. The
          // result contains the new URI
          setResult(RESULT_OK, new Intent().setData(uri));
      } else {

          // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
          // Intent's data is the note ID URI. The effect is to call NoteEdit.
          startActivity(new Intent(Intent.ACTION_EDIT, uri));
      }
  }

}
