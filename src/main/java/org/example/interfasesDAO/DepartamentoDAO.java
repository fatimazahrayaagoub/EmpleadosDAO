package org.example.interfasesDAO;

import org.example.clases.Departamento;

import java.util.List;

public interface DepartamentoDAO {

     void crearTabla();
     void insertarDepartamentos(Departamento departamento);
     List<Departamento>listarDepartamento(Departamento departamento);
     void modificarLocalidad(String nombre,String localidad);
     List<Departamento> listarDepartamentoPorLocalidad(String localidad);


}
