package Ciclista;

import org.example.DBConfig;

import java.sql.*;

public class MostrarCiclistas {
    public static void mostrar(){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()) {

            String consulta = "SELECT C.NOMBRE AS CICLISTA, C.ID_CICLISTA, C.NACIONALIDAD, C.EDAD, E.NOMBRE AS EQUIPO\n" +
                    "FROM CICLISTA C JOIN EQUIPO E USING (ID_EQUIPO)" +
                    " ORDER BY ID_CICLISTA";
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id =  resultSet.getString("id_ciclista");
                String ciclista = resultSet.getString("ciclista");
                String nacionalidad = resultSet.getString("nacionalidad");
                String equipo = resultSet.getString("equipo");
                int edad = resultSet.getInt("edad");

                System.out.println("ID: "+id + " -- NOMBRE: " +ciclista + "-- NACIONALIDAD: " + nacionalidad + "-- EDAD: " + edad + "-- EQUIPO: " + equipo);
            }
        } catch (SQLException e){
            System.out.println("ERROR -> "+e.getMessage());
        }
    }
}
