package com.example.retrofitazure;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface AzureDictorsAPI {
    String API_URL = "https://eastasia.api.cognitive.microsoft.com";
    String key = "1db01788ad86488d90d573a7fe502c11 ";

    @POST("/sts/v1.0/issueToken")
    @Headers({
            "Content-type: application/x-www-form-urlencoded",
            "Content-Length: 0",
            "Ocp-Apim-Subscription-Key: " + key
    })

    Call<String> getToken();

    @GET("https://eastasia.tts.speech.microsoft.com/cognitiveservices/voices/list")
    Call<ArrayList<Dictors>> getDictors(@Header("Authorization") String Token);

}
