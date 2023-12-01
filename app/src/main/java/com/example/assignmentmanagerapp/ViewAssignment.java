package com.example.assignmentmanagerapp;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignmentmanagerapp.data.AssignmentDbHelper;
import com.example.assignmentmanagerapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * An activity to view a particular assignment
 */
public class ViewAssignment extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = ViewAssignment.class.getSimpleName();

    String id, name, desc, dueDate, moduleCode;

    AssignmentDbHelper DB;

    Cursor cursor;

    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private static final int LITERATURE_SEARCH_LOADER = 43;

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    private TextView mErrorMessageDisplay;

    /**
     * onCreate is called when the ViewAssignment activity is first called. It fetches information
     * from the database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assignment);

        Bundle extras = getIntent().getExtras();
        id = extras.getString("ID");
        Log.d(TAG,id);
        DB = new AssignmentDbHelper(this);
        cursor = DB.getData();
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(id)) {
                name = (cursor.getString(1));
                desc = (cursor.getString(2));
                dueDate = (cursor.getString(3));
                moduleCode = (cursor.getString(4));
            }
        }
        TextView name_view = (TextView) findViewById(R.id.viewAssignmentTitle);
        TextView desc_view = (TextView) findViewById(R.id.viewAssignmentDescription);
        TextView dueDate_view = (TextView) findViewById(R.id.viewAssignmentDueDate);
        TextView moduleCode_view = (TextView) findViewById(R.id.viewAssignmentModuleCode);
        name_view.setText(name);
        desc_view.setText(desc);
        dueDate_view.setText(dueDate);
        moduleCode_view.setText(moduleCode);

        mSearchBoxEditText = (EditText) findViewById(R.id.searchBox);
        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.search_results);
        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message);


        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);

            mUrlDisplayTextView.setText(queryUrl);
        }

        getSupportLoaderManager().initLoader(LITERATURE_SEARCH_LOADER,null,this);
    }

    /**
     * This method generates the query to the literature api using the Url constructor in NetworkUtils
     */
    private void makeLiteratureSearchQuery() {
        String LiteratureQuery = mSearchBoxEditText.getText().toString();

        if (TextUtils.isEmpty(LiteratureQuery)) {
            mUrlDisplayTextView.setText("No query has been entered.");
            return;
        }

        URL literatureSearchUrl = NetworkUtils.buildUrl(LiteratureQuery);
        mUrlDisplayTextView.setText(literatureSearchUrl.toString());

        Bundle queryBundle = new Bundle();

        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, literatureSearchUrl.toString());

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader<String> literatureSearchLoader = loaderManager.getLoader(LITERATURE_SEARCH_LOADER);

        if (literatureSearchLoader == null) {
            loaderManager.initLoader(LITERATURE_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(LITERATURE_SEARCH_LOADER, queryBundle, this);
        }
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * onCreate for Loader
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                try {
                    URL literatureUrl = new URL(searchQueryUrlString);
                    String literatureSearchResults = NetworkUtils.getResponseFromHttpUrl(literatureUrl);
                    return literatureSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    /**
     * Called when loader is finished
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override public void onLoadFinished(Loader<String> loader, String data) {
        if (null == data) {
            showErrorMessage();
        } else {
            mSearchResultsTextView.setText(data);
            showJsonDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    /**
     * onCreate for options menu that executes search using API
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Method to execute search when menu is selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClickedId = item.getItemId();
        if (itemClickedId == R.id.action_search) {
            makeLiteratureSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to save state in case of device rotation
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mUrlDisplayTextView.getText().toString();
        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);
    }
}