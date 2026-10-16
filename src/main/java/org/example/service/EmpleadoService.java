package org.example.service;

public class EmpleadoService {

    public void validar(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("el ide debe ser mayor que 0");

        }
    }

    public void valodarNombre(String nombre){
        if(nombre.length()>50){
            throw new IllegalArgumentException("el nombre como máximo puede tener 50 caracteres");
        }
    }

    public void  validarAppellidos(String apellido){

    }
}
