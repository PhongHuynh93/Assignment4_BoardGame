package com.example.duyquoc.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class EasyAI extends AI {
    protected String myMark;
    protected String oppMark;
    protected String[][] board;
    private int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
            {0, 1}, {1, 0}, {1, 2}, {2, 1}};

    EasyAI(String marker) {
        this.myMark = marker;
        this.oppMark = (myMark == "X") ? "O" : "X";
    }

    public int[] move(GameBoard theboard, String marker) {
        board = theboard.getBoard();
        for (int[] move : preferredMoves) {
            int row = move[0];
            int col = move[1];
            if(board[row][col] == "")
                return move;
        }
        return null;
    }

}
