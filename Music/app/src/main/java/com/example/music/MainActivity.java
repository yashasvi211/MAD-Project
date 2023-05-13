package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private Button next;
    private Button back;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int[] songs = {R.raw.monkey, R.raw.calm, R.raw.go};
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(this, songs[currentPosition]); // Start with the first song in the array

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release(); // ??

                currentPosition = (currentPosition - 1 + songs.length) % songs.length;
                int previousSongResourceId = songs[currentPosition];
                mediaPlayer = MediaPlayer.create(MainActivity.this, previousSongResourceId);

                mediaPlayer.start();
                play.setText("Pause");

                seekBar.setMax(mediaPlayer.getDuration());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release(); // Release the MediaPlayer resources

                currentPosition = (currentPosition + 1) % songs.length;
                int nextSongResourceId = songs[currentPosition];
                mediaPlayer = MediaPlayer.create(MainActivity.this, nextSongResourceId);

                mediaPlayer.start();
                play.setText("Pause");

                seekBar.setMax(mediaPlayer.getDuration());
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play.setText("Play");
                } else {
                    mediaPlayer.start();
                    play.setText("Pause");
                }
            }
        });
    }


}
