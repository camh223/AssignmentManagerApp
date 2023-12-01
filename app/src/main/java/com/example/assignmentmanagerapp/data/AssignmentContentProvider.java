package com.example.assignmentmanagerapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;

import static com.example.assignmentmanagerapp.data.AssignmentContract.AssignmentEntry.TABLE_NAME;

/**
    AssignmentContentProvider is a content provider class which
    provides access to the data stored in the assignments table.
 */
public class AssignmentContentProvider extends ContentProvider {

    public static final int ASSIGNMENTS = 100;
    public static final int ASSIGNMENT_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     *
        buildUriMatch() constructs the UriMatcher so that other classes
        may access the assignments table.
     */
    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AssignmentContract.AUTHORITY, AssignmentContract.PATH_ASSIGNMENTS, ASSIGNMENTS);
        uriMatcher.addURI(AssignmentContract.AUTHORITY, AssignmentContract.PATH_ASSIGNMENTS + "/#", ASSIGNMENT_WITH_ID);

        return uriMatcher;
    }

    private AssignmentDbHelper mAssignmentDbHelper;

    /**
        onCreate() instantiates the AssignmentDbHelper class with the current context
     */
    @Override
    public boolean onCreate() {

        Context context = getContext();
        mAssignmentDbHelper = new AssignmentDbHelper(context);
        return true;
    }

    /**
     *
        A method to insert a row into the assignments table.
        If the Uri is unknown it throws an UnsupportedOperationException.
        If it is unable to insert a row into the table, it throws an SQLException
     * @param uri the uri used to refer to the database
     * @param values
     */

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mAssignmentDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ASSIGNMENTS:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(AssignmentContract.AssignmentEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    /**
     * A method to query the data in the assignments table and return it as a cursor.
     *         If the uri is unknown, an UnsupportedOperationException is thrown.
     * @param uri The uri used to refer to the database
     * @param projection A selection argument
     * @param selection A selection argument
     * @param selectionArgs A selection argument
     * @param sortOrder The order to sort the queried data by
     * @return retCursor containing queried data
     */

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mAssignmentDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case ASSIGNMENTS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    /**
        The remaining methods exist due to the class extending Content Provider, but they were not used.
     */

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
