package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DataBaseManager {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String dbURL = "jdbc:h2:./database";
    private static final DataBaseManager instance = new DataBaseManager();

    static DataBaseManager getInstance() {
        return instance;
    }

    private DataBaseManager() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch(final Exception e) {
            e.printStackTrace();
        }
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final Statement statement = connection.createStatement()
        ) {
            connection.setAutoCommit(true);
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS company (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) UNIQUE NOT NULL" +
                ");"
            );
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS car (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) UNIQUE NOT NULL, " +
                "company_id INT NOT NULL, " +
                "CONSTRAINT fk_company_id FOREIGN KEY (company_id) REFERENCES company(id)" +
                ");"
            );
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS customer (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) UNIQUE NOT NULL, " +
                "rented_car_id INT DEFAULT NULL, " +
                "CONSTRAINT fk_car_id FOREIGN KEY (rented_car_id) REFERENCES car(id)" +
                ");"
            );
        } catch(final SQLException e) {
            e.printStackTrace();
        }
    }

    List<String> getCompanies() {
        final List<String> companies = new ArrayList<>();
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final Statement statement = connection.createStatement()
        ) {
            connection.setAutoCommit(true);
            final ResultSet result = statement.executeQuery(
                "SELECT name FROM company ORDER BY id;"
            );
            while (result.next()) {
                companies.add(result.getString("name"));
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    boolean createCompany(final String companyName) {
        final String sql = "INSERT INTO company (name) VALUES (?);";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement pStatement = connection.prepareStatement(sql)
        ) {
            connection.setAutoCommit(true);
            pStatement.setString(1, companyName);
            final int affectedRows = pStatement.executeUpdate();
            return affectedRows == 1;
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param companyName The name of the company.
     * @return The company's cars which are not currently rented.
     */
    List<String> getCars(final String companyName) {
        final List<String> cars = new ArrayList<>();
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final Statement getRentedCarIds = connection.createStatement();
            final PreparedStatement getCarNames = connection.prepareStatement(
                "SELECT name, id FROM car WHERE company_id = " +
                    "(SELECT id FROM company WHERE name = ?);"
            )
        ) {
            connection.setAutoCommit(true);
            final ResultSet rentedCarIdsResult = getRentedCarIds.executeQuery(
                "SELECT rented_car_id FROM customer WHERE rented_car_id IS NOT NULL;"
            );
            final List<Integer> rentedCarIds = new ArrayList<>();
            while (rentedCarIdsResult.next()) {
                rentedCarIds.add(rentedCarIdsResult.getInt("rented_car_id"));
            }
            getCarNames.setString(1, companyName);
            final ResultSet result = getCarNames.executeQuery();
            while (result.next()) {
                if (!rentedCarIds.contains(result.getInt("id"))) {
                    cars.add(result.getString("name"));
                }
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    boolean createCar(final String carName, final String companyName) {
        final String sqlGetCompanyId = "SELECT id FROM company WHERE name = ?;";
        final String sqlAddCar = "INSERT INTO car (name, company_id) VALUES (?, ?);";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement getCompanyId = connection.prepareStatement(sqlGetCompanyId);
            final PreparedStatement addCar = connection.prepareStatement(sqlAddCar)
        ) {
            connection.setAutoCommit(true);
            getCompanyId.setString(1, companyName);
            final ResultSet result = getCompanyId.executeQuery();
            if (result.next()) {
                final int companyId = result.getInt("id");
                addCar.setString(1, carName);
                addCar.setInt(2, companyId);
                final int affectedRows = addCar.executeUpdate();
                return affectedRows == 1;
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    List<String> getCustomers() {
        final List<String> customers = new ArrayList<>();
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final Statement statement = connection.createStatement()
        ) {
            connection.setAutoCommit(true);
            final ResultSet result = statement.executeQuery("SELECT name FROM customer ORDER BY id;");
            while (result.next()) {
                customers.add(result.getString("name"));
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    boolean createCustomer(final String name) {
        final String sql = "INSERT INTO customer (name) VALUES (?);";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement pStatement = connection.prepareStatement(sql)
        ) {
            connection.setAutoCommit(true);
            pStatement.setString(1, name);
            final int affectedRows = pStatement.executeUpdate();
            return affectedRows == 1;
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean rentCar(final String customerName, final String carName) {
        final String sqlGetCarId = "SELECT id FROM car WHERE name = ?;";
        final String sqlUpdateCustomer = "UPDATE customer SET rented_car_id = ? WHERE name = ?;";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement getCarId = connection.prepareStatement(sqlGetCarId);
            final PreparedStatement updateCustomer = connection.prepareStatement(sqlUpdateCustomer)
        ) {
            connection.setAutoCommit(true);

            getCarId.setString(1, carName);
            final ResultSet result = getCarId.executeQuery();
            if (result.next()) {
                final int carId = result.getInt("id");
                updateCustomer.setInt(1, carId);
                updateCustomer.setString(2, customerName);
                final int affectedRows = updateCustomer.executeUpdate();
                return affectedRows == 1;
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    String[] getRentedCarAndCompany(final String customerName) {
        final String[] rentedCarAndCompany = new String[2];
        final String sqlGetCarId = "SELECT rented_car_id FROM customer WHERE name = ?;";
        final String sqlGetCarInfo = "SELECT name, company_id FROM car WHERE id = ?;";
        final String sqlGetCompanyName = "SELECT name FROM company WHERE id = ?;";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement getCarId = connection.prepareStatement(sqlGetCarId);
            final PreparedStatement getCarInfo = connection.prepareStatement(sqlGetCarInfo);
            final PreparedStatement getCompanyName = connection.prepareStatement(sqlGetCompanyName)
        ) {
            connection.setAutoCommit(true);
            getCarId.setString(1, customerName);
            final ResultSet carIdResult = getCarId.executeQuery();
            if (carIdResult.next()) {
                final Object carIdObj = carIdResult.getObject("rented_car_id");
                if (carIdObj == null) {
                    return rentedCarAndCompany;
                }
                getCarInfo.setInt(1, (int) carIdObj);
                final ResultSet carInfoResult = getCarInfo.executeQuery();
                if (carInfoResult.next()) {
                    rentedCarAndCompany[0] = carInfoResult.getString("name");
                    getCompanyName.setInt(1, carInfoResult.getInt("company_id"));
                    final ResultSet companyNameResult = getCompanyName.executeQuery();
                    if (companyNameResult.next()) {
                        rentedCarAndCompany[1] = companyNameResult.getString("name");
                    }
                }
            }
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return rentedCarAndCompany;
    }

    boolean returnCar(final String customer) {
        final String sql = "UPDATE customer SET rented_car_id = NULL WHERE name = ?;";
        try (
            final Connection connection = DriverManager.getConnection(dbURL);
            final PreparedStatement pStatement = connection.prepareStatement(sql)
        ) {
            connection.setAutoCommit(true);
            pStatement.setString(1, customer);
            final int affectedRows = pStatement.executeUpdate();
            return affectedRows == 1;
        } catch(final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
