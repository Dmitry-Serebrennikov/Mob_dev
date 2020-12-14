package com.example.randommovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            movieListConstructor();
        } catch (IOException e) {};
    }
    ArrayList<Movie>movies = new ArrayList<Movie>();

    public void movieListConstructor() throws IOException {
        InputStream stream = getAssets().open("Movies.json");
        InputStreamReader reader = new InputStreamReader(stream);
        Gson gson = new Gson();
        MovieArray m = gson.fromJson(reader, MovieArray.class);
        movies = m.movies;
        Collections.shuffle(movies);
    }
    int movieNumber = -1;
    public void onButtonClick(View v) {
        TextView title = findViewById(R.id.title);
        TextView release_date = findViewById(R.id.release_date);
        TextView running_time = findViewById(R.id.running_time);
        TextView genre = findViewById(R.id.genre);
        TextView director_by = findViewById(R.id.director_by);
        TextView description = findViewById(R.id.description);

        Button button = findViewById(R.id.button);
        if (movieNumber == movies.size() - 1) {
            title.setText("Kina ne budet! \n Click on the button to generate a new list!");
            release_date.setText(""); running_time.setVisibility(View.GONE); genre.setText(""); director_by.setText(""); description.setText("");

            button.setText("Generate a new list");

            Collections.shuffle(movies);
            movieNumber = -1;
        }
        else {
            movieNumber += 1;
            Movie m = movies.get(movieNumber);

            title.setText(m.title);
            release_date.setText("Release date: " + m.release_date);
            running_time.setText("Running time: " + Integer.toString(m.running_time) + " min");
            genre.setText("Genre: " + m.genre);
            director_by.setText("Director by " + m.director_by);
            description.setText(m.description);
            button.setText("Сhoose next movie");

        }

    }

}