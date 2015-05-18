package com.thinkful.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;


@SuppressWarnings("deprecation")
public class ColorSettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        RadioGroup bgGroup = (RadioGroup) findViewById(R.id.BGRadio);
        String bgColor = prefs.getString("BG_COLOR", "W");
        assert bgColor != null;
        switch (bgColor) {
            case "G":
                bgGroup.check(R.id.green);
                break;
            case "R":
                bgGroup.check(R.id.red);
                break;
            default:
                bgGroup.check(R.id.white);
                break;
        }

        RadioGroup fgGroup = (RadioGroup) findViewById(R.id.FGRadio);
        String fgColor = prefs.getString("FG_COLOR", "B");
        assert fgColor != null;
        switch (fgColor) {
            case "P":
                fgGroup.check(R.id.purple);
                break;
            case "Y":
                fgGroup.check(R.id.yellow);
                break;
            case "G":
                fgGroup.check(R.id.grey);
                break;
            default:
                fgGroup.check(R.id.black);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
