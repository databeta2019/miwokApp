package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdaptor extends ArrayAdapter<Word> {
    private int mColor;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);
        // List View
        LinearLayout linearLayout = (LinearLayout)listView.findViewById(R.id.linear_text);
        int color = ContextCompat.getColor(getContext(),mColor);
        linearLayout.setBackgroundColor(color);
        ImageView iconImage = (ImageView) listView.findViewById(R.id.icon);
        iconImage.setBackgroundColor(color);

        // Image View
        ImageView imageView = (ImageView) listView.findViewById(R.id.image_view);
        if ((currentWord.hasImage())) {
            // Set Text
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else
            imageView.setVisibility(View.GONE);


        // First View
        TextView milokWordView = (TextView) listView.findViewById(R.id.miwok_text_view);
        // Set Text
        milokWordView.setText(currentWord.getMiwokTranslation());

        // Second View
        TextView defaultWordView = (TextView) listView.findViewById(R.id.default_text_view);
        // Set Text
        defaultWordView.setText(currentWord.getDefaultTranslation());

        return listView;
    }



    public WordAdaptor(Activity context, ArrayList<Word> words, int color) {
        super(context,0,words);
        mColor =color;
    }
}
