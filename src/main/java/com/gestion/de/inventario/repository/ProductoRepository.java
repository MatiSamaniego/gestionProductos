package com.gestion.de.inventario.repository;
import com.gestion.de.inventario.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


    @Query(value = "SELECT * FROM productos", nativeQuery = true)
    List<Producto> findAll();

    @Query(value = "SELECT * FROM productos WHERE modelo LIKE %:modelo%", nativeQuery = true)
    List<Producto> findByModeloLike(@Param("modelo") String modelo);

    @Query(value = "SELECT * FROM productos WHERE id = :id", nativeQuery = true)
    Producto findProductoById(@Param("id") Long id);

    @Query(value = "SELECT * FROM productos WHERE marca LIKE %:marca%", nativeQuery = true)
    List<Producto> findByMarcaLike(@Param("marca") String marca);

    @Query(value = "SELECT * FROM productos WHERE tipo_producto LIKE %:tipoProducto%", nativeQuery = true)
    List<Producto> findByTipoProductoLike(@Param("tipoProducto") String tipoProducto);
}
