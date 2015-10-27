package com.example.duyquoc.tictactoe;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duyquoc.tictactoe.R;

public class TwoPlayer extends Activity {

    // Representing the game state:
    private boolean noughtsTurn = false; // Who's turn is it? false=X true=O
    private char board[][] = new char[3][3]; // for now we will represent the board as an array of characters
    private MediaPlayer mp;
    private TextView player1ScoreTV;
    private TextView player2ScoreTV;
    private int player1Score;
    private int player2Score;
    private boolean player1Go = true;
    private TextView playerGoTV;
    private int totalStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_player);
        setupOnClickListeners();
        resetButtons();
        mp = MediaPlayer.create(this, R.raw.forest_surround);

        // get player1 and player2 score
        player1ScoreTV = (TextView) findViewById(R.id.player1ScoreTV);
        player2ScoreTV = (TextView) findViewById(R.id.player2ScoreTV);
        player1Score = 0;
        player2Score = 0;

        // get playerGoTV
        playerGoTV = (TextView) findViewById(R.id.playerGoTV);

        totalStep = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null) {
            mp.setLooping(true);
            mp.start();
        }
    }

    @Override
    protected  void onPause() {
        super.onPause();
        mp.pause();
    }

    public void newGame(View view) {
        noughtsTurn = false;
        board = new char[3][3];
        player1Go = true;
        playerGoTV.setText("Người chơi 1 đi");
        totalStep = 0;
        resetButtons();
    }


    private void resetButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                    }
                }
            }
        }
    }

    private boolean checkWin() {

        String messgage = "";
        char winner = '\0';
        if (checkWinner(board, 3, 'X')) {
            winner = 'X';
            messgage = "Người chơi 1 dành chiến thắng!";
            player1ScoreTV.setText(++player1Score + "");
        } else if (checkWinner(board, 3, 'O')) {
            winner = 'O';
            messgage = "Người chơi 2 dành chiến thắng!";
            player2ScoreTV.setText(++player2Score + "");
        }

        if (winner == '\0') {
            if (totalStep == 9) {
                messgage = "Hai người hoà";
                playerGoTV.setText("");
                Toast.makeText(this, messgage, Toast.LENGTH_SHORT).show();
                totalStep = 0;
            }
            return false; // nobody won
        } else {
            // display winner
            Toast.makeText(this, messgage, Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    private boolean checkWinner(char[][] board, int size, char player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diag
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diag
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        return false; // nobody won
    }


    private void disableButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }


    private void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    View V = R.getChildAt(x); // In our case this will be each button on the grid
                    V.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }

    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            totalStep++;
            if (view instanceof Button) {
                if (player1Go) {
                    playerGoTV.setText("Người chơi 2 đi");
                    player1Go = false;
                } else {
                    playerGoTV.setText("Người chơi 1 đi");
                    player1Go = true;
                }
                Button B = (Button) view;
                board[x][y] = noughtsTurn ? 'O' : 'X';
                B.setText(noughtsTurn ? "O" : "X");
                B.setEnabled(false);
                noughtsTurn = !noughtsTurn;

                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                    playerGoTV.setText("");
                }
            }
        }
    }
/*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
            for (int y = 0; y < T.getChildCount(); y++) {
                if (T.getChildAt(y) instanceof TableRow) {
                    TableRow R = (TableRow) T.getChildAt(y);
                    for (int x = 0; x < R.getChildCount(); x++) {
                        Button B = (Button) R.getChildAt(x);
                        B.setHeight(50);
                        B.setWidth(50);
                    }
                }
            }
        }
    }
*/
}