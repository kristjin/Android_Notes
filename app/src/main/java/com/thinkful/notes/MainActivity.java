package com.thinkful.notes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteListItemAdapter mAdapter;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesDBHelper.getInstance(this).getReadableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text in the EditText
                EditText view = (EditText) findViewById(R.id.edit_text);
                String text = view.getText().toString();
                // Create a new NoteListItem with the text
                NoteListItem i = new NoteListItem(text);
                // Add the item to the adapter
                mAdapter.addItem(i);
                // Set the EditText to an empty string
                view.setText("");
                //Instantiate the NoteDAO class
                NoteDAO dao = new NoteDAO(MainActivity.this);
                dao.save(i);
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        setColor();
    }

    public void openColorDialog(){
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_color_title)
                .setMessage(R.string.setting_color_message)
                .setView(input)
                .setPositiveButton(R.string.positive_button_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value = input.getText().toString();
                                SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("NOTE_COLOR", value);
                                editor.commit();
                                setColor();
                            }
                        })
                .setNegativeButton(R.string.negative_button_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //No need for code here.
                            }
                        })
                .show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.hasExtra("Note")) {
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openColorDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setColor(){
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String color = prefs.getString("NOTE_COLOR", "W");
        if(color.toUpperCase().contains("G")){
            mRecyclerView.setBackgroundColor(Color.GREEN);
        }else if(color.toUpperCase().contains("R")){
            mRecyclerView.setBackgroundColor(Color.RED);
        }else{
            mRecyclerView.setBackgroundColor(Color.WHITE);
        }
    }
}
