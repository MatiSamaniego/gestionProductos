// src/main/java/com/gestion/de/inventario/model/Producto.java
package com.gestion.de.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data; // <--- ¡ASEGÚRATE DE QUE ESTÉ ESTA ANOTACIÓN!
import lombok.NoArgsConstructor;

@Entity
@Data // <--- Esta es clave para generar getters y setters
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private int stock;
    private String marca;
    private String tipoProducto;
    private int precio; // Asumiendo que quieres int para el precio
}