package tictactoe;

import java.util.Scanner;

public class Main {
    static String[] validPlayers = {"user", "easy", "medium", "hard"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("Input command: ");
            String[] commands = scanner.nextLine().split(" ");
            switch (commands[0]) {
                case "exit":
                    running = false;
                    break;
                case "start":
                    if (commands.length == 3) {
                        String player1 = commands[1];
                        String player2 = commands[2];
                        if (checkPlayer(player1) && checkPlayer(player2)) {
                            Game game = new Game(player1, player2);
                            game.run();
                            break;
                        }
                    }
                default:
                    System.out.println("Bad parameters!");
            }
        }
    }

    private static boolean checkPlayer(String type) {
        for (String validType : validPlayers) {
            if (type.equals(validType)) {
                return true;
            }
        }
        return false;
    }
}
