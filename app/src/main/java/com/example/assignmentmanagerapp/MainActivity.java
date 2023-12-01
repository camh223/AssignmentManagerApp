package com.example.assignmentmanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.assignmentmanagerapp.data.AssignmentDbHelper;
import com.example.assignmentmanagerapp.data.AssignmentContract;

import java.util.ArrayList;

/**
 * The main activity called when the app is first run. Responsible for generating the recycler view
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int ASSIGNMENT_LOADER_ID = 0;


    RecyclerView recyclerView;
    ArrayList<String> id, name, desc, dueDate, moduleCode;
    AssignmentDbHelper DB;
    AssignmentAdapter adapter;

    private Toast mToast;

    /**
     * Called when the activity is created, generates the recycler view layout
     *      using the assignment adapter class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new AssignmentDbHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        desc = new ArrayList<>();
        dueDate = new ArrayList<>();
        moduleCode = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_assignments);
        adapter = new AssignmentAdapter(this, id, name, desc, dueDate, moduleCode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        displayData();

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        /**
         * A floating action button that triggers an intent to the add assignment activity
         */

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddAssignment.class);
                startActivity(addTaskIntent) ;
            }
        });

        Button refreshButton = (Button) findViewById(R.id.refresh_button);

        /**
         * A button that refreshes the recycler view list
         */
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = new ArrayList<>();
                desc = new ArrayList<>();
                dueDate = new ArrayList<>();
                moduleCode = new ArrayList<>();
                refreshList();
            }
        });

    }

    /**
     * A method that updates the recycler view data with the database content
     */
    private void displayData() {
        Cursor cursor = DB.getData();
        if (cursor.getCount() == 0 ) {
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                desc.add(cursor.getString(2));
                dueDate.add(cursor.getString(3));
                moduleCode.add(cursor.getString(4));
            }
        }
    }

    /**
     * A method to refresh recycler view
     */
    private void refreshList() {
        adapter = new AssignmentAdapter(this, id, name, desc, dueDate, moduleCode);
        recyclerView.setAdapter(adapter);
        displayData();
    }
}
