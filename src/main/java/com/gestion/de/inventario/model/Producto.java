package com.gestion.de.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String modelo;

    @Column(nullable = false, length = 3)
    private int stock;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String tipoProducto;

    @Column(nullable = false)
    private Integer precio;

    public boolean isDisponible() {
        return stock > 0;
    }


}
