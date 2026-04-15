package Ciclista;

import org.example.DBConfig;

import java.sql.*;

public class EliminarCiclista {
    public static void eliminar(int idCiclista){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()){
            String SQLdelete = "DELETE FROM CICLISTA WHERE ID_CICLISTA = ?";
            String participacion = "DELETE FROM PARTICIPACION WHERE ID_CICLISTA = ?";

            //Ejecutar primero participacion antes de la tabla padre para que no de error (violación clave)
            PreparedStatement preparedStatement2 = connection.prepareStatement(participacion);
            preparedStatement2.setInt(1, idCiclista);
            preparedStatement2.executeUpdate();

            PreparedStatement preparedStatement = connection.prepareStatement(SQLdelete);
            preparedStatement.setInt(1, idCiclista);
            preparedStatement.executeUpdate();

            System.out.println("Eliminado el ciclista con ID: "+idCiclista);
        } catch (SQLException e){
            System.out.println("Error --> " + e.getMessage());
        }
    }
}
