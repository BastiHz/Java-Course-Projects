package carsharing;

import java.util.List;

class ManagerMenu extends Menu {

    @Override
    void run() {
        int choice;
        while (true) {
            System.out.println("1. List companies");
            System.out.println("2. Create a company");
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
                    showCompanyList();
                    break;
                case 2:
                    createACompany();
                    break;
                default:
                    System.out.println("Invalid input. Please type a number between 0 and 2.");
                    break;
            }
            System.out.println();
        }
    }

    private static void showCompanyList() {
        final List<String> companies = dataBaseManager.getCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
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
                new CompanyMenu(companies.get(companyChoice - 1)).run();
            } else if (companyChoice != 0) {
                System.out.println("Invalid input.");
            }
        }
    }

    private static void createACompany() {
        System.out.println("Enter the company name:");
        final String name = scanner.nextLine();
        final boolean success = dataBaseManager.createCompany(name);
        if (success) {
            System.out.println("The company was created!");
        } else {
            System.out.println("Something went wrong.");
        }
    }
}
