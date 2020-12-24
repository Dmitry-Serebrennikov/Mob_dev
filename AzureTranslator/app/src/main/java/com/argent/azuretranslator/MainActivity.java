package com.argent.azuretranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AzureTranslationAPI.API_URL)
            .build();

    Gson gson = new Gson();

    AzureTranslationAPI api = retrofit.create(AzureTranslationAPI.class);

    LanguagesResponse languagesResponse;
    ArrayList<Language> languages = new ArrayList<>();
    ArrayList<TranslatedText> translatedTexts = new ArrayList<>();

    Spinner spinner;
    ArrayAdapter<Language> adapter;

    EditText inputText;
    TextView translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.editText);
        translatedText = findViewById(R.id.translatedTextView);

        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Call<LanguagesResponse> call = api.getLanguages();
        call.enqueue(new LanguagesCallback());
    }

    public void onClick(View view) {
        String text = inputText.getText().toString();
        ArrayList<TranslateItemForRequest> request = new ArrayList<>();
        request.add(new TranslateItemForRequest(text));
        String selectedLang = spinner.getSelectedItem().toString();
        String shortLang = "";

        for(String key : languagesResponse.translation.keySet())
        {
            if (selectedLang == languagesResponse.translation.get(key).nativeName) {
                shortLang = key;
                break;
            }
        }

        String body = gson.toJson(request);
        Call<List<TranslatorResponse>> translatedText = api.translate(body, shortLang);
        translatedText.enqueue(new TranslatedTextCallback());
    }

    class LanguagesCallback implements Callback<LanguagesResponse> {

        @Override
        public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
            if (response.isSuccessful()) {
                languagesResponse = response.body();
                for(String key : languagesResponse.translation.keySet())
                {
                    languages.add(languagesResponse.translation.get(key));
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                Log.d("TAG", "onResponse: " + response.code());
                Log.d("TAG", "onResponse: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<LanguagesResponse> call, Throwable t) {
            Log.d("TAG", "onFailure: " + t.toString());
            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
        }
    }

    class TranslatedTextCallback implements Callback<List<TranslatorResponse>> {

        @Override
        public void onResponse(Call<List<TranslatorResponse>> call, Response<List<TranslatorResponse>> response) {
            if (response.isSuccessful()) {
                List<TranslatorResponse> test = response.body();
                translatedTexts = test.get(0).translations;
                StringBuilder stringBuilder = new StringBuilder();
                for (TranslatedText row : translatedTexts) {
                    stringBuilder.append(row.toString() + '\n');
                }
                translatedText.setText(stringBuilder);
            }
            else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                Log.d("TAG", "onResponse: " + response.code());
                Log.d("TAG", "onResponse: " + response.raw());
            }
        }

        @Override
        public void onFailure(Call<List<TranslatorResponse>> call, Throwable t) {
            Log.d("TAG", "onFailure: " + t.toString());
            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
        }
    }
}