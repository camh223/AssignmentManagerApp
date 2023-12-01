package com.example.assignmentmanagerapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignmentmanagerapp.data.AssignmentContract;

/**
 * AddAssignment is a class to add a new assignment to the database.
 */
public class AddAssignment extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment);
    }

    /**
     * This method submits the values in the text fields to the database using the content provider.
     * @param view The view that the method was called from.
     */

    public void onClickAddTask(View view) {

        String inputName = ((EditText) findViewById(R.id.editTextAssignmentTitle)).getText().toString();
        if (inputName.length() == 0) {
            return;
        }
        String inputDescription = ((EditText) findViewById(R.id.editTextAssignmentDescription)).getText().toString();
        if (inputDescription.length() == 0) {
            return;
        }
        String inputDueDate = ((EditText) findViewById(R.id.editTextAssignmentDueDate)).getText().toString();
        if (inputDueDate.length() == 0) {
            return;
        }
        String inputModuleCode = ((EditText) findViewById(R.id.editTextAssignmentModuleCode)).getText().toString();
        if (inputModuleCode.length() == 0) {
            return;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME, inputName);
        contentValues.put(AssignmentContract.AssignmentEntry.COLUMN_DESCRIPTION, inputDescription);
        contentValues.put(AssignmentContract.AssignmentEntry.COLUMN_DUE_DATE, inputDueDate);
        contentValues.put(AssignmentContract.AssignmentEntry.COLUMN_MODULE_CODE, inputModuleCode);

        Uri uri = getContentResolver().insert(AssignmentContract.AssignmentEntry.CONTENT_URI, contentValues);

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
