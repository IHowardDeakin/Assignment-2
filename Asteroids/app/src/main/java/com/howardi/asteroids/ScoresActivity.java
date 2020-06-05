package com.howardi.asteroids;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ScoresActivity extends AppCompatActivity {
    List<String> highScores;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_scores);


        highScores = new ArrayList<String>();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.high_score_key),
                Context.MODE_PRIVATE);
        String scores = sharedPref.getString(getString(R.string.high_score_key), "2000 Bruce|1000 Lee");
        highScores = Arrays.asList(scores.split("\\|"));


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listrow, highScores);
        ListView scores_list = (ListView) findViewById(R.id.listScores);

        scores_list.setAdapter(adapter);
    }

    public void onBackClick(View view) {
        finish();
    }
}
