package com.example.duyquoc.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Scanner;


public class MainActivity extends Activity {

    private MediaPlayer mp;
    private Button continueBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mp = MediaPlayer.create(this, R.raw.dota_2);
        continueBT = (Button) findViewById(R.id.continueBT);

        readSaveFile();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null) {
            mp.setLooping(true);
            mp.start();
        }

    }

    private void readSaveFile() {
        try {
            Scanner scan = new Scanner(
                    openFileInput("save.txt"));
            if (scan.hasNextLine()) {
                continueBT.setVisibility(View.VISIBLE);
            }
            scan.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void continueGame(View v) {
        Intent intent = new Intent(this, ContinueGame.class);

        startActivity(intent);
    }


    public void singlePlayer(View view) {
        Intent intent = new Intent(this, ChoseLevel.class);

        startActivity(intent);
    }


    public void twoPlayer(View view) {
        Intent intent = new Intent(this, TwoPlayer.class);

        startActivity(intent);
    }

    public void exit(View view) {
        finish();
    }
}