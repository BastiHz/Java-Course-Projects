package carsharing;

import java.util.List;

class MainMenu extends Menu {

    @Override
    void run() {
        int choice;
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch(final NumberFormatException e) {
                System.out.println("\nPlease enter only numbers!\n");
                continue;
            }
            System.out.println();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    new ManagerMenu().run();
                    break;
                case 2:
                    logInAsACustomer();
                    break;
                case 3:
                    createACustomer();
                    break;
                default:
                    System.out.println("Invalid input. Please type a number between 0 and 3.");
                    break;
            }
            System.out.println();
        }
    }

    private static void logInAsACustomer() {
        final List<String> customers = dataBaseManager.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println("Customer list:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println(i + 1 + ". " + customers.get(i));
            }
            System.out.println("0. Back");
            final int customerChoice;
            try {
                customerChoice = Integer.parseInt(scanner.nextLine());
            } catch(final NumberFormatException e) {
                System.out.println("\nPlease enter only numbers!");
                return;
            }
            System.out.println();
            if (customerChoice > 0 && customerChoice <= customers.size()) {
                new CustomerMenu(customers.get(customerChoice - 1)).run();
            } else if (customerChoice != 0){
                System.out.println("Invalid input.");
            }
        }
    }

    private static void createACustomer() {
        System.out.println("Enter the customer name:");
        final String name = scanner.nextLine();
        final boolean success = dataBaseManager.createCustomer(name);
        if (success) {
            System.out.println("The customer was added!");
        } else {
            System.out.println("Something went wrong.");
        }
    }
}
