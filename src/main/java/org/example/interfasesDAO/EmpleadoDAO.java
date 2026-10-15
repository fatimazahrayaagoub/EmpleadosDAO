package org.example.interfasesDAO;

import org.example.modelo.Empleado;

import java.sql.SQLDataException;
import java.util.List;

public interface EmpleadoDAO {

    void crearTabla() throws SQLDataException;
    void insertarEmpleado(Empleado empleado) throws SQLDataException;

    List<Empleado> mostrarEmpleado() throws SQLDataException;

    List<Empleado> empleadoPorDepartamento(String nombreDepartamentp) throws SQLDataException;;

    List<Empleado> empleadosPorSalario() throws SQLDataException;

    List<Empleado> empleadosPorOficio() throws SQLDataException;

    List<Empleado> empleadosSinComision() throws SQLDataException;

    double calcularSalarioMedio() throws SQLDataException;


}

