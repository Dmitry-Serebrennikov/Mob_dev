package com.argent.azuretranslator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AzureTranslationAPI {
    String API_URL = "https://api.cognitive.microsofttranslator.com";
    String key = "3e985f30c7e8447296a145b9d7e97f9c";
    String region = "eastasia";

    @GET("/languages?api-version=3.0&scope=translation")
    Call<LanguagesResponse> getLanguages();

    @POST("/translate?api-version=3.0")
    @Headers({
            "Content-Type: application/json",
            "Ocp-Apim-Subscription-Key: " + key,
            "Ocp-Apim-Subscription-Region: " + region,
    })
    Call<List<TranslatorResponse>> translate(@Body String body, @Query("to") String language);
}
