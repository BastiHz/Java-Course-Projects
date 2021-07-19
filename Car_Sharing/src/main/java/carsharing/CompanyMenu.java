package carsharing;

import java.util.List;

class CompanyMenu extends Menu {

    private final String companyName;

    CompanyMenu(final String companyName) {
        this.companyName = companyName;
    }

    @Override
    void run() {
        int choice;
        while (true) {
            System.out.printf("'%s' company:%n", companyName);
            System.out.println("1. List cars");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (final NumberFormatException e) {
                System.out.println("\nPlease enter only numbers!\n");
                continue;
            }
            System.out.println();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    showCarList();
                    break;
                case 2:
                    createACar();
                    break;
                default:
                    System.out.println("Invalid input. Please type a number between 0 and 2.");
                    break;
            }
            System.out.println();
        }
    }

    private void showCarList() {
        final List<String> carNames = dataBaseManager.getCars(companyName);
        if (carNames.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            for (int i = 0; i < carNames.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, carNames.get(i));
            }
        }
    }

    private void createACar() {
        System.out.println("Enter the car name:");
        final String carName = scanner.nextLine();
        final boolean success = dataBaseManager.createCar(carName, companyName);
        if (success) {
            System.out.println("The car was added!");
        } else {
            System.out.println("Something went wrong.");
        }
    }
}
