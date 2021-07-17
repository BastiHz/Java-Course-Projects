package guesstheanimal;

import java.time.LocalTime;


class Menu {

    private final KnowledgeTree knowledgeTree;
    private final GuessingGame guessingGame;

    Menu(final String mapperType) {
        this.knowledgeTree = new KnowledgeTree(mapperType);
        this.guessingGame = new GuessingGame(knowledgeTree);
    }

    void run() {
        greet();
        knowledgeTree.load();
        TextHelper.println("welcome");
        menu();
        knowledgeTree.save();
        TextHelper.println("farewell");
    }

    private void menu() {
        while (true) {
            System.out.println();
            TextHelper.println("menu.title");
            TextHelper.println("menu.entry.play");
            TextHelper.println("menu.entry.list");
            TextHelper.println("menu.entry.search");
            TextHelper.println("menu.entry.statistics");
            TextHelper.println("menu.entry.print");
            TextHelper.println("menu.entry.exit");
            final String choice = TextHelper.scanner.nextLine();
            if ("0".equals(choice)) {
                return;
            }
            switch (choice) {
                case "1":
                    guessingGame.play();
                    break;
                case "2":
                    knowledgeTree.printAllAnimalNames();
                    break;
                case "3":
                    knowledgeTree.printAllFactsForAnimal();
                    break;
                case "4":
                    knowledgeTree.printStatistics();
                    break;
                case "5":
                    knowledgeTree.print();
                    break;
                default:
                    TextHelper.println("menu.error");
                    break;
            }
        }
    }

    private static void greet() {
        final int currentHour = LocalTime.now().getHour();
        if (currentHour < 3) {
            TextHelper.println("greeting.night");
        } else if (currentHour < 5) {
            TextHelper.println("greeting.early");
        } else if (currentHour < 12) {
            TextHelper.println("greeting.morning");
        } else if (currentHour < 18) {
            TextHelper.println("greeting.afternoon");
        } else {
            TextHelper.println("greeting.evening");
        }
        System.out.println();
    }
}
