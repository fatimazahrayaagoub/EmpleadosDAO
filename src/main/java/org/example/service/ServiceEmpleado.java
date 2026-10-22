package org.example.service;

import org.example.modelo.Empleado;
import org.example.interfasesDAO.EmpleadoDAO;

import java.sql.SQLDataException;
import java.util.List;

public class ServiceEmpleado {

    private final EmpleadoDAO empleadoDAO;
    private final ServiceDepartamento serviceDepartamento;

    public ServiceEmpleado(
            EmpleadoDAO empleadoDAO,
            ServiceDepartamento serviceDepartamento) {

        this.empleadoDAO = empleadoDAO;
        this.serviceDepartamento = serviceDepartamento;
    }

    // Validar los datos del empleado
    public void validarEmpleado(Empleado empleado)
            throws SQLDataException {

        if (empleado == null) {
            throw new IllegalArgumentException(
                    "El empleado no puede ser nulo");
        }

        // Nombre
        if (empleado.getNombre() == null
                || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio");
        }

        if (empleado.getNombre().length() > 50) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 50 caracteres");
        }

        // Apellidos
        if (empleado.getApel() == null
                || empleado.getApel().isBlank()) {
            throw new IllegalArgumentException(
                    "Los apellidos son obligatorios");
        }

        if (empleado.getApel().length() > 50) {
            throw new IllegalArgumentException(
                    "Los apellidos no pueden superar los 50 caracteres");
        }

        // Oficio
        if (empleado.getOficio() == null
                || empleado.getOficio().isBlank()) {
            throw new IllegalArgumentException(
                    "El oficio es obligatorio");
        }

        if (empleado.getOficio().length() > 10) {
            throw new IllegalArgumentException(
                    "El oficio no puede superar los 10 caracteres");
        }

        // DNI español
        if (!dniValido(empleado.getDni())) {
            throw new IllegalArgumentException(
                    "El DNI no es válido. Ejemplo: 12345678Z");
        }

        // Fecha obligatoria
        if (empleado.getFecha_alta() == null) {
            throw new IllegalArgumentException(
                    "La fecha de alta es obligatoria");
        }

        // Salario entre 0 y 1.000.000
        double salario = empleado.getSalario();

        if (!Double.isFinite(salario)
                || salario < 0
                || salario > 1_000_000) {
            throw new IllegalArgumentException(
                    "El salario debe estar entre 0 y 1.000.000");
        }

        // Comisión opcional, pero si existe debe ser válida
        Double comision = empleado.getComision();

        if (comision != null
                && (!Double.isFinite(comision)
                || comision < 0
                || comision >= salario)) {
            throw new IllegalArgumentException(
                    "La comisión debe ser no negativa y menor que el salario");
        }

        // El departamento debe existir
        if (!serviceDepartamento.existeDepartamento(
                empleado.getIdDep())) {
            throw new IllegalArgumentException(
                    "El departamento no existe");
        }
    }

    // Validar el formato y la letra del DNI
    private boolean dniValido(String dni) {

        if (dni == null
                || !dni.matches("[0-9]{8}[A-Za-z]")) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);

        return Character.toUpperCase(dni.charAt(8))
                == letraCorrecta;
    }

    // Comprobar si ya existe un empleado con ese DNI
    public boolean existeDNI(String dni)
            throws SQLDataException {

        if (dni == null) {
            return false;
        }

        List<Empleado> lista = empleadoDAO.mostrarEmpleado();

        for (Empleado e : lista) {
            if (dni.equalsIgnoreCase(e.getDni())) {
                return true;
            }
        }

        return false;
    }

    // Insertar empleado validando antes
    public void insertarEmpleado(Empleado empleado)
            throws SQLDataException {

        validarEmpleado(empleado);

        if (existeDNI(empleado.getDni())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con ese DNI");
        }

        empleadoDAO.insertarEmpleado(empleado);
    }

    // Mostrar todos los empleados
    public List<Empleado> listarEmpleados()
            throws SQLDataException {

        return empleadoDAO.mostrarEmpleado();
    }
}