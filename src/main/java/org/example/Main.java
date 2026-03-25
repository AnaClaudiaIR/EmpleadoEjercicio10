package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Try para hacer la conexión
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        );  Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false); //Desactivar el commit automático

            //Try para intentar meter los datos --> Si no sale hace rollback
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EMPLEADO (ID,NOMBRE,SALARIO) VALUES (?,?,?)")) {
                preparedStatement.setInt(1, 10);
                preparedStatement.setString(2, "Musaraña Lopez");
                preparedStatement.setDouble(3, 267.00);
                preparedStatement.executeUpdate();

                preparedStatement.setInt(1, 15);
                preparedStatement.setString(2, "Fabricia");
                preparedStatement.setDouble(3, 40.00);
                preparedStatement.executeUpdate();

                connection.commit(); //Hacer el commit al acabar
                System.out.println("Transacción exitosa");

            } catch (SQLException e) {
                connection.rollback(); //Hacer rollback de todo si hay más de una operación
                System.out.println("Error al hacer la operación -> " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("ERROR -> "+e.getMessage());
        }
    }
}
