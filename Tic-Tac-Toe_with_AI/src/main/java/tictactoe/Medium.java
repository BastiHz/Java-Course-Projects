package tictactoe;

class Medium extends Player {
    Medium(char c, Board board) {
        super(c, board);
    }

    @Override
    void makeMove() {
        // 1. If it already has two in a row and can win with one further move, it does so.
        // 2. If the opponent can win with their the next move, it plays the move necessary to block this.
        // 3. Otherwise, it makes a random move.
        System.out.println("Making move level \"medium\"");

        int enemyThreatIndex = -1;
        for (int move = 0; move < 9; move++) {
            if (board.spotIsEmpty(move)) {
                board.setAt(move, character);
                if (board.checkWin(character)) {
                    // (1) This is a winning move.
                    return;
                }
                board.setAt(move, enemyCharacter);
                if (board.checkWin(enemyCharacter)) {
                    enemyThreatIndex = move;
                }
                board.setAt(move, ' ');
            }
        }

        if (enemyThreatIndex > -1) {
            // (2) Block enemy.
            board.setAt(enemyThreatIndex, character);
        } else {
            // (3) Make random move.
            makeRandomMove();
        }
    }
}
