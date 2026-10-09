package org.example.interfasesDAO;

import org.example.clases.Departamento;

import java.util.List;

public interface DepartamentoDAO {

     void crearTabla();
     void insertarDepartamentos(Departamento departamento);
     List<Departamento>listarDepartamento();
     void modificarLocalidad();
     List<Departamento> listarDepartamentoPorLocalidad(String localidad);


}
