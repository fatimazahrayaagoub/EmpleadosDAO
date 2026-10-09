package org.example.implementacionesDAO;

import org.example.clases.Empleado;
import org.example.conexion.DatabaseConnection;
import org.example.interfasesDAO.EmpleadoDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private final DatabaseConnection databaseConnection;

    public EmpleadoDAOImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public void crearTabla() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Empleados(
                id_emp INTEGER PRIMARY KEY,
                nombre VARCHAR(100),
                apel VARCHAR(30),
                oficio VARCHAR(10),
                dni VARCHAR(9),
                fecha_alt DATE,
                salario REAL,
                comision REAL,
                id_dep INTEGER,
                FOREIGN KEY(id_dep) REFERENCES Departamentos
                )
                """;
        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement()) {
            st.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void insertarEmpleado(Empleado empleado) {
        String sql = """
                INSERT INTO Empleados(id_emp, nombre, apel, oficio, dni, fecha_alt, salario, comision, id_dep)
                VALUES(?,?,?,?,?,?,?,?,?)
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, empleado.getIdEmp());
            pst.setString(2, empleado.getNombre());
            pst.setString(3, empleado.getApel());
            pst.setString(4, empleado.getOficio());
            pst.setString(5, empleado.getDni());
            pst.setString(6, empleado.getFecha_alta().toString());
            pst.setDouble(7, empleado.getSalario());
            if (empleado.getComision() == null) {
                pst.setNull(8, java.sql.Types.DOUBLE);
            } else {
                pst.setDouble(8, empleado.getComision());
            }
            pst.setInt(9, empleado.getIdDep());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Empleado> mostrarEmpleado() {
        List<Empleado> lista = new ArrayList<>();

        String sql = """
                SELECT nombre,oficio,salario FROM Empleados
                """;

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setOficio(rs.getString("oficio"));
                empleado.setSalario(rs.getDouble("salario"));

                lista.add(empleado);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;

    }

    @Override
    public List<Empleado> empleadoPorDepartamento(String nombreDepartamentp) {
        List<Empleado> lista = new ArrayList<>();

        String sql = """
                SELECT* FROM Empleados e
                JOIN Departamentos d
                ON e.id_dep=d.id
                WHERE d.nombre=?
                """;
        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)
        ) {


            pst.setString(1, nombreDepartamentp);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getInt("id_emp"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("oficio"),
                            rs.getString("dni"),
                            LocalDate.parse(rs.getString("fecha_alt")),
                            rs.getDouble("salario"),
                            rs.getObject("comision") == null
                                    ? null : rs.getDouble("comision"),
                            rs.getInt("id_dep")
                    );

                    lista.add(empleado);
                }

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;

    }

    @Override
    public List<Empleado> empleadosPorSalario() {
        List<Empleado> lista = new ArrayList<>();


        Scanner sc = new Scanner(System.in);
        System.out.println("INTRODUCE UN SALARIO : ");
        Double salario = sc.nextDouble();

        String sql = """
                SELECT*FROM Empleados
                WHERE salario=?
                """;


        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setDouble(1, salario);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getInt("id_emp"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("oficio"),
                            rs.getString("dni"),
                            LocalDate.parse(rs.getString("fecha_alt")),
                            rs.getDouble("salario"),
                            rs.getObject("comision") == null
                                    ? null : rs.getDouble("comision"),
                            rs.getInt("id_dep")
                    );

                    lista.add(empleado);
                }

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;


    }

    @Override
    public List<Empleado> empleadosPorOficio() {

        List<Empleado> lista = new ArrayList<>();
        String sql = """
                SELECT* FROM Empleados
                WHERE oficio="ANALISTA"
                
                """;


        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_emp"),
                        rs.getString("nombre"),
                        rs.getString("apel"),
                        rs.getString("oficio"),
                        rs.getString("dni"),
                        LocalDate.parse(rs.getString("fecha_alt")),
                        rs.getDouble("salario"),
                        rs.getObject("comision") == null
                                ? null : rs.getDouble("comision"),
                        rs.getInt("id_dep")
                );

                lista.add(empleado);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;

    }


    @Override
    public List<Empleado> empleadosSinComision() {
        List<Empleado> lista=new ArrayList<>();
        String sql= """
                SELECT* FROM Empleados
                WHERE comision IS NULL
                """;


        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_emp"),
                        rs.getString("nombre"),
                        rs.getString("apel"),
                        rs.getString("oficio"),
                        rs.getString("dni"),
                        LocalDate.parse(rs.getString("fecha_alt")),
                        rs.getDouble("salario"),
                        rs.getObject("comision") == null
                                ? null : rs.getDouble("comision"),
                        rs.getInt("id_dep")
                );

                lista.add(empleado);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
   public double calcularSalarioMedio(){

        Double salarioMedio=0.0;
        String sql= """
                SELECT AVG(salario) AS salarioMedio  FROM Empleados
                """;

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {


            if (rs.next()) {
                salarioMedio = rs.getDouble("salarioMedio");
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salarioMedio;


    }



}
