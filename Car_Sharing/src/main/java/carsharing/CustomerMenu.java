package carsharing;

import java.util.List;

class CustomerMenu extends Menu {

    private final String customerName;
    private final String[] carAndCompany;

    CustomerMenu(final String customerName) {
        this.customerName = customerName;
        this.carAndCompany = dataBaseManager.getRentedCarAndCompany(customerName);
    }

    @Override
    void run() {
        int choice;
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
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
                    rentACar();
                    break;
                case 2:
                    returnARentedCar();
                    break;
                case 3:
                    showMyRentedCar();
                    break;
                default:
                    System.out.println("Invalid input. Please type a number between 0 and 3.");
                    break;
            }
            System.out.println();
        }
    }

    private void rentACar() {
        if (carAndCompany[0] != null) {
            System.out.println("You've already rented a car!");
            return;
        }
        final List<String> companies = dataBaseManager.getCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println(i + 1 + ". " + companies.get(i));
            }
            System.out.println("0. Back");
            final int companyChoice;
            try {
                companyChoice = Integer.parseInt(scanner.nextLine());
            } catch (final NumberFormatException e) {
                System.out.println("\nPlease enter only numbers!");
                return;
            }
            System.out.println();
            if (companyChoice > 0 && companyChoice <= companies.size()) {
                final String companyName = companies.get(companyChoice - 1);
                final List<String> cars = dataBaseManager.getCars(companyName);
                if (cars.isEmpty()) {
                    System.out.println("The car list is empty!");
                } else {
                    System.out.println("Choose a car:");
                    for (int i = 0; i < cars.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, cars.get(i));
                    }
                    System.out.println("0. Back");
                    final int carChoice;
                    try {
                        carChoice = Integer.parseInt(scanner.nextLine());
                    } catch (final NumberFormatException e) {
                        System.out.println("\nPlease enter only numbers!");
                        return;
                    }
                    System.out.println();
                    if (carChoice > 0 && carChoice <= cars.size()) {
                        final String carName = cars.get(carChoice - 1);
                        final boolean success = dataBaseManager.rentCar(customerName, carName);
                        if (success) {
                            carAndCompany[0] = carName;
                            carAndCompany[1] = companyName;
                            System.out.printf("You rented '%s'%n", carName);
                        } else {
                            System.out.println("Something went wrong.");
                        }
                    } else if (carChoice != 0) {
                        System.out.println("Invalid input.");
                    }
                }
            } else if (companyChoice != 0) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void returnARentedCar() {
        if (carAndCompany[0] == null) {
            System.out.println("You didn't rent a car!");
        } else {
            final boolean success = dataBaseManager.returnCar(customerName);
            if (success) {
                carAndCompany[0] = null;
                carAndCompany[1] = null;
                System.out.println("You've returned a rented car!");
            } else {
                System.out.println("Something went wrong.");
            }
        }
    }

    private void showMyRentedCar() {
        if (carAndCompany[0] == null) {
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println("Your rented car:");
            System.out.println(carAndCompany[0]);
            System.out.println("Company:");
            System.out.println(carAndCompany[1]);
        }
    }
}
