package Ciclista;

import org.example.DBConfig;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("1. Insertar CICLISTA.");
            System.out.println("2. Actualizar CICLISTA.");
            System.out.println("3. Eliminar CICLISTA.");
            System.out.println("4. Buscar CICLISTA por PAÍS del equipo.");
            System.out.println("5. Mostrar CICLISTAS.");
            System.out.println("0. Salir.");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                //INSERTAR CICLISTA
                case 1:
                    boolean idValido = false;
                    while (!idValido) {
                        //Pedir el ID
                        System.out.println("Ingrese el ID del equipo:");
                        int idEquipo = sc.nextInt();

                        try (Connection connection = DriverManager.getConnection(
                                DBConfig.getUrl(),
                                DBConfig.getUser(),
                                DBConfig.getPassword()
                        ); Statement statement = connection.createStatement()){

                            //Hacer una consulta para comprobar si existe el ID
                            String SQLComprobar = "SELECT ID_EQUIPO\n" +
                                    "FROM EQUIPO\n" +
                                    "WHERE ID_EQUIPO = ?";

                            //Meter el ID que han pasado con un PS
                            PreparedStatement preparedStatement = connection.prepareStatement(SQLComprobar);
                            preparedStatement.setInt(1, idEquipo);

                            //Resultado --> Lo que se ejecute del PS
                            ResultSet resultSet = preparedStatement.executeQuery();

                            //Si está el ID --> pide los datos, luego llama al método y los mete como parámetros
                            if (resultSet.next()){
                                idValido = true;
                                System.out.println("Nombre: ");
                                String nombre = sc.next();

                                System.out.println("Nacionalidad: ");
                                String nacionalidad = sc.next();

                                System.out.println("Edad: ");
                                int edad = sc.nextInt();

                                InsertarCiclista.insertar(nombre,nacionalidad,edad,idEquipo);
                            } else{
                                //Si no, pone que no existe
                                System.out.println("No existe el equipo con ese ID.");
                            }
                        } catch (SQLException e) {
                            System.out.println("ERROR --> " + e.getMessage());
                            break;
                        }
                    }
                    break;
                //ACTUALIZAR CICLISTA
                case 2:
                    boolean idCiclistaValido = false;
                    while (!idCiclistaValido) {
                        System.out.println("Ingrese el ID del ciclista: ");
                        int idCiclista = sc.nextInt();

                        try (Connection connection = DriverManager.getConnection(
                                DBConfig.getUrl(),
                                DBConfig.getUser(),
                                DBConfig.getPassword()
                        ); Statement statement = connection.createStatement()){

                            //Hacer una consulta para comprobar si existe el ID del Ciclista
                            String SQLComprobar = "SELECT ID_CICLISTA\n" +
                                    "FROM CICLISTA\n" +
                                    "WHERE ID_CICLISTA = ?";

                            //Meter el ID que han pasado con un PS
                            PreparedStatement preparedStatement = connection.prepareStatement(SQLComprobar);
                            preparedStatement.setInt(1, idCiclista);

                            //Resultado --> Lo que se ejecute del PS
                            ResultSet resultSet = preparedStatement.executeQuery();

                            //Si está el ID --> pide el ID del equipo
                            if (resultSet.next()){
                                idCiclistaValido = true;

                                System.out.println("ID del nuevo equipo: ");
                                int idEquipoNuevo = sc.nextInt();

                                boolean idEquipoValido = false;
                                while (!idEquipoValido) {
                                    //Comprueba si el ID del equipo existe
                                    try (Statement statement2 = connection.createStatement()) {
                                        String SQLComprobarID = "SELECT ID_EQUIPO\n" +
                                                "FROM EQUIPO\n" +
                                                "WHERE ID_EQUIPO = ?";

                                        PreparedStatement preparedStatement2 = connection.prepareStatement(SQLComprobarID);
                                        preparedStatement2.setInt(1, idEquipoNuevo);

                                        //Resultado --> Lo que se ejecute del PS
                                        ResultSet resultSet2 = preparedStatement2.executeQuery();

                                        //Si el ID existe, pide la edad para ya luego meter los tres datos
                                        if (resultSet2.next()) {
                                            idEquipoValido = true;
                                            //idCiclistaValido = true;
                                            System.out.println("Edad: ");
                                            int edadNuevo = sc.nextInt();

                                            //Comprobado todo, se llama al método y se meten todos los datos
                                            ActualizarCiclista.actualizar(idCiclista, edadNuevo, idEquipoNuevo);
                                        } else {
                                            System.out.println("No existe el equipo con ese ID.");
                                        }
                                    } catch (SQLException e){
                                        System.out.println("ERROR --> " + e.getMessage());
                                        break;
                                    }
                               }
                            } else{
                                System.out.println("No existe el ciclista con ese ID.");
                            }
                        } catch (SQLException e) {
                            System.out.println("ERROR --> " + e.getMessage());
                            break;
                        }
                    }
                    break;
                //ELIMINAR CICLISTA
                case 3:
                    //Pedir ID
                    System.out.println("Ingrese el ID del ciclista: ");
                    int idCiclistaEliminar = sc.nextInt();

                    //Confirmar los cambios
                    System.out.println("¿Desea confirmar cambios? (S/N)");
                    char elegir = sc.next().charAt(0);
                    if (elegir == 'S' || elegir == 's'){
                        //Llamar al método
                        EliminarCiclista.eliminar(idCiclistaEliminar);
                    } else {
                        System.out.println("No se han realizado cambios.");
                    }
                    break;
                //MOSTRAR SEGÚN PAÍSES
                case 4:
                    sc.nextLine();
                    System.out.println("Introduce el país: ");
                    String pais = sc.nextLine();

                    EquipoPais.buscarPorPaisEquipo(pais);
                    break;

                //MOSTRAR TODOS LOS CICLISTAS
                case 5:
                    MostrarCiclistas.mostrar();
                    break;
                case 0:
                    System.out.println("Has salido.");
                    break;
                default:
                    System.out.println("Escoge una opción válida.");
            }
        } while (opcion != 0);
    }
}
