package com.example.assignmentmanagerapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * AssignmentContract is a class that provides the URI to the
 *     assignments table, as well as providing static variables
 *     that correspond to the attributes in the assignments table.
 */
public class AssignmentContract {

    /*
        Variables that make up the components of the URI.
     */
    public static final String AUTHORITY = "com.example.assignmentmanagerapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_ASSIGNMENTS = "assignments";


    /**
     * AssignmentEntry is an inner class to AssignmentContract which contains
     *         the columns within the assignments table.
     */
    public static final class AssignmentEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ASSIGNMENTS).build();


        public static final String TABLE_NAME = "assignments";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_MODULE_CODE = "module_code";

    }
}
