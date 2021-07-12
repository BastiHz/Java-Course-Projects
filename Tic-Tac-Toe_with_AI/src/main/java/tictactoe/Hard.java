package tictactoe;

public class Hard extends Player {
    Hard(char c, Board board) {
        super(c, board);
    }

    @Override
    void makeMove() {
        // Use the minimax algorithm to find the best possible move.
        // This should always result in a win or a draw.
        System.out.println("Making move level \"hard\"");
        int bestMove = 0;
        int bestScore = -10;
        for (int move = 0; move < 9; move++) {
            if (board.spotIsEmpty(move)) {
                int score = minimax(move, true);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
        board.setAt(bestMove, character);
    }

    int minimax(int move, boolean myTurn) {
        if (myTurn) {
            board.setAt(move, character);
            if (board.checkWin(character)) {
                board.setAt(move, ' ');
                return 1;
            }
        } else {
            board.setAt(move, enemyCharacter);
            if (board.checkWin(enemyCharacter)) {
                board.setAt(move, ' ');
                return -1;
            }
        }

        int bestMove = -1;
        int bestScore = myTurn ? 10 : -10;
        for (int nextMove = 0; nextMove < 9; nextMove++) {
            if (board.spotIsEmpty(nextMove)) {
                int score = minimax(nextMove, !myTurn);
                if (myTurn && score < bestScore || !myTurn && score > bestScore) {
                    bestScore = score;
                    bestMove = nextMove;
                }
            }
        }

        board.setAt(move, ' ');
        if (bestMove == -1) {
            // No more available empty spots.
            return 0;
        }
        return bestScore;
    }
}
