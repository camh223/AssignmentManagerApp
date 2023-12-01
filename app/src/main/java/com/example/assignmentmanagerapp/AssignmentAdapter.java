package com.example.assignmentmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignmentmanagerapp.data.AssignmentContract;

import java.util.ArrayList;

/**
 * AssignmentAdapter is an adapter class that populates the recycler view containing the assignments.
 */
public class AssignmentAdapter extends
        RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private static final String TAG = AssignmentAdapter.class.getSimpleName();

    private static int viewHolderCount;

    private Context mContext;

    private ArrayList id_id, name_id, desc_id, dueDate_id, modCode_id;

    /**
     * AssignmentAdapter constructor
     * @param mContext the context that the constructor is called from
     * @param id_id the primary key of an assignment
     * @param name_id the name of an assignment
     * @param desc_id the description of an assignment
     * @param dueDate_id the date an assignment is due
     * @param modCode_id the module code that the assignment belongs to
     */

    public AssignmentAdapter(Context mContext, ArrayList id_id, ArrayList name_id, ArrayList desc_id, ArrayList dueDate_id, ArrayList modCode_id) {
        this.mContext = mContext;
        this.id_id = id_id;
        this.name_id = name_id;
        this.desc_id = desc_id;
        this.dueDate_id = dueDate_id;
        this.modCode_id = modCode_id;
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * onCreateViewHolder is called when a viewholder is created, it specifies the design elements of
     * the viewholder
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return The view holder
     */
    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForListItem = R.layout.assignment_item;
        boolean shouldAttachToParentImmediately = false;

        View v = LayoutInflater.from(mContext).inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        AssignmentViewHolder viewHolder = new AssignmentViewHolder(v);

        viewHolder.itemView.setBackgroundColor(Color.WHITE);

        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    /**
     * onBindViewHolder updates the viewholder contents depending on the items position
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {

        //Set values
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.dueDate_id.setText(String.valueOf(dueDate_id.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewAssignment.class);
                String view_id = String.valueOf(id_id.get(holder.getAdapterPosition()));
                Log.d(TAG,view_id);
                intent.putExtra("ID", view_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    /**
     * Constructor for an assignment view holder
     */
    class AssignmentViewHolder extends RecyclerView.ViewHolder {

        TextView name_id, dueDate_id;

        public AssignmentViewHolder(View itemView) {
            super(itemView);

            name_id = (TextView) itemView.findViewById(R.id.tv_assignment_name);

            dueDate_id = (TextView) itemView.findViewById(R.id.tv_assignment_due_date);
        }
    }
}