package tictactoe;

import java.util.Scanner;

class User extends Player {
    private final Scanner scanner = new Scanner(System.in);

    User(char c, Board board) {
        super(c, board);
    }

    @Override
    void makeMove() {
        int x;
        int y;
        while (true) {
            System.out.print("Enter the coordinates: ");
            String[] coordinates = scanner.nextLine().split(" ");
            if (coordinates.length != 2) {
                System.out.println("You should enter two numbers separated by a space!");
                continue;
            }
            y = convertCoordinate(coordinates[0]);
            if (y == -1) {
                continue;
            }
            x = convertCoordinate(coordinates[1]);
            if (x == -1) {
                continue;
            }
            if (!board.spotIsEmpty(x, y)) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                break;
            }
        }
        board.setAt(x, y, character);
    }

    private int convertCoordinate(String input) {
        if (input.length() > 1) {
            System.out.println("Coordinates should be single positive digits!");
            return -1;
        }
        char c = input.charAt(0);
        if (c < '1' || c > '3') {
            System.out.println("Coordinates should be between 1 and 3 (inclusive)!");
            return -1;
        }
        return Character.getNumericValue(c) - 1;  // User coordinates are [1, 3] but arrays are [0, 2]
    }
}
