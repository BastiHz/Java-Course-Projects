package tictactoe;

class Easy extends Player {
    Easy(char c, Board board) {
        super(c, board);
    }

    @Override
    void makeMove() {
        System.out.println("Making move level \"easy\"");
        makeRandomMove();
    }
}
