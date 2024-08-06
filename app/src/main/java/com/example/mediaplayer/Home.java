package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Button playButton;
    private Button pauseButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        seekBar = findViewById(R.id.seekBar);
        imageView = findViewById(R.id.imageView);

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
        seekBar.setMax(mediaPlayer.getDuration());

        playButton.setOnClickListener(v -> {
            mediaPlayer.start();
            updateSeekBar();
        });

        pauseButton.setOnClickListener(v -> mediaPlayer.pause());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> handler.removeCallbacks(updateRunnable));
    }

    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(updateRunnable, 1000);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateRunnable);
    }
}
