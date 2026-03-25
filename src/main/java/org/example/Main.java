import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        )) {
            System.out.println("Connected to database.");
        } catch (SQLException e){
            System.out.println("ERROR --> "+e.getMessage());
        }
    }
}
