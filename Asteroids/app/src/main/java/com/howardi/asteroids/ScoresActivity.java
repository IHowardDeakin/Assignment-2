package com.howardi.asteroids;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class ScoresActivity extends AppCompatActivity {
    List<String> highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        highScores.add("1. Bruce 1000");
        highScores.add("2. Steve 900");


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listrow, highScores);
        ListView scores_list = (ListView) findViewById(R.id.listScores);

        scores_list.setAdapter(adapter);
    }
}
