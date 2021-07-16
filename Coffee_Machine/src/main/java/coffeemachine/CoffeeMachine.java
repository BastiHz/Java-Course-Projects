package coffeemachine;

import java.util.Scanner;

public class CoffeeMachine {
    final static int waterPerCupOfEspresso = 250;
    final static int milkPerCupOfEspresso = 0;
    final static int beansPerCupOfEspresso = 16;
    final static int costPerCupOfEspresso = 4;

    final static int waterPerCupOfLatte = 350;
    final static int milkPerCupOfLatte = 75;
    final static int beansPerCupOfLatte = 20;
    final static int costPerCupOfLatte = 7;

    final static int waterPerCupOfCappuccino = 200;
    final static int milkPerCupOfCappuccino = 100;
    final static int beansPerCupOfCappuccino = 12;
    final static int costPerCupOfCappuccino = 6;

    int storedWater = 400;
    int storedMilk = 540;
    int storedBeans = 120;
    int storedCups = 9;
    int storedMoney = 550;

    int requiredWater;
    int requiredMilk;
    int requiredBeans;
    int requiredMoney;

    enum State {
        CHOOSING_ACTION, CHOOSING_PRODUCT,
        FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS,
        FINISHED
    }
    State state = State.CHOOSING_ACTION;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.mainLoop(scanner);
    }

    void mainLoop(Scanner scanner) {
        while (this.state != State.FINISHED) {
            if (this.state == State.CHOOSING_ACTION) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
            }
            doStuff(scanner.next());
        }
    }

    void doStuff(String input) {
        switch (this.state) {
            case CHOOSING_ACTION:
                switch (input) {
                    case "buy":
                        this.state = State.CHOOSING_PRODUCT;
                        System.out.print("\nWhat do you want to buy? 1 - espresso, 2 - latte,");
                        System.out.println(" 3 - cappuccino, back - to main menu:");
                        break;
                    case "fill":
                        this.state = State.FILLING_WATER;
                        System.out.println("\nWrite how many ml of water do you want to add:");
                        break;
                    case "remaining":
                        printRemaining();
                        break;
                    case "take":
                        takeMoney();
                        break;
                    case "exit":
                        this.state = State.FINISHED;
                        break;
                }
                break;
            case CHOOSING_PRODUCT:
                if (!input.equals("back")) {
                    if (checkIngredients(input)) {
                        brew();
                    }
                }
                this.state = State.CHOOSING_ACTION;
                break;
            case FILLING_WATER:
                fillWater(Integer.parseInt(input));
                break;
            case FILLING_MILK:
                fillMilk(Integer.parseInt(input));
                break;
            case FILLING_BEANS:
                fillBeans(Integer.parseInt(input));
                break;
            case FILLING_CUPS:
                fillCups(Integer.parseInt(input));
                break;
        }
    }

    boolean checkIngredients(String type) {
        switch (type) {
            case "1":
                requiredWater = waterPerCupOfEspresso;
                requiredMilk = milkPerCupOfEspresso;
                requiredBeans = beansPerCupOfEspresso;
                requiredMoney = costPerCupOfEspresso;
                break;
            case "2":
                requiredWater = waterPerCupOfLatte;
                requiredMilk = milkPerCupOfLatte;
                requiredBeans = beansPerCupOfLatte;
                requiredMoney = costPerCupOfLatte;
                break;
            case "3":
                requiredWater = waterPerCupOfCappuccino;
                requiredMilk = milkPerCupOfCappuccino;
                requiredBeans = beansPerCupOfCappuccino;
                requiredMoney = costPerCupOfCappuccino;
                break;
            default:
                return false;
        }
        if (storedWater < waterPerCupOfEspresso) {
            System.out.println("Sorry, not enough water!\n");
        } else if (storedMilk < requiredMilk) {
            System.out.println("Sorry, not enough milk!\n");
        } else if (storedBeans < requiredBeans) {
            System.out.println("Sorry, not enough beans!\n");
        } else if (storedCups < 1) {
            System.out.println("Sorry, not enough cups!\n");
        } else {
            System.out.println("I have enough resources, making you a coffee!\n");
            return true;
        }
        return false;
    }

    void brew() {
        storedWater -= requiredWater;
        storedMilk -= requiredMilk;
        storedBeans -= requiredBeans;
        storedMoney += requiredMoney;
        storedCups--;
    }

    void fillWater(int amount) {
        this.storedWater += amount;
        this.state = State.FILLING_MILK;
        System.out.println("Write how many ml of milk do you want to add:");
    }

    void fillMilk(int amount) {
        this.storedMilk += amount;
        this.state = State.FILLING_BEANS;
        System.out.println("Write how many grams of coffee beans do you want to add:");
    }

    void fillBeans(int amount) {
        this.storedBeans += amount;
        this.state = State.FILLING_CUPS;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
    }

    void fillCups(int amount) {
        this.storedCups += amount;
        this.state = State.CHOOSING_ACTION;
        System.out.println();
    }

    void takeMoney() {
        System.out.println("\nI gave you $" + this.storedMoney);
        this.storedMoney = 0;
    }

    void printRemaining() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(storedWater + " of water");
        System.out.println(storedMilk + " of milk");
        System.out.println(storedBeans + " of coffee beans");
        System.out.println(storedCups + " of disposable cups");
        System.out.println("$" + storedMoney + " of money\n");
    }
}
