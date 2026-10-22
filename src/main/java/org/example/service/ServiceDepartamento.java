package org.example.service;

import org.example.modelo.Departamento;
import org.example.interfasesDAO.DepartamentoDAO;

import java.sql.SQLDataException;
import java.util.List;

public class ServiceDepartamento {

    private final DepartamentoDAO departamentoDAO;

    public ServiceDepartamento(DepartamentoDAO departamentoDAO) {
        this.departamentoDAO = departamentoDAO;
    }

    // Validar los datos del departamento
    public void validarDepartamento(Departamento departamento)
            throws SQLDataException {

        if (departamento == null) {
            throw new IllegalArgumentException(
                    "El departamento no puede ser nulo");
        }

        if (departamento.getId() <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0");
        }

        if (departamento.getNombre() == null
                || departamento.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio");
        }

        if (departamento.getNombre().length() > 15) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 15 caracteres");
        }

        if (departamento.getLocalidad() == null
                || departamento.getLocalidad().isBlank()) {
            throw new IllegalArgumentException(
                    "La localidad es obligatoria");
        }

        if (departamento.getLocalidad().length() > 15) {
            throw new IllegalArgumentException(
                    "La localidad no puede superar los 15 caracteres");
        }
    }

    // Comprobar si existe un departamento por su ID
    public boolean existeDepartamento(int id)
            throws SQLDataException {

        if (id <= 0) {
            return false;
        }

        List<Departamento> lista =
                departamentoDAO.listarDepartamento();

        for (Departamento d : lista) {
            if (d.getId() == id) {
                return true;
            }
        }

        return false;
    }

    // Insertar departamento validando antes
    public void insertarDepartamento(Departamento departamento)
            throws SQLDataException {

        validarDepartamento(departamento);

        if (existeDepartamento(departamento.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un departamento con ese ID");
        }

        departamentoDAO.insertarDepartamentos(departamento);
    }

    // Listar departamentos
    public List<Departamento> listarDepartamentos()
            throws SQLDataException {

        return departamentoDAO.listarDepartamento();
    }
}