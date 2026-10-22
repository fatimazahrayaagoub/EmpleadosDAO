package org.example.implementacionesDAO;

import org.example.modelo.Departamento;
import org.example.conexion.DatabaseConnection;
import org.example.interfasesDAO.DepartamentoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOImpl implements DepartamentoDAO {
    private final DatabaseConnection databaseConnection;

    public DepartamentoDAOImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void crearTabla() throws SQLDataException {
        String sql = """
                CREATE TABLE IF NOT EXISTS Departamentos(
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(15),
                localidad VARCHAR(15)
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
    public void insertarDepartamentos(Departamento departamento) throws SQLDataException {
        // Si el ID es autoincremental, es mejor no forzarlo en el INSERT a menos que sea necesario.
        // Si necesitas insertar el ID manual, asegúrate de quitar AUTO_INCREMENT en la tabla.
        String sql = """
                INSERT INTO Departamentos (id, nombre, localidad)
                VALUES (?, ?, ?)
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, departamento.getId());
            pst.setString(2, departamento.getNombre());
            pst.setString(3, departamento.getLocalidad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Departamento> listarDepartamento() throws SQLDataException {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamentos";

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Departamento departamento = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("localidad")
                );
                lista.add(departamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public void modificarLocalidad() throws SQLDataException {
        String sql = """
                UPDATE Departamentos
                SET localidad = 'Toledo'
                WHERE nombre = 'Ventas'
                """;

        try (Connection conex = databaseConnection.getConnection();
             Statement st = conex.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Departamento> listarDepartamentoPorLocalidad(String localidad) throws SQLDataException {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamentos WHERE localidad = ?";

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setString(1, localidad);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Departamento departamento = new Departamento(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("localidad")
                    );
                    lista.add(departamento);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}