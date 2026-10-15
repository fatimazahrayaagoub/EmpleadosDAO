package org.example.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Departamento {
    private int id;
    private String  nombre;
    private String localidad;


}
