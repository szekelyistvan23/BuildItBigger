package com.example.jokeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity{

    public static final String JOKE_FROM_SERVER = "joke_from_server";

    TextView jokeTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        jokeTextView = findViewById(R.id.joke_text);

        Intent intent = getIntent();
        String jokeSent = intent.getStringExtra(JOKE_FROM_SERVER);

        jokeTextView.setText(jokeSent);


    }
}
