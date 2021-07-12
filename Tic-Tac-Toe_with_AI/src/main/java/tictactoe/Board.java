package tictactoe;

import java.util.Arrays;

public class Board {
    // Storing the board as a 1d-array instead of a 2d-array makes many things simpler.
    // 0 1 2
    // 3 4 5
    // 6 7 8
    private final char[] board;
    {
        board = new char[9];
        Arrays.fill(board, ' ');
    }

    int xyToIndex(int x, int y){
        return y * 3 + x;
    }

    char getAt(int i) {
        return board[i];
    }

    char getAt(int x, int y) {
        return getAt(xyToIndex(x, y));
    }

    void setAt(int i, char c) {
        board[i] = c;
    }

    void setAt(int x, int y, char c) {
        setAt(xyToIndex(x, y), c);
    }

    boolean spotIsEmpty(int i){
        return board[i] == ' ';
    }

    boolean spotIsEmpty(int x, int y){
        return getAt(x, y) == ' ';
    }

    boolean checkWin(char c) {
        return board[0] == c && board[1] == c && board[2] == c      // first row
                || board[3] == c && board[4] == c && board[5] == c  // middle row
                || board[6] == c && board[7] == c && board[8] == c  // last row
                || board[0] == c && board[3] == c && board[6] == c  // first column
                || board[1] == c && board[4] == c && board[7] == c  // middle column
                || board[2] == c && board[5] == c && board[8] == c  // last column
                || board[0] == c && board[4] == c && board[8] == c  // main diagonal
                || board[2] == c && board[4] == c && board[6] == c; // secondary diagonal
    }

    void print() {
        System.out.println("---------");
        for (int y = 0; y < 3; y++) {
            System.out.print("| ");
            for (int x = 0; x < 3; x++) {
                System.out.print(getAt(x, y) + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
