package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    private MediaPlayer mPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mPlayer.pause();
                mPlayer.seekTo(0);
            } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mPlayer.stop();
                releaseMediaPlayer();
            }
        }
    };

    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container, false);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("weṭeṭṭi", "red", R.drawable.color_red,R.raw.color_red));
        words.add(new Word("chokokki", "green", R.drawable.color_green,R.raw.color_green));
        words.add(new Word("ṭakaakki", "brown", R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("ṭopoppi", "gray", R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("kululli", "black", R.drawable.color_black,R.raw.color_black));
        words.add(new Word("kelelli", "white", R.drawable.color_white,R.raw.color_white));
        words.add(new Word("ṭopiisә", "dusty yellow", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("chiwiiṭә", "mustard yellow", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdaptor itemsAdaptor = new WordAdaptor(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(word.hasMedia()) {
                    releaseMediaPlayer();
                    int requestFocus = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(requestFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mPlayer = MediaPlayer.create(getActivity(),word.getMediaResourceId());
                        mPlayer.start();
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                releaseMediaPlayer();
                            }
                        });
                    }
                }
            }
        });

        return rootView;
    }
    private void releaseMediaPlayer() {
        if(mPlayer!= null) {
            mPlayer.release();
            mPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
