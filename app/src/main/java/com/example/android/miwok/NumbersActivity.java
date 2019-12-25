package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer player;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                player.start();
            } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                player.pause();
                player.seekTo(0);
            } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                player.stop();
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("lutti", "one",R.drawable.number_one, R.raw.number_one));
        words.add(new Word("otiiko", "two",R.drawable.number_two, R.raw.number_two));
        words.add(new Word("tolookosu", "three",R.drawable.number_three, R.raw.number_three));
        words.add(new Word("oyyisa", "four",R.drawable.number_four, R.raw.number_four));
        words.add(new Word("massokka", "five",R.drawable.number_five, R.raw.number_five));
        words.add(new Word("temmokka", "six",R.drawable.number_six, R.raw.number_six));
        words.add(new Word("kenekaku", "seven",R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("kawinta", "eight",R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("wo’e", "nine",R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("na’aacha", "ten",R.drawable.number_ten, R.raw.number_ten));

        WordAdaptor itemsAdaptor = new WordAdaptor(this, words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(word.hasMedia()) {
                    releaseMediaPlayer();
                    int focusRequest = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(focusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        player = MediaPlayer.create(NumbersActivity.this,word.getMediaResourceId());
                        player.start();
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                releaseMediaPlayer();
                            }
                        });

                    }
                }
            }
        });


    }

    private void releaseMediaPlayer() {
        if(player!= null) {
            player.release();
            player = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }




}
