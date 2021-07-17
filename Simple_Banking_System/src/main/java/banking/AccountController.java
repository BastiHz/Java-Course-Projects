package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AccountController {

    private final Random random = new Random();
    private final SQLiteDataSource dataSource = new SQLiteDataSource();
    private final Set<String> cardNumbers = new HashSet<>();

    AccountController() {
        dataSource.setUrl("jdbc:sqlite:database.s3db");

        try (final Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS card (" +
                "id INTEGER PRIMARY KEY," +
                "number TEXT NOT NULL," +
                "pin TEXT NOT NULL," +
                "balance INTEGER NOT NULL DEFAULT 0);"
            );
            final ResultSet numbers = statement.executeQuery("SELECT number FROM card;");
            while (numbers.next()) {
                cardNumbers.add(numbers.getString("number"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    String[] createAccount() {
        final String cardNumber = createCardNumber();
        final String pin = String.format("%04d", random.nextInt(10_000));
        cardNumbers.add(cardNumber);

        final String sql = "INSERT INTO card (number, pin) VALUES (?, ?);";
        try (final PreparedStatement pStatement = dataSource.getConnection().prepareStatement(sql)) {
            pStatement.setString(1, cardNumber);
            pStatement.setString(2, pin);
            pStatement.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return new String[] {cardNumber, pin};
    }

    private String createCardNumber() {
        // 6 iin + 9 account number + 1 check digit = 16 digits
        final char[] iin = "400000".toCharArray();
        final char[] cardNumber = new char[16];
        System.arraycopy(iin, 0, cardNumber, 0, iin.length);
        String cardNumberStr;
        do {
            for (int i = iin.length; i < cardNumber.length - 1; i++) {
                cardNumber[i] = (char) ('0' + random.nextInt(10));
            }
            cardNumber[cardNumber.length - 1] = getCheckDigit(cardNumber);
            cardNumberStr = String.valueOf(cardNumber);
        } while (cardNumbers.contains(cardNumberStr));
        return cardNumberStr;
    }

    private static char getCheckDigit(final char[] number) {
        // Luhn algorithm
        int sum = 0;
        for (int i = 0; i < number.length - 1; i++) {
            int x = Character.getNumericValue(number[i]);
            if (i % 2 == 0) {
                x += x;
                if (x > 9) {
                    x -= 9;
                }
            }
            sum += x;
        }
        if (sum % 10 == 0) {
            return '0';
        } else {
            final int nextMultipleOf10 = (sum / 10 + 1) * 10;
            return (char) ('0' + nextMultipleOf10 - sum);
        }
    }

    boolean checkCardAndPin(final String cardNumber, final String pin) {
        if (!cardNumbers.contains(cardNumber)) {
            return false;
        }
        final String sql = "SELECT pin FROM card WHERE number = ?;";
        try (final PreparedStatement pStatement = dataSource.getConnection().prepareStatement(sql)) {
            pStatement.setString(1, cardNumber);
            final ResultSet pins = pStatement.executeQuery();
            return pin.equals(pins.getString("pin"));
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    long getBalance(final String cardNumber) {
        final String sql = "SELECT balance FROM card WHERE number = ?;";
        try (final PreparedStatement pStatement = dataSource.getConnection().prepareStatement(sql)) {
            pStatement.setString(1, cardNumber);
            final ResultSet resultBalance = pStatement.executeQuery();
            return resultBalance.getLong(1);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void addIncome(final String cardNumber, final long amount) {
        final String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (final PreparedStatement pStatement = dataSource.getConnection().prepareStatement(sql)) {
            pStatement.setLong(1, amount);
            pStatement.setString(2, cardNumber);
            pStatement.executeUpdate();
            System.out.println("Income was added!");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    void closeAccount(final String cardNumber) {
        final String sql = "DELETE FROM card WHERE number = ?";
        try (final PreparedStatement pStatement = dataSource.getConnection().prepareStatement(sql)) {
            pStatement.setString(1, cardNumber);
            pStatement.executeUpdate();
            cardNumbers.remove(cardNumber);
            System.out.println("\nThe account has been closed!\n");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    boolean checkInvalidTargetCardNumber(final String targetCardNumber) {
        final char[] digits = targetCardNumber.toCharArray();
        final char checkDigit = getCheckDigit(digits);
        if (checkDigit != digits[digits.length - 1]) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return true;
        }
        if (cardNumbers.contains(targetCardNumber)) {
            return false;
        }
        System.out.println("Such a card does not exist.");
        return true;
    }

    void transfer(final String fromCardNumber, final String toCardNumber, final long amount) {
        final String takeMoney = "UPDATE card SET balance = balance - ? WHERE number = ?";
        final String addMoney = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (final Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);

            try (final PreparedStatement takeStatement = con.prepareStatement(takeMoney);
                 final PreparedStatement addStatement = con.prepareStatement(addMoney)) {
                takeStatement.setLong(1, amount);
                takeStatement.setString(2, fromCardNumber);
                takeStatement.executeUpdate();

                addStatement.setLong(1, amount);
                addStatement.setString(2, toCardNumber);
                addStatement.executeUpdate();

                con.commit();

                System.out.println("Success!");
            } catch (final SQLException e1) {
                e1.printStackTrace();
                try {
                    con.rollback();
                } catch (final SQLException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
