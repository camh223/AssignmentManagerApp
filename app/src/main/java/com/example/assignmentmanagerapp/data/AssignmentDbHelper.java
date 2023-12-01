package com.example.assignmentmanagerapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.assignmentmanagerapp.MainActivity;
import com.example.assignmentmanagerapp.data.AssignmentContract.AssignmentEntry;

/**
 * AssignmentDpHelper contains the SQL needed to create and query the database.
 */
public class AssignmentDbHelper extends SQLiteOpenHelper {

    private static final String TAG = AssignmentDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "assignmentsDb.db";

    /*
        Version should be incremented each time a change is made to the schema of the database.
     */
    private static final int VERSION = 4;


    /**
     * Constructor for assignmentdbhelper
     * @param context that helper is called from
     */
    public AssignmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * onCreate called to generate table schema
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE "  + AssignmentEntry.TABLE_NAME + " (" +
                AssignmentEntry._ID                + " INTEGER PRIMARY KEY, " +
                AssignmentEntry.COLUMN_NAME        + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_DUE_DATE    + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_MODULE_CODE + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    /**
     * When the version is incremented, the old table is dropped in order to be replaced by the new one.
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AssignmentEntry.TABLE_NAME);
        onCreate(db);
    }

    /**
     * A method to fetch the data from the database and return it as a cursor.
     * @return a cursor containing the result of the db query
     */
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + AssignmentEntry.TABLE_NAME, null);
        return cursor;
    }
}

