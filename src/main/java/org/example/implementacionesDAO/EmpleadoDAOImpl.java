package org.example.implementacionesDAO;

import org.example.modelo.Empleado;
import org.example.conexion.DatabaseConnection;
import org.example.interfasesDAO.EmpleadoDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private final DatabaseConnection databaseConnection;

    public EmpleadoDAOImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void crearTabla() throws SQLDataException {
        String sql = """
                CREATE TABLE IF NOT EXISTS Empleados(
                id_emp INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100),
                apel VARCHAR(30),
                oficio VARCHAR(10),
                dni VARCHAR(9),
                fecha_alt DATE,
                salario DOUBLE,
                comision DOUBLE,
                id_dep INT,
                FOREIGN KEY(id_dep) REFERENCES Departamentos(id)
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
    public void insertarEmpleado(Empleado empleado) throws SQLDataException {
        String sql = """
                INSERT INTO Empleados
                (nombre, apel, oficio, dni, fecha_alt, salario, comision, id_dep)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setString(1, empleado.getNombre());
            pst.setString(2, empleado.getApel());
            pst.setString(3, empleado.getOficio());
            pst.setString(4, empleado.getDni());

            if (empleado.getFecha_alta() == null) {
                pst.setNull(5, java.sql.Types.DATE);
            } else {
                pst.setDate(5, java.sql.Date.valueOf(empleado.getFecha_alta()));
            }

            pst.setDouble(6, empleado.getSalario());

            if (empleado.getComision() == null) {
                pst.setNull(7, java.sql.Types.DOUBLE);
            } else {
                pst.setDouble(7, empleado.getComision());
            }

            pst.setInt(8, empleado.getIdDep());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Empleado> mostrarEmpleado() throws SQLDataException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados";

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<Empleado> empleadoPorDepartamento(String nombreDepartamento) throws SQLDataException {
        List<Empleado> lista = new ArrayList<>();
        String sql = """
                SELECT e.* FROM Empleados e
                JOIN Departamentos d ON e.id_dep = d.id
                WHERE d.nombre = ?
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setString(1, nombreDepartamento);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEmpleado(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    // Modificado para recibir el salario por parámetro en lugar de usar Scanner en el DAO
    @Override
    public List<Empleado> empleadosPorSalario(double salario) throws SQLDataException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados WHERE salario = ?";

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setDouble(1, salario);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEmpleado(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<Empleado> empleadosPorOficio(String oficio) throws SQLDataException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados WHERE oficio = ?";

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setString(1, oficio);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEmpleado(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<Empleado> empleadosSinComision() throws SQLDataException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados WHERE comision IS NULL";

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public double calcularSalarioMedio() throws SQLDataException {
        double salarioMedio = 0.0;
        String sql = "SELECT AVG(salario) AS salarioMedio FROM Empleados";

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

    // Método auxiliar privado para evitar repetir la lógica de mapeo del ResultSet
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("id_emp"),
                rs.getString("nombre"),
                rs.getString("apel"),
                rs.getString("oficio"),
                rs.getString("dni"),
                rs.getDate("fecha_alt") != null ? rs.getDate("fecha_alt").toLocalDate() : null,
                rs.getDouble("salario"),
                rs.getObject("comision") == null ? null : rs.getDouble("comision"),
                rs.getInt("id_dep")
        );
    }
}