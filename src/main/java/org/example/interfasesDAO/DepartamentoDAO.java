package org.example.interfasesDAO;

import org.example.modelo.Departamento;

import java.sql.SQLDataException;
import java.util.List;

public interface DepartamentoDAO {

     void crearTabla() throws SQLDataException;
     void insertarDepartamentos(Departamento departamento) throws SQLDataException;
     List<Departamento>listarDepartamento() throws SQLDataException;
     void modificarLocalidad() throws SQLDataException;
     List<Departamento> listarDepartamentoPorLocalidad(String localidad) throws SQLDataException;


}
