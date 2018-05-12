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

    public static final String JOKE_FROM_SERVER = "joke_from_server" ;
    public static final String SERVER_ERROR = "Failed to connect to /10.0.2.2:8080";
    public static final String EMULATOR_LOCALHOST = "http://10.0.2.2:8080/_ah/api/";
    private ProgressBar jokeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeProgressBar = findViewById(R.id.joke_progress_bar);
    }

    public void tellJoke(View view) {
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
            if (myApi == null){
                MyApi.Builder builder =
                        new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                                .setRootUrl(EMULATOR_LOCALHOST)
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
            jokeProgressBar.setVisibility(View.GONE);

            if ( s != null && !s.equals(SERVER_ERROR)){
                Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                intent.putExtra(JOKE_FROM_SERVER, s);
                startActivity(intent);
            }
            if (s.equals(SERVER_ERROR)){
                Toast.makeText(MainActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
