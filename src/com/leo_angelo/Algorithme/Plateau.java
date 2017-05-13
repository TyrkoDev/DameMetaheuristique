package com.leo_angelo.Algorithme;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Plateau {
    private int[] chessBoard;
    private int size = 0;

    public Plateau(int dimension) {
        changeSize(dimension);
    }

    public Plateau(int[] colomns) {
        this.size = colomns.length;
        this.chessBoard = new int[this.size];
        for(int i = 0; i < this.size; i++) {
            this.chessBoard[i] = colomns[i];
        }
    }

    public void changeSize(int dimension) {
        this.size = dimension;
        ArrayList<Integer> listColumns = new ArrayList<>();

        for(int i = 0; i < this.size; i++)
            listColumns.add(i);

        this.chessBoard = new int[dimension];

        for(int i = 0; i < this.size; i++) {
            int choice = (int) (Math.random() * (listColumns.size()));
            this.chessBoard[i] = listColumns.get(choice);
            listColumns.remove(choice);
        }
    }

    public int[] getChessBoard() {
        int[] chessBoardReturn = new int[this.chessBoard.length];
        for(int i = 0; i<this.chessBoard.length; i++) {
            chessBoardReturn[i] = this.chessBoard[i];
        }
        return chessBoardReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                stringBuilder.append((chessBoard[i] == j ? 1 : 0) + " ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public int getSize() {
        return this.size;
    }
}
