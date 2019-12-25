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
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("minto wuksus", "Where are you going?", Word.NO_IMAGE_STATE, R.raw.phrase_where_are_you_going));
        words.add(new Word("tinnә oyaase'nә", "What is your name", Word.NO_IMAGE_STATE, R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...", "My name is...", Word.NO_IMAGE_STATE, R.raw.phrase_my_name_is));
        words.add(new Word("michәksәs?", "How are you feeling?", Word.NO_IMAGE_STATE, R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit", "I’m feeling good", Word.NO_IMAGE_STATE, R.raw.phrase_im_feeling_good));
        words.add(new Word("әәnәs'aa?", "Are you coming?", Word.NO_IMAGE_STATE, R.raw.phrase_are_you_coming));
        words.add(new Word("hәә’ әәnәm", "Yes, I’m coming.", Word.NO_IMAGE_STATE, R.raw.phrase_yes_im_coming));
        words.add(new Word("әәnәm", "I’m coming", Word.NO_IMAGE_STATE, R.raw.phrase_im_coming));
        words.add(new Word("yoowutis", "Let’s go", Word.NO_IMAGE_STATE, R.raw.phrase_lets_go));
        words.add(new Word("әnni'nem", "Come here.", Word.NO_IMAGE_STATE, R.raw.phrase_come_here));

        WordAdaptor itemsAdaptor = new WordAdaptor(getActivity(), words, R.color.category_phrases);
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
