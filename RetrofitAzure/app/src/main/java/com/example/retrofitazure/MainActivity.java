package com.example.retrofitazure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

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
            .baseUrl(AzureDictorsAPI.API_URL)
            .build();
    AzureDictorsAPI api = retrofit.create(AzureDictorsAPI.class);

    String token = "";
    ArrayList<Dictors> dictors = new ArrayList<>();
    DictorsAdapter dictorsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        dictorsAdapter = new DictorsAdapter(this, dictors);

        listView.setAdapter(dictorsAdapter);
    }

    public void onTokenClick(View v){
        Call<String> call = api.getToken();
        call.enqueue(new TokenCallback());
    }

    public void onDictorsClick(View v) {
        Call<ArrayList<Dictors>> callDictors = api.getDictors("Bearer " + token);
        callDictors.enqueue(new  TokenDictorsCallback());
    }

    class TokenCallback implements Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response){
            if (response.isSuccessful()){
                token = response.body();
                Log.d("mytag", "response: " + response.body());
            }
            else {
                Log.d("mytag", "error " + response.code());
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Log.d("mytag", "error " + t.getLocalizedMessage());
        }
    }

    class TokenDictorsCallback implements Callback<ArrayList<Dictors>> {
        @Override
        public void onResponse(Call<ArrayList<Dictors>> call, Response<ArrayList<Dictors>> response){
            if (response.isSuccessful()){
                dictors.clear();
                dictors.addAll(response.body());
                Log.d("mytag", "response: " + response.body());
                dictorsAdapter.notifyDataSetChanged();
            }
            else {
                Log.d("mytag", "error " + response.code());
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Dictors>> call, Throwable t) {
            Log.d("mytag", "error " + t.getLocalizedMessage());
        }
    }
}