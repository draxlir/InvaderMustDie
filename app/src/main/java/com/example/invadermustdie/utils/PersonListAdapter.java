package com.example.invadermustdie.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.invadermustdie.R;
import com.example.invadermustdie.domain.Score;

import java.util.ArrayList;

// Display a row of the history
public class PersonListAdapter extends ArrayAdapter<Score> {

    private final Context mContext;
    int myRessource;
    public PersonListAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        mContext = context;
        myRessource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        int score = getItem(position).getScore();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(myRessource, parent, false);

        TextView tDate = (TextView) convertView.findViewById(R.id.date);
        TextView tScore = (TextView) convertView.findViewById(R.id.score);

        tDate.setText(date);
        tScore.setText(String.valueOf(score));
        return convertView;
    }
}
