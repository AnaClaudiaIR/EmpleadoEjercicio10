package Ciclista;

import org.example.DBConfig;

import java.sql.*;

public class InsertarCiclista {
    public static void insertar(String nombre, String nacionalidad, int edad, int idEquipo){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()) {

            //Preparar el String para insertar datos
            String SQLinsert = "INSERT INTO CICLISTA(id_ciclista, nombre, nacionalidad, edad, id_equipo) VALUES (?,?,?,?,?)";

            //Pillar el último ID mediante una consulta
            String SQLid = "SELECT MAX(ID_CICLISTA) as max_id\n" +
                    "FROM CICLISTA";
            int idCiclista = 0;

            ResultSet rs = statement.executeQuery(SQLid);

            //Si hay un ID, lo toma y le suma 1 para asignarle un ID al nuevo ciclista
            if (rs.next()) {
                idCiclista = rs.getInt("max_id")+1;
            }
            //Preparar el statement para introducir los datos
            PreparedStatement preparedStatement = connection.prepareStatement(SQLinsert);

            //Se introducen en el orden asignado en el String
            preparedStatement.setInt(1, idCiclista);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, nacionalidad);
            preparedStatement.setInt(4, edad);
            preparedStatement.setInt(5, idEquipo);
            preparedStatement.executeUpdate();

            System.out.println("Registro insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("ERROR --> " + e.getMessage());
        }
    }

    /*public static void comprobarID(int idEquipo, String nombre, String nacionalidad, int edad){
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        ); Statement statement = connection.createStatement()){
            String equipo = "SELECT ID_EQUIPO\n" +
                    "FROM EQUIPO\n" +
                    "WHERE ID_EQUIPO = ?";
           statement.executeQuery(sql);
           ResultSet equipo = statement.executeQuery(sql);
           if (rs.next()){

           } else{
               System.out.println("No existe el equipo con ese ID.");
           }


        } catch (SQLException e) {
            System.out.println("ERROR --> " + e.getMessage());
        }
    }*/
}
