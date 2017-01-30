package com.example.android.miwok;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * Created by Arnav on 29-12-2016.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private MediaPlayer mediaPlayer;
    private int mcolor;

    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }

    public WordAdapter(Activity context, ArrayList<Word> Word, int color) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, Word);
        mcolor = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Word currentWord = getItem(position);

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView englishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        englishTextView.setText(currentWord.getDefaultTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else if (!currentWord.hasImage()) {
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        final View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mcolor);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


        textContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soundId = currentWord.getSoundId();
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(getContext(), soundId);
                Toast.makeText(getContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getContext(), "Sound Played", Toast.LENGTH_SHORT).show();
                        releaseMediaPlayer();
                    }
                });
            }
        });


        AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    mediaPlayer.stop();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                    mediaPlayer.pause();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mediaPlayer.pause();
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.start();
                }
            }
        };


        return listItemView;
    }
}
