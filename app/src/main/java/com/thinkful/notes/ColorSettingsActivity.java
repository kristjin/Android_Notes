package com.thinkful.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class ColorSettingsActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        final RadioGroup bgGroup = (RadioGroup) findViewById(R.id.BGRadio);
        final RadioGroup fgGroup = (RadioGroup) findViewById(R.id.FGRadio);
        final Intent intent = getIntent();
        final String bgColor = (String)intent.getSerializableExtra("BG");
        final String fgColor = (String)intent.getSerializableExtra("FG");

        setColorRadios(bgGroup, fgGroup, bgColor, fgColor);

        Button mButton;
        mButton = (Button) findViewById(R.id.save_colors);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Intent goHome = new Intent();
                saveColors(bgGroup, fgGroup, goHome);
                setResult(RESULT_OK, goHome);
                finish();
            }
        });
    }

    private void setColorRadios(
            RadioGroup bgGroup,
            RadioGroup fgGroup,
            String bgColor,
            String fgColor)
    {

        switch (bgColor)
        {
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

        switch (fgColor)
        {
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

    private void saveColors(
            RadioGroup bgGroup,
            RadioGroup fgGroup,
            Intent intent)
    {

        int checkedId = bgGroup.getCheckedRadioButtonId();
        switch (checkedId)
        {
            case R.id.red:
                intent.putExtra("BG", "R");
                break;
            case R.id.green:
                intent.putExtra("BG", "G");
                break;
            case R.id.white:
                intent.putExtra("BG", "W");
                break;
            default:
                intent.putExtra("BG", "W");
                break;
        }
        checkedId = fgGroup.getCheckedRadioButtonId();
        switch (checkedId)
        {
            case R.id.purple:
                intent.putExtra("FG", "P");
                break;
            case R.id.yellow:
                intent.putExtra("FG", "Y");
                break;
            case R.id.grey:
                intent.putExtra("FG", "G");
                break;
            case R.id.black:
                intent.putExtra("FG", "B");
                break;
            default:
                intent.putExtra("FG", "B");
                break;
        }

    }
}
