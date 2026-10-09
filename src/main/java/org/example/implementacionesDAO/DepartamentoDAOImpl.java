package org.example.implementacionesDAO;

import org.example.clases.Departamento;
import org.example.conexion.DatabaseConnection;
import org.example.interfasesDAO.DepartamentoDAO;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOImpl implements DepartamentoDAO {
    private final DatabaseConnection databaseConnection;


    public DepartamentoDAOImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void crearTabla(){
        String sql= """
                CREATE TABLE IF NOT EXISTS Departamentos
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(15),
                localidad VARCHAR(15)
                
                """;

        try(Connection conex=databaseConnection.getConnection();
            Statement st=conex.createStatement()){

            st.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarDepartamentos(Departamento departamento){
        String sql= """
                INSERT INTO Departamentos (id,nombre,localidad)
                VALUES(?.?.?)
                """;

        try (Connection conex=databaseConnection.getConnection();
             PreparedStatement pst=conex.prepareStatement(sql)){
            pst.setInt(1,departamento.getId());
            pst.setString(2,departamento.getNombre());
            pst.setString(3,departamento.getLocalidad());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Departamento> listarDepartamento(){
        List<Departamento> lista=new ArrayList<>();

        String sql= """
                SELECT*FROM Departamentos
                """;

        try()

    }
    public void modificarLocalidad(String nombre,String localidad){

    }
    public List<Departamento> listarDepartamentoPorLocalidad(String localidad){

    }

}
