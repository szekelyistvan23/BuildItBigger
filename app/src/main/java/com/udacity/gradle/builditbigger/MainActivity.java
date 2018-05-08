package com.udacity.gradle.builditbigger;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tellJoke(View view) {
        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
        JokeLoader jokeLoader = new JokeLoader();
        jokeLoader.execute();
    }
}
