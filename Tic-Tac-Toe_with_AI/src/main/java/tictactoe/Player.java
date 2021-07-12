package tictactoe;

import java.util.NoSuchElementException;
import java.util.Random;

abstract class Player {
    final char character;
    final char enemyCharacter;
    final Board board;
    private final Random random = new Random();

    Player(char c, Board board) {
        this.character = c;
        this.enemyCharacter = c == 'X' ? 'O' : 'X';
        this.board = board;
    }

    abstract void makeMove();

    void makeRandomMove() {
        int startIndex = random.nextInt(9);
        for (int i = 0; i < 9; i++) {
            int move = (startIndex + i) % 9;
            if (board.spotIsEmpty(move)) {
                board.setAt(move, character);
                return;
            }
        }
        throw new NoSuchElementException("No empty spot found. This should not have happened!");
    }
}
