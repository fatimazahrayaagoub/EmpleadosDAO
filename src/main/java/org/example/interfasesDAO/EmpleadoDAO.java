package org.example.interfasesDAO;

import org.example.modelo.Empleado;
import java.sql.SQLDataException;
import java.util.List;

public interface EmpleadoDAO {
    void crearTabla() throws SQLDataException;
    void insertarEmpleado(Empleado empleado) throws SQLDataException;
    List<Empleado> mostrarEmpleado() throws SQLDataException;
    List<Empleado> empleadoPorDepartamento(String nombreDepartamento) throws SQLDataException;

    // Firma actualizada con parámetro
    List<Empleado> empleadosPorSalario(double salario) throws SQLDataException;

    // Firma actualizada con parámetro
    List<Empleado> empleadosPorOficio(String oficio) throws SQLDataException;

    List<Empleado> empleadosSinComision() throws SQLDataException;
    double calcularSalarioMedio() throws SQLDataException;
}