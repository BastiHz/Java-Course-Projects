package carsharing;

import java.util.Scanner;

abstract class Menu {

    protected static final Scanner scanner = new Scanner(System.in);
    protected static final DataBaseManager dataBaseManager = DataBaseManager.getInstance();

    void run() {}
}
