package com.example.randommovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListConstructor();
    }
    ArrayList<String>movies = new ArrayList<String>();

    public void movieListConstructor() {
        Resources res = getResources();
        String[] movieList = res.getStringArray(R.array.movies);
        for (int i = 0; i < movieList.length; i++) {
            movies.add(movieList[i]);
        }
        Collections.shuffle(movies);
    }
    int movieNumber = -1;
    public void onButtonClick(View v) {
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        if (movieNumber == movies.size() - 1) {
            textView.setText("Kina ne budet! \n Click on the button to generate a new list!");
            button.setText("Generate a new list");
            Collections.shuffle(movies);
            movieNumber = -1;
        }
        else {
            movieNumber += 1;
            textView.setText(movies.get(movieNumber));
            button.setText("Сhoose next movie");
        }

    }

}