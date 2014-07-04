package com.example.mytodolist.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sony on 7/3/2014.
 */
public class MyToDo {
    public static final String AUTHORITY = "com.example.mytodolist";

    private MyToDo() {}

    public static final class Users implements BaseColumns {
        private Users() {}

        /**
         * The table name offered by this provider
         */
        public static final String TABLE_NAME = "users";

        /*
         * URI definitions
         */

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path parts for the URIs
         */

        /**
         * Path part for the Users URI
         */
        private static final String PATH_USERS = "/users";

        /**
         * Path part for the User ID URI
         */
        private static final String PATH_USER_ID = "/users/";

        /**
         * 0-relative position of a user ID segment in the path part of a user ID URI
         */
        public static final int USER_ID_PATH_POSITION = 1;

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_USERS);

        /**
         * The content URI base for a single user. Callers must
         * append a numeric user id to this Uri to retrieve a user
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_USER_ID);

        /**
         * The content URI match pattern for a single user, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN
                = Uri.parse(SCHEME + AUTHORITY + PATH_USER_ID + "/#");
        /*
         * MIME type definitions
         */

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of users.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.users";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single user.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.users";

        /*
         * Column definitions
         */

        /**
         * Column name for the id of the user
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_ID = "userId";

        /**
         * Column name of the username
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_USERNAME = "username";

        /**
         * Column name of the password
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_PASSWORD = "password";

        /**
         * Column name of the full name
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_FULL_NAME = "full_name";
    }

    public static final class Tasks implements BaseColumns {
        private Tasks() {}

        /**
         * The table name offered by this provider
         */
        public static final String TABLE_NAME = "tasks";

        /*
         * URI definitions
         */

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path parts for the URIs
         */

        /**
         * Path part for the Tasks URI
         */
        private static final String PATH_TASKS = "/tasks";

        /**
         * Path part for the Task ID URI
         */
        private static final String PATH_TASK_ID = "/tasks/";

        /**
         * 0-relative position of a task ID segment in the path part of a task ID URI
         */
        public static final int TASK_ID_PATH_POSITION = 1;

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_TASKS);

        /**
         * The content URI base for a single task. Callers must
         * append a numeric task id to this Uri to retrieve a task
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_ID);

        /**
         * The content URI match pattern for a single task, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN
                = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_ID + "/#");
        /*
         * MIME type definitions
         */

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of tasks.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.tasks";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single task.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.tasks";

        /*
         * Column definitions
         */

        /**
         * Column name for the taskId
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_ID = "taskId";
        
        /**
         * Column name for the taskId
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_USER_ID = "userId";

        /**
         * Column name of the task name
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_NAME = "name";

        /**
         * Column name of the description
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_REMINDER_DATE = "reminder";
        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_CREATE_DATE = "created";

        /**
         * Column name for the modification timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_UPDATE_DATE = "updated";
    }
}
