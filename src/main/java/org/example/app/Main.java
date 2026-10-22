package org.example.app;

import org.example.modelo.Departamento;
import org.example.modelo.Empleado;
import org.example.conexion.DatabaseConnection;
import org.example.implementacionesDAO.DepartamentoDAOImpl;
import org.example.implementacionesDAO.EmpleadoDAOImpl;
import org.example.interfasesDAO.DepartamentoDAO;
import org.example.interfasesDAO.EmpleadoDAO;

import java.sql.SQLDataException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        DepartamentoDAO departamentoDAO =
                new DepartamentoDAOImpl(databaseConnection);

        EmpleadoDAO empleadoDAO =
                new EmpleadoDAOImpl(databaseConnection);


        // ==================== DEPARTAMENTOS ====================

        try {
            departamentoDAO.crearTabla();
            System.out.println("========== TABLA DEPARTAMENTOS CREADA ==========");
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            Departamento departamento1 = new Departamento(10, "Informatica", "Madrid");
            Departamento departamento2 = new Departamento(20, "Ventas", "Sevilla");
            Departamento departamento3 = new Departamento(30, "Contabilidad", "Valencia");

            departamentoDAO.insertarDepartamentos(departamento1);
            departamentoDAO.insertarDepartamentos(departamento2);
            departamentoDAO.insertarDepartamentos(departamento3);

            System.out.println("========== DEPARTAMENTOS INSERTADOS ==========");
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== LISTA DE DEPARTAMENTOS ==========");
            List<Departamento> departamentos = departamentoDAO.listarDepartamento();
            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            departamentoDAO.modificarLocalidad();
            System.out.println("========== LOCALIDAD MODIFICADA ==========");
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== DEPARTAMENTOS DE VALENCIA ==========");
            List<Departamento> departamentosPorLocalidad =
                    departamentoDAO.listarDepartamentoPorLocalidad("Valencia");

            for (Departamento departamento : departamentosPorLocalidad) {
                System.out.println(departamento);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }


        // ==================== EMPLEADOS ====================

        try {
            empleadoDAO.crearTabla();
            System.out.println("========== TABLA EMPLEADOS CREADA ==========");
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            Empleado empleado1 = new Empleado(1, "Ana", "Garcia", "ANALISTA", "12345678A", LocalDate.of(2021, 3, 15), 2200, 300.0, 10);
            Empleado empleado2 = new Empleado(2, "Luis", "Perez", "VENDEDOR", "23456789B", LocalDate.of(2022, 6, 10), 1500, 200.0, 20);
            Empleado empleado3 = new Empleado(3, "Maria", "Lopez", "CONTADOR", "34567890C", LocalDate.of(2020, 1, 20), 1800, null, 30);
            Empleado empleado4 = new Empleado(4, "Carlos", "Martin", "ANALISTA", "45678901D", LocalDate.of(2023, 2, 1), 2200, null, 10);
            Empleado empleado5 = new Empleado(5, "Elena", "Sanchez", "VENDEDOR", "56789012E", LocalDate.of(2021, 9, 12), 1600, 250.0, 20);
            Empleado empleado6 = new Empleado(6, "Pedro", "Ruiz", "ADMIN", "67890123F", LocalDate.of(2024, 4, 5), 1400, null, 30);
            Empleado empleado7 = new Empleado(7, "Lucia", "Torres", "ANALISTA", "78901234G", LocalDate.of(2019, 11, 18), 2500, 400.0, 10);
            Empleado empleado8 = new Empleado(8, "Jorge", "Diaz", "VENDEDOR", "89012345H", LocalDate.of(2022, 8, 22), 1500, null, 20);
            Empleado empleado9 = new Empleado(9, "Sofia", "Romero", "CONTADOR", "90123456J", LocalDate.of(2023, 5, 16), 1800, 200.0, 30);
            Empleado empleado10 = new Empleado(10, "David", "Moreno", "ADMIN", "01234567K", LocalDate.of(2024, 1, 8), 1400, null, 10);

            empleadoDAO.insertarEmpleado(empleado1);
            empleadoDAO.insertarEmpleado(empleado2);
            empleadoDAO.insertarEmpleado(empleado3);
            empleadoDAO.insertarEmpleado(empleado4);
            empleadoDAO.insertarEmpleado(empleado5);
            empleadoDAO.insertarEmpleado(empleado6);
            empleadoDAO.insertarEmpleado(empleado7);
            empleadoDAO.insertarEmpleado(empleado8);
            empleadoDAO.insertarEmpleado(empleado9);
            empleadoDAO.insertarEmpleado(empleado10);

            System.out.println("========== EMPLEADOS INSERTADOS ==========");
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== LISTA DE EMPLEADOS ==========");
            List<Empleado> empleados = empleadoDAO.mostrarEmpleado();
            for (Empleado empleado : empleados) {
                System.out.println(empleado);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== EMPLEADOS DEL DEPARTAMENTO VENTAS ==========");
            List<Empleado> empleadosPorDepartamento = empleadoDAO.empleadoPorDepartamento("Ventas");
            for (Empleado empleado : empleadosPorDepartamento) {
                System.out.println(empleado);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== EMPLEADOS POR SALARIO (Ej: 2200.0) ==========");
            // Se pasa el salario directamente por parámetro en lugar de usar Scanner en el DAO
            List<Empleado> empleadosPorSalario = empleadoDAO.empleadosPorSalario(2200.0);
            for (Empleado empleado : empleadosPorSalario) {
                System.out.println(empleado);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== EMPLEADOS DEL OFICIO ANALISTA ==========");
            // Se pasa el oficio por parámetro de forma dinámica
            List<Empleado> empleadosPorOficio = empleadoDAO.empleadosPorOficio("ANALISTA");
            for (Empleado empleado : empleadosPorOficio) {
                System.out.println(empleado);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== EMPLEADOS SIN COMISION ==========");
            List<Empleado> empleadosSinComision = empleadoDAO.empleadosSinComision();
            for (Empleado empleado : empleadosSinComision) {
                System.out.println(empleado);
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("========== SALARIO MEDIO ==========");
            System.out.println("El salario medio de los empleados es: " + empleadoDAO.calcularSalarioMedio());
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }
    }
}