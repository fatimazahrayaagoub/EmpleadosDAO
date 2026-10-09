package org.example.interfasesDAO;

import org.example.clases.Empleado;

import java.util.List;

public interface EmpleadoDAO {

    void crearTabla();
    void insertarEmpleado(Empleado empleado);

    List<Empleado> mostrarEmpleado();

    List<Empleado> empleadoPorDepartamento(String nombreDepartamentp);

    List<Empleado> empleadosPorSalario();

    List<Empleado> empleadosPorOficio();

    List<Empleado> empleadosSinComision();

    double calcularSalarioMedio();


}

