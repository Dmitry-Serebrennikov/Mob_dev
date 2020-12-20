package com.company;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Weather {
    int id;
    String name;
    public Temperature main;

    static class Temperature {
        float temp;

        Temperature (float temp) {this.temp = temp;}
    }

    Weather(){};
    Weather(int id, String name, Temperature main){
        this.id = id;
        this.name = name;
        this.main = main;
    }

    float getTemperature(){
        return main.temp;
    }
    String print(){return name + ": " + String.format("%.1f", main.temp) + "°C";}
}


public class Main {

    static Weather[] w = new Weather[10];

    public static Weather getTemperatureByCity(String id) {
        Gson gson = new Gson();
        String API_URL = "https://api.openweathermap.org/data/2.5/weather?id="
                + id + "&appid=9d7a2d3697710e82698fb89e91674276&units=metric";


        Weather weather = new Weather();

        try {
            URL url = new URL(API_URL);
            InputStream stream = (InputStream) url.getContent();
            InputStreamReader reader = new InputStreamReader(stream);
            weather = gson.fromJson(reader, Weather.class);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return weather;
    }

    public static void sortByTemperature(Weather[] w){
        Arrays.sort(w, new Comparator<Weather>(){
            public int compare(Weather a, Weather b) {
                if (a.getTemperature() > b.getTemperature())
                    return -1;
                else if (a.getTemperature() < b.getTemperature())
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 0; i < w.length; i++){
            System.out.println(w[i].print());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("cities.txt"));
        int cnt = 0;
        while (sc.hasNextLine()){
            w[cnt++] = getTemperatureByCity(sc.nextLine());
        }
        sortByTemperature(w);
    }
}


/* *
    1. Рим Rome () (3169070)
	2. Стамбул Istanbul (745044)
	3. Москва Moscow (524894)
	4. Иркутск Irkutsk (2023469)
	5. Токио Tokyo (1850147)
	6. Хьюстон Houston (2646507)
	7. Лондон London (2643743)
	8. Канберра Canberra (2172517)
	9.  Шанхай Shanghai (1796236)
	10. Улан-Удэ Ulan-Ude (2014407)

* */