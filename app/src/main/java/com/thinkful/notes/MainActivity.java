package com.thinkful.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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


@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteListItemAdapter mAdapter;
    private Button mButton;
    private Context mContext;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            if (data.hasExtra("Note"))
            {
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        }
        else if (resultCode == RESULT_OK && requestCode == 2)
        {
            SharedPreferences prefs;
            prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            String BG = (String)data.getSerializableExtra("BG");
            String FG = (String)data.getSerializableExtra("FG");

            switch(BG)
            {
                case "R":
                    editor.putString("BG_COLOR", "R");
                    break;
                case "G":
                    editor.putString("BG_COLOR", "G");
                    break;
                case "W":
                    editor.putString("BG_COLOR", "W");
                    break;
                default:
                    editor.putString("BG_COLOR", "W");
                    break;
            }

            switch(FG)
            {
                case "P":
                    editor.putString("FG_COLOR", "P");
                    break;
                case "Y":
                    editor.putString("FG_COLOR", "Y");
                    break;
                case "G":
                    editor.putString("FG_COLOR", "G");
                    break;
                case "B":
                    editor.putString("FG_COLOR", "B");
                    break;
                default:
                    editor.putString("FG_COLOR", "B");
                    break;
            }

            editor.commit();
            setColor();
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

            // open Color Settings Activity page;
            launchColorSettings(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchColorSettings(Context mContext){
        Intent intent = new Intent(mContext, ColorSettingsActivity.class);
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String bgColor = prefs.getString("BG_COLOR", "DEFAULT");
        String fgColor = prefs.getString("FG_COLOR", "DEFAULT");

        intent.putExtra("BG", bgColor);
        intent.putExtra("FG", fgColor);

        ((Activity)mContext).startActivityForResult(intent, 2);
    }

    public void setColor(){
        Toast.makeText(this, "setColor() called!",
                Toast.LENGTH_LONG).show();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String color = prefs.getString("BG_COLOR", "DEFAULT");

        assert color != null;
        switch (color)
        {
            case "G":
                this.mRecyclerView.setBackgroundColor(Color.GREEN);
                break;
            case "R":
                this.mRecyclerView.setBackgroundColor(Color.RED);
                break;
            case "W":
                this.mRecyclerView.setBackgroundColor(Color.WHITE);
                break;
            default:
                Toast.makeText(this, "Defaulting, color is " + color,
                        Toast.LENGTH_LONG).show();
                this.mRecyclerView.setBackgroundColor(Color.WHITE);
                break;
        }

        color = prefs.getString("FG_COLOR", "B");
        assert color != null;

    }
}
