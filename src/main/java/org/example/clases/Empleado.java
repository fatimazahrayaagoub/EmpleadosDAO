package org.example.clases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Empleado {

    private int idEmp;
    private String nombre;
    private String apel;
    private String oficio;
    private String dni;
    private LocalDate fecha_alta;
    private double salario;
    private Double comision;
    private int idDep;


}
