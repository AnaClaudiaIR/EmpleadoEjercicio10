package Ciclista;

import org.example.DBConfig;

import java.sql.*;

public class ActualizarCiclista {
    public static void actualizar(int idCiclista, int edad, int idEquipo){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()){
            String update = "UPDATE CICLISTA\n" +
                    "SET EDAD = ?, ID_EQUIPO = ?\n" +
                    "WHERE ID_CICLISTA = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, edad);
            preparedStatement.setInt(2, idEquipo);
            preparedStatement.setInt(3, idCiclista);
            preparedStatement.executeUpdate();
            System.out.println("Datos actualizados.");

        } catch (SQLException e){
            System.out.println("Error --> " + e.getMessage());
        }
    }
}
