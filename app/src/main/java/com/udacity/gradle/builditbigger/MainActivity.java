package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String JOKE_FROM_SERVER = "joke_from_server";
    private ProgressBar jokeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeProgressBar = findViewById(R.id.joke_progress_bar);
    }

    public void tellJoke(View view) {
//        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();
        jokeAsyncTask.execute();
    }

    public class JokeAsyncTask extends AsyncTask<Void, Void, String> {

        private MyApi myApi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jokeProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String address = "http://10.0.2.2:8080/_ah/api/";
            if (myApi == null){
                MyApi.Builder builder =
                        new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                                .setRootUrl(address)
                                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                    @Override
                                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                        request.setDisableGZipContent(true);
                                    }
                                });
                myApi = builder.build();
            }
            try {
                return myApi.getJoke().execute().getData();
            } catch (IOException e){
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JokeAsyncTask", "onPostExecute: " + s);
            if ( s != null){
                jokeProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                intent.putExtra(JOKE_FROM_SERVER, s);
                startActivity(intent);
            }
        }
    }
}
