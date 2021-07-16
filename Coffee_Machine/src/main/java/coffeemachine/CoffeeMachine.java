package coffeemachine;

import java.util.Scanner;

public class CoffeeMachine {

    private static final int WATER_PER_CUP_OF_ESPRESSO = 250;
    private static final int MILK_PER_CUP_OF_ESPRESSO = 0;
    private static final int BEANS_PER_CUP_OF_ESPRESSO = 16;
    private static final int COST_PER_CUP_OF_ESPRESSO = 4;

    private static final int WATER_PER_CUP_OF_LATTE = 350;
    private static final int MILK_PER_CUP_OF_LATTE = 75;
    private static final int BEANS_PER_CUP_OF_LATTE = 20;
    private static final int COST_PER_CUP_OF_LATTE = 7;

    private static final int WATER_PER_CUP_OF_CAPPUCCINO = 200;
    private static final int MILK_PER_CUP_OF_CAPPUCCINO = 100;
    private static final int BEANS_PER_CUP_OF_CAPPUCCINO = 12;
    private static final int COST_PER_CUP_OF_CAPPUCCINO = 6;

    private int storedWater = 400;
    private int storedMilk = 540;
    private int storedBeans = 120;
    private int storedCups = 9;
    private int storedMoney = 120;

    private int requiredWater;
    private int requiredMilk;
    private int requiredBeans;
    private int requiredMoney;

    private enum State {
        CHOOSING_ACTION, CHOOSING_PRODUCT,
        FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS,
        FINISHED
    }
    private State state = State.CHOOSING_ACTION;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        new CoffeeMachine().run();
    }

    void run() {
        while (this.state != State.FINISHED) {
            if (this.state == State.CHOOSING_ACTION) {
                System.out.print("\nChoose an action (buy, remaining, fill, take, exit): ");
            }
            doStuff(scanner.next());
        }
    }

    void doStuff(final String input) {
        switch (this.state) {
            case CHOOSING_ACTION:
                switch (input) {
                    case "buy":
                        this.state = State.CHOOSING_PRODUCT;
                        System.out.print("What do you want to buy? (espresso, latte, cappuccino, back): ");
                        break;
                    case "remaining":
                        printRemaining();
                        break;
                    case "fill":
                        this.state = State.FILLING_WATER;
                        System.out.print("How many ml of water do you want to add? ");
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
                if (!"back".equals(input) && checkIngredients(input)) {
                    brew();
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

    boolean checkIngredients(final String type) {
        switch (type) {
            case "espresso":
                requiredWater = WATER_PER_CUP_OF_ESPRESSO;
                requiredMilk = MILK_PER_CUP_OF_ESPRESSO;
                requiredBeans = BEANS_PER_CUP_OF_ESPRESSO;
                requiredMoney = COST_PER_CUP_OF_ESPRESSO;
                break;
            case "latte":
                requiredWater = WATER_PER_CUP_OF_LATTE;
                requiredMilk = MILK_PER_CUP_OF_LATTE;
                requiredBeans = BEANS_PER_CUP_OF_LATTE;
                requiredMoney = COST_PER_CUP_OF_LATTE;
                break;
            case "cappuccino":
                requiredWater = WATER_PER_CUP_OF_CAPPUCCINO;
                requiredMilk = MILK_PER_CUP_OF_CAPPUCCINO;
                requiredBeans = BEANS_PER_CUP_OF_CAPPUCCINO;
                requiredMoney = COST_PER_CUP_OF_CAPPUCCINO;
                break;
            default:
                return false;
        }
        boolean hasEnoughResources = true;
        if (storedWater < requiredWater) {
            System.out.println("Sorry, not enough water!");
            hasEnoughResources = false;
        }
        if (storedMilk < requiredMilk) {
            System.out.println("Sorry, not enough milk!");
            hasEnoughResources = false;
        }
        if (storedBeans < requiredBeans) {
            System.out.println("Sorry, not enough beans!");
            hasEnoughResources = false;
        }
        if (storedCups < 1) {
            System.out.println("Sorry, not enough cups!");
            hasEnoughResources = false;
        }
        return hasEnoughResources;
    }

    void brew() {
        System.out.println("Brewing you a coffee... done. Enjoy!");
        storedWater -= requiredWater;
        storedMilk -= requiredMilk;
        storedBeans -= requiredBeans;
        storedCups--;
        storedMoney += requiredMoney;
    }

    void fillWater(final int amount) {
        this.storedWater += amount;
        this.state = State.FILLING_MILK;
        System.out.print("How many ml of milk do you want to add? ");
    }

    void fillMilk(final int amount) {
        this.storedMilk += amount;
        this.state = State.FILLING_BEANS;
        System.out.print("How many grams of coffee beans do you want to add? ");
    }

    void fillBeans(final int amount) {
        this.storedBeans += amount;
        this.state = State.FILLING_CUPS;
        System.out.print("How many disposable cups do you want to add? ");
    }

    void fillCups(final int amount) {
        this.storedCups += amount;
        this.state = State.CHOOSING_ACTION;
    }

    void takeMoney() {
        System.out.println("\nIt gave you $" + this.storedMoney + ".");
        this.storedMoney = 0;
    }

    void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.println(storedWater + " ml of water");
        System.out.println(storedMilk + " ml of milk");
        System.out.println(storedBeans + " g of coffee beans");
        System.out.println(storedCups + " disposable cups");
        System.out.println("$" + storedMoney);
    }
}
