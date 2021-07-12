package tictactoe;

class Game {
    private final Player[] players = new Player[2];
    private final Board board = new Board();

    public Game(String player1, String player2) {
        this.players[0] = createPlayer(player1, 'X');
        this.players[1] = createPlayer(player2, 'O');
    }

    Player createPlayer(String type, char c) {
        switch (type) {
            case "user":
                return new User(c, board);
            case "easy":
                return new Easy(c, board);
            case "medium":
                return new Medium(c, board);
            case "hard":
                return new Hard(c, board);
            default:
                throw new IllegalArgumentException("Unknown player type");
        }
    }

    void run() {
        board.print();
        for (int i = 0; i < 9; i++) {
            Player currentPlayer = players[i % 2];
            currentPlayer.makeMove();
            board.print();
            if (board.checkWin(currentPlayer.character)) {
                System.out.println(currentPlayer.character + " wins");
                return;
            }
        }
        System.out.println("Draw");
    }
}
