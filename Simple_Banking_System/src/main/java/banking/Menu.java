package banking;

import java.util.Scanner;

class Menu {

    protected final Scanner scanner = new Scanner(System.in);
    protected final AccountController accountController;

    Menu() {
        accountController = new AccountController();
    }

    void run() {
        runMainMenu();
        System.out.println("\nBye!");
    }

    void runMainMenu() {
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            final String input = scanner.nextLine();
            switch (input) {
                case "0":
                    return;
                case "1":
                    final String[] cardAndPin = accountController.createAccount();
                    System.out.println("\nYour card has been created");
                    System.out.println("Your card number:");
                    System.out.println(cardAndPin[0]);
                    System.out.println("Your PIN:");
                    System.out.println(cardAndPin[1] + "\n");
                    break;
                case "2":
                    System.out.println("\nEnter your card number:");
                    final String cardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    final String pin = scanner.nextLine();
                    if (accountController.checkCardAndPin(cardNumber, pin)) {
                        if (runAccountMenu(cardNumber)) {
                            return;
                        }
                    } else {
                        System.out.println("\nWrong card number or PIN!\n");
                    }
                    break;
                default:
                    System.out.println("\nInvalid input. Please type a number between 0 and 2.\n");
                    break;
            }
        }
    }

    boolean runAccountMenu(final String cardNumber) {
        System.out.println("\nYou have successfully logged in!");
        long amount;
        while (true) {
            System.out.println("\n1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            final String input = scanner.nextLine();
            switch (input) {
                case "0":
                    return true;
                case "1":
                    System.out.println("\nBalance: " + accountController.getBalance(cardNumber));
                    break;
                case "2":
                    System.out.println("\nEnter the income:");
                    amount = scanner.nextLong();
                    scanner.nextLine();  // consume leftover newline
                    if (amount <= 0) {
                        System.out.println("The amount must be positive!");
                    }
                    accountController.addIncome(cardNumber, amount);
                    break;
                case "3":
                    System.out.println("\nTransfer\nEnter card number:");
                    final String targetCardNumber = scanner.nextLine();
                    if (cardNumber.equals(targetCardNumber)) {
                        System.out.println("You can't transfer money to the same account!");
                        break;
                    }
                    if (accountController.checkInvalidTargetCardNumber(targetCardNumber)) {
                        break;
                    }
                    System.out.println("Enter how much money you want to transfer:");
                    amount = scanner.nextLong();
                    scanner.nextLine();  // consume leftover newline
                    if (amount <= 0) {
                        System.out.println("The amount must be positive!");
                    } else if (amount > accountController.getBalance(cardNumber)) {
                        System.out.println("Not enough money!");
                    } else {
                        accountController.transfer(cardNumber, targetCardNumber, amount);
                    }
                    break;
                case "4":
                    accountController.closeAccount(cardNumber);
                    return false;
                case "5":
                    System.out.println("\nYou have successfully logged out!\n");
                    return false;
                default:
                    System.out.println("\nInvalid input. Please type a number between 0 and 2.");
                    break;
            }
        }
    }
}
