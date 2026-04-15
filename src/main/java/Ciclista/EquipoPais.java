package Ciclista;

import org.example.DBConfig;

import java.sql.*;

public class EquipoPais {
    public static void buscarPorPaisEquipo(String pais){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()) {

            String consulta = "SELECT C.NOMBRE, C.ID_CICLISTA, C.NACIONALIDAD, C.EDAD\n" +
                    "FROM CICLISTA C JOIN EQUIPO E USING (ID_EQUIPO)\n" +
                    "WHERE PAIS = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, pais);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                while (resultSet.next()) {
                    String nombre = resultSet.getString("NOMBRE");
                    int id_ciclista = resultSet.getInt("ID_CICLISTA");
                    int edad = resultSet.getInt("EDAD");
                    String nacionalidad = resultSet.getString("NACIONALIDAD");

                    System.out.println("-------------------");
                    System.out.println("Ciclista: "+nombre);
                    System.out.println("ID: "+id_ciclista);
                    System.out.println("Edad: "+edad);
                    System.out.println("Nacionalidad: "+nacionalidad);
                }
            } else {
                System.out.println("No hay ningún equipo de ese país.");
            }
        } catch (SQLException e){
            System.out.println("ERROR -> "+e.getMessage());
        }
    }
}
