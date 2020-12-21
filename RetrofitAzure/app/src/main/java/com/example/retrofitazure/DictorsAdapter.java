package com.example.retrofitazure;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DictorsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Dictors> dictors;

    DictorsAdapter(Context context, ArrayList<Dictors> dictors) {
        this.context = context;
        this.dictors = dictors;
    }

    @Override
    public int getCount() {
        return dictors.size();
    }

    @Override
    public Object getItem(int position) {
        return dictors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dictors d = dictors.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.dictorlayout, parent, false);
        TextView textView = convertView.findViewById(R.id.dictorName);
        textView.setText(d.Name);
        ImageView countryView = convertView.findViewById(R.id.countryEmblem);
        ImageView genderView = convertView.findViewById(R.id.gender);
        switch (d.Gender) {
            case "Male":
                genderView.setImageResource(R.drawable.male);
                break;
            case "Female":
                genderView.setImageResource(R.drawable.female);
        }

        String country = d.Locale.substring(3,5).toLowerCase();
        try {
            InputStream inputStream = context.getAssets().open("CountryEmblems/"+ country + ".png");
            Drawable image = Drawable.createFromStream(inputStream, null);
            countryView.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
