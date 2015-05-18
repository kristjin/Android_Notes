package com.thinkful.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<NoteListItem> mNoteListItems = new ArrayList<>();

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        NoteDAO dao = new NoteDAO(context);
        mNoteListItems = dao.list();

    }

    public void addItem(NoteListItem item) {
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        mNoteListItems.remove(position);
        notifyItemRemoved(position);
    }



    @Override
    public NoteListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of v
                int pos = mRecyclerView.getChildLayoutPosition(v);
                NoteListItem noteListItem = mNoteListItems.get(pos);

                NoteDAO dao = new NoteDAO(mContext);
                dao.delete(noteListItem);

                Toast.makeText(mContext, "Deleted: " + noteListItem.getText(), Toast.LENGTH_LONG).show();

                removeItem(pos);

            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int position = mRecyclerView.getChildLayoutPosition(v);
                NoteListItem noteListItem = mNoteListItems.get(position);
                removeItem(position);

                Intent intent = new Intent(mContext, EditNoteActivity.class);
                intent.putExtra("Note", noteListItem);

                ((Activity)mContext).startActivityForResult(intent, 1);

                return true;
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteListItemAdapter.ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.setText(noteListItem.getText());
    }

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }
}